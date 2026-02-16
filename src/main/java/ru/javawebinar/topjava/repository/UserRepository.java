package ru.javawebinar.topjava.repository;

import java.util.List;
import ru.javawebinar.topjava.model.User;

public interface UserRepository {
    
    User save(User user);

    User get(int id);

    User getByEmail(String email);
    
    List<User> getAll();

    boolean delete(int id);
}