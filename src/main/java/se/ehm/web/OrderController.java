package se.ehm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.ehm.service.OrderService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/orders", method = RequestMethod.GET)
public class OrderController {
    String SQL = "SELECT timespent, userid, subsystem, servicename FROM external_call WHERE stptimestamp > ?";

    @Autowired
    DataSource oracleDatasource;

    @Autowired
    OrderService orderService;

    private long lastFetched;
    private long databaseTimeOffset;
    private long receptSinceMidnight_value = 0;
    private long receptSinceMidnight_lastFetched = 0;


    @RequestMapping(value="/day", method = RequestMethod.GET)
    public List<Map<String, Object>> getDaily() {
        return orderService.getOrdersFromCurrentDay();
    }

    @RequestMapping(value="/week", method = RequestMethod.GET)
    public List<Map<String, Object>> getAverageResponsTimeForCurrentWeek() {
        return orderService.getOrdersFromCurrentWeek();
    }



    private long synchronizeDatabaseTime() throws SQLException {
        Connection connection = oracleDatasource.getConnection();
        PreparedStatement stmt = connection.prepareStatement("select systimestamp from dual");

        long localTime = System.currentTimeMillis();
        ResultSet rs = stmt.executeQuery();
        long latency = System.currentTimeMillis() - localTime;

        rs.next();
        long databaseTime = rs.getTimestamp(1).getTime();

        long offset = localTime - databaseTime;


        return offset;
    }
}
