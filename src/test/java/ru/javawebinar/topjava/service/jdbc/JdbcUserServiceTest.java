package ru.javawebinar.topjava.service.jdbc;

import org.junit.Before;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.Objects;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    @Before
    public void setup() {
        Objects.requireNonNull(cacheManager.getCache("users")).clear();
    }
}