package ru.gurkin.spring.journal.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@Component
public class DayTimeHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        int errorCode = check(); // выполняем определенную проверку работоспособности
        if (errorCode != 0) {
            return Health.down().withDetail("Time to sleep", errorCode).build();
        }
        return Health.up().build();
    }

    private int check(){
        Instant currentTime = Instant.now();
        LocalDateTime ldt = LocalDateTime.ofInstant(currentTime, ZoneId.systemDefault());
        int hour = ldt.getHour();
        OptionalInt result = IntStream.range(9, 21).filter(i -> i == hour).findAny();
        if (result.isPresent()){
            return 0;
        }else {
            return hour;
        }
    }
}