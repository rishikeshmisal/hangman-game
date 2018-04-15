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

@RestController
@RequestMapping("/hangman-game")
public class Controller {

    @Autowired
    Hangman hangman;

    @RequestMapping(
            value="/data-upload",
            method = RequestMethod.POST)
    public void uploadToDB(@RequestParam(value="file")MultipartFile file){
        if(file.getSize()==0){
            System.out.println("File empty!");
            return;
        }
        hangman.saveToDB(file);
    }

}
