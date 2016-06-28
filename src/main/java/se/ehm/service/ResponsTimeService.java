package se.ehm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Service
public class ResponsTimeService {

    private static final long ONE_WEEK = 7;
    private static final long ONE_DAY = 1;

    @Autowired
    DataSource oracleDatasource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL = "SELECT timespent FROM external_call WHERE stptimestamp > ?";

    public List<Map<String, Object>> getResponsTimesFromCurrentWeek() {
        System.out.println("Fetching response times from current week...");

        LocalDateTime localDateTime = LocalDateTime.now();
        long milliseconds = localDateTime.minusDays(ONE_WEEK).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Timestamp timestamp = new Timestamp(milliseconds);

        return jdbcTemplate.queryForList(SQL, timestamp);
    }

    public List<Map<String, Object>> getResponsTimesFromCurrentDay() {
        System.out.println("Fetching response times from current day...");

        LocalDateTime localDateTime = LocalDateTime.now();
        long milliseconds = localDateTime.minusDays(ONE_DAY).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Timestamp timestamp = new Timestamp(milliseconds);

        return jdbcTemplate.queryForList(SQL, timestamp);
    }
}
