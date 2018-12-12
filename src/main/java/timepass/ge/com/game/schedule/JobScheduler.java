package timepass.ge.com.game.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import timepass.ge.com.game.repository.IMovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import timepass.ge.com.game.service.Hangman;

import java.io.IOException;
import java.util.Scanner;

@Component
public class JobScheduler {

    private static final String fileName = "movies.xls";

    @Autowired
    IMovieRepo iMovieRepo;

    @Autowired
    Hangman hangman;

    @Scheduled(fixedDelay = 1243252454)
    public void reportCurrentTime() throws IOException {
        Scanner src = new Scanner(System.in);
        hangman.saveToDB(fileName);
        System.out.println("Right now the count is"+ iMovieRepo.findCount());
        do {
            hangman.playGame();
            String input = src.nextLine();
            while (!input.toLowerCase().contains("play")) {
                input = src.nextLine();
            }
        }while(true);
    }
}
