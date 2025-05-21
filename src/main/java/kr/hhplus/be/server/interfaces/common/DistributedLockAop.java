package kr.hhplus.be.server.interfaces.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.data.util.Pair;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @see EnableTransactionManagement
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DistributedLockAop {

    private final RedissonClient redissonClient;

    @Around("@annotation(DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature ms)) {
            throw new AssertionError();
        }

        Method method = ms.getMethod();
        DistributedLock annotation = AnnotationUtils.findAnnotation(method, DistributedLock.class);
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();

        List<Pair<Parameter, Object>> pairs = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            pairs.add(Pair.of(parameters[i], args[i]));
        }

        String keyExpression = annotation.keyExpression();

        if (!StringUtils.hasText(keyExpression)) {
            throw new AssertionError();
        }

        String keyParameterName;
        Pattern pattern = Pattern.compile("#(\\w+)");
        Matcher matcher = pattern.matcher(keyExpression);

        if (matcher.find()) {
            keyParameterName = matcher.group(1);
        } else {
            throw new AssertionError();
        }

        if (!StringUtils.hasText(keyParameterName)) {
            throw new AssertionError();
        }

        Pair<Parameter, Object> pair = pairs.stream()
                .filter(it -> it.getFirst().getName().equals(keyParameterName))
                .findFirst()
                .orElseThrow();

        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(keyExpression);

        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable(pair.getFirst().getName(), pair.getSecond());
        Object idObject = expression.getValue(context);

        List<Object> ids = new ArrayList<>();
        if (idObject instanceof Object[] array) {
            ids.addAll(List.of(array));
        } else if (idObject instanceof Collection<?> collection) {
            ids.addAll(collection);
        } else {
            ids.add(idObject);
        }

        String topic = annotation.topic();
        RLock[] locks = ids.stream()
                .map(id -> "%s:%s".formatted(topic, id))
                .map(redissonClient::getLock)
                .toArray(RLock[]::new);
        // TODO: 멀티락 vs 단일락 여러 번
        RLock multiLock = redissonClient.getMultiLock(locks);
        log.info("try locks: {}", (Object) locks);

        boolean available = false;
        try {
            log.info("try ids: {}", ids);
            long waitTime = annotation.waitTime();
            long leaseTime = annotation.leaseTime();
            TimeUnit unit = annotation.unit();
            log.info("tryLock for ids={} with topic={}", ids, topic);
            available = multiLock.tryLock(waitTime, leaseTime, unit); // 5초 동안 락 시도, 3초 동안 유지
            log.info("lock status: {}", available);
            if (!available) {
                throw new IllegalStateException("락 획득 실패 - ids: " + ids);
            }

            return joinPoint.proceed();
        } finally {
            if (available) {
                multiLock.unlock();
            }
        }
    }

}