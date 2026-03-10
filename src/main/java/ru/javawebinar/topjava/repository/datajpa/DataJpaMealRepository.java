package ru.javawebinar.topjava.repository.datajpa;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

@Repository
public class DataJpaMealRepository implements MealRepository {
    
    private final CrudMealRepository crudRepository;
    private final CrudUserRepository userRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository, CrudUserRepository userRepository) {
        this.crudRepository = crudRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setUser(userRepository.getReferenceById(userId));
            return crudRepository.save(meal);
        }
        return crudRepository.update(
                meal.id(),
                userId,
                meal.getDateTime(),
                meal.getDescription(),
                meal.getCalories()) == 0 ? null : meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.findById(id).filter(meal -> meal.getUser().getId() == userId).orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAllByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.getBetweenHalfOpen(startDateTime, endDateTime, userId);
    }
}
