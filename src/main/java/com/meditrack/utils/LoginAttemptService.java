javapackage com.meditrack.utils;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LoginAttemptService {

    private static final int MAX_TENTATIVES = 5;
    private final Map<String, AtomicInteger> tentatives = new ConcurrentHashMap<>();

    public void echec(String email) {
        tentatives.computeIfAbsent(email, k -> new AtomicInteger(0))
                  .incrementAndGet();
    }

    public void succes(String email) {
        tentatives.remove(email);
    }

    public boolean estBloque(String email) {
        AtomicInteger count = tentatives.get(email);
        return count != null && count.get() >= MAX_TENTATIVES;
    }
}
