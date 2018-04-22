package game.schedule;

import game.repository.IMovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobScheduler {

    @Autowired
    IMovieRepo iMovieRepo;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {

        System.out.println("Right now the count is"+ iMovieRepo.findCount());
    }
}
