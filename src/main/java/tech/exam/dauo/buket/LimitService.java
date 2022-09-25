package tech.exam.dauo.buket;


import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LimitService {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private String getHost(HttpServletRequest httpServletRequest){
        return httpServletRequest.getHeader("Host");
    }

    /* ---------------------- 접속 제한! ----------------- */
    public Bucket resolveBucket(HttpServletRequest httpServletRequest) {
        return cache.computeIfAbsent(getHost(httpServletRequest), this::newBucket);
    }

    private Bucket newBucket(String apiKey) {
        return Bucket4j.builder()
                // 10개의 클라이언트가 10초에 1000개씩 보낼 수 있는 대역폭
                .addLimit(Bandwidth.classic(1000, Refill.intervally(10, Duration.ofSeconds(10))))
                .build();
    }

    public Bucket apiBucket(HttpServletRequest httpServletRequest) {
        return cache.computeIfAbsent(getHost(httpServletRequest), this::newAPIBucket);
    }

    private Bucket newAPIBucket(String apiKey){
        return Bucket4j.builder()
                // 2개의 클라이언트가 30초에 10개씩 보낼 수 있는 대역폭
                .addLimit(Bandwidth.classic(10, Refill.intervally(2, Duration.ofSeconds(30))))
                .build();
    }
}
