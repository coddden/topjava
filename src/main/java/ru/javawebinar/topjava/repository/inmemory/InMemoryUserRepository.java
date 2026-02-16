package ru.javawebinar.topjava.repository.inmemory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

@Repository
public class InMemoryUserRepository implements UserRepository {
    
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private final Map<Integer, User> userMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            userMap.put(user.getId(), user);
            return user;
        }
        return userMap.computeIfPresent(user.getId(), (id, oldMeal) -> user);
    }
    
    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userMap.get(id);
    }
    
    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return userMap.values().stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return userMap.values().stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return userMap.remove(id) != null;
    }
}
