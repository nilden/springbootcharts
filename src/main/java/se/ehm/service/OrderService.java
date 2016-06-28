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
public class OrderService {

    private static final long ONE_WEEK = 7;
    private static final long ONE_DAY = 1;
    private static final String SQL = "SELECT timespent, userid, subsystem, servicename FROM external_call WHERE stptimestamp > ?";

    @Autowired
    DataSource oracleDatasource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getOrdersFromCurrentDay() {
        System.out.println("Fetching orders from current week...");

        LocalDateTime localDateTime = LocalDateTime.now();
        long milliseconds = localDateTime.minusDays(ONE_DAY).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Timestamp timestamp = new Timestamp(milliseconds);

        return jdbcTemplate.queryForList(SQL, timestamp);
    }

    public List<Map<String, Object>> getOrdersFromCurrentWeek() {
        System.out.println("Fetching orders from current week...");

        LocalDateTime localDateTime = LocalDateTime.now();
        long milliseconds = localDateTime.minusDays(ONE_WEEK).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Timestamp timestamp = new Timestamp(milliseconds);

        return jdbcTemplate.queryForList(SQL, timestamp);
    }
}
