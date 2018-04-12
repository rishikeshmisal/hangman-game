package game.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobScheduler {

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("Right now the time is "+(new Date()));
    }
}
