package kr.hhplus.be.server.interfaces.common;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {
    String topic(); // 락 이름의 prefix
    String keyExpression(); // SpEL 표현식으로 파라미터에서 키 추출
    long waitTime() default 5L; // 락 대기 시간 (초)
    long leaseTime() default 3L; // 락 유지 시간 (초)
    TimeUnit unit() default TimeUnit.SECONDS;
}