package se.ehm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.ehm.service.ResponsTimeService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/responsetime", method = RequestMethod.GET)
public class ResponseTimeController {

    /** Database column name in external_call. */
    public static final String TIMESPENT = "TIMESPENT";

    @Autowired
    ResponsTimeService responseTimeService;

    @CrossOrigin(origins = "http://localhost:63342")
    @RequestMapping(value="/week", method = RequestMethod.GET)
    public String getAverageResponsTimeForCurrentWeek() {

        List<Map<String, Object>> responseTimes = responseTimeService.getResponsTimesFromCurrentWeek();
        long averageResponseTime = calculateAverageResponseTime(responseTimes);
        System.out.println("Response weekly: " + "&fd_dial=" + averageResponseTime);

        return "&fd_dial=" + averageResponseTime;
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @RequestMapping(value="/day", method = RequestMethod.GET)
    public String getAverageResponsTimeForCurrentDay() {

        List<Map<String, Object>> responseTimes = responseTimeService.getResponsTimesFromCurrentDay();
        System.out.println();
        long averageResponseTime = calculateAverageResponseTime(responseTimes);

        System.out.println("Response daily: " + "&fd_dial=" + averageResponseTime);

        return "&fd_dial=" + averageResponseTime;
    }

    private long calculateAverageResponseTime(List<Map<String, Object>> responseTimes) {
        Double average = responseTimes.stream().collect(Collectors.averagingInt(s -> Integer.valueOf(s.get(TIMESPENT).toString())));
        return average.longValue();
    }
}
