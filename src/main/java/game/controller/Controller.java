package game.controller;

import com.sun.deploy.net.HttpResponse;
import game.service.Hangman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/hangman-game")
public class Controller {

    @Autowired
    Hangman hangman;

    @RequestMapping(
            value="/data-upload",
            method = RequestMethod.POST)
    public void uploadToDB(@RequestParam(value="file-name")String fileName) throws IOException {
        hangman.saveToDB(fileName);

    }

    @RequestMapping(
            value="/play-game",
            method = RequestMethod.POST)
    public void play() throws IOException {
        hangman.playGame();

    }

}
