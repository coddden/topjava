package ru.javawebinar.topjava.repository.jdbc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

@Repository
@Profile(Profiles.HSQL_DB)
public class HsqlDbJdbcMealRepository extends AbstractJdbcMealRepository<Timestamp> {

    public HsqlDbJdbcMealRepository(JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    protected Timestamp convertToDbTime(LocalDateTime time) {
        return time == null ? null : Timestamp.valueOf(time);
    }
}
