import http from 'k6/http';
import { check } from 'k6';

export const options = {
  scenarios: {
    spike: {
      executor: 'ramping-vus',
      startVUs: 0,
      stages: [
        { duration: '1m', target: 5 }, // 빠르게 1000명까지 증가
        { duration: '3m', target: 5 }, // 1000명 유지
        { duration: '1m', target: 0 }  // 점차 감소
      ],
    },
  }
};

export default function () {
  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const response = http.get('http://host.docker.internal:9001/api/v1/products', params);

  check(response, {
    'Status is 200': (r) => r.status === 200,
  });
}
