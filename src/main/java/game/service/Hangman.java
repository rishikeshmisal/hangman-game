package game.service;

import com.opencsv.CSVReader;
import game.entity.Movie;
import game.repository.IMovieRepo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

@Service
public class Hangman {

    @Autowired
    IMovieRepo iMovieRepo;

    public  void play(String args[]) throws Exception {
       // BufferedReader bufferedReader = null;
        try {
            //FileReader fileReader = null;
            //bufferedReader = new BufferedReader(new FileReader("C:\\rishi\\moviesList.txt"));
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            /*if(bufferedReader!=null) {
                bufferedReader.close();
            }*/
            /*if (bufferedReader == null) {
                System.out.println("File not found");
                System.exit(1);
            }*/
        }

        ArrayList<Movie> arrayList = new ArrayList<Movie>();

        //String line = bufferedReader.readLine();


        /*while (line != null) {
            line.trim();
            StringTokenizer stringTokenizer = new StringTokenizer(line, "(");
            Movie m = new Movie();
            m.name = stringTokenizer.nextToken();
            String yr = stringTokenizer.nextToken();
            m.year = Integer.parseInt(yr.substring(0, yr.indexOf(")")));
            arrayList.add(m);
            line = bufferedReader.readLine();
        }*/

       // playGame(arrayList);

    }

    public  void playGame() {
        Scanner src = new Scanner(System.in);
        Random random = new Random();
        System.out.println("Type Exit to end the game");
        System.out.println("Type hint to get the hint!");
        String response = "";
        while (!response.toLowerCase().equals("exit")) {
            int index = random.nextInt(7896);
            Movie m = iMovieRepo.findById(index);//arrayList.get(index);
            char movie[] = new char[m.name.length()];
            movie = buildArray(movie, m.name);
            int wrong = 0;
            boolean won = false;
            boolean hint = false;
            while (wrong < 6 && !won) {
                printMovie(movie, wrong);
                String input = src.nextLine();
                input = input.toUpperCase();
                while (input.length() > 1) {
                    if (input.toUpperCase().equals("HINT")) {
                        System.out.println("This movie was released in " + m.year + " \t Good Luck!");
                        hint = true;
                        System.out.println("Enter you attempt");
                    } else {
                        System.out.println("You cannot enter more than one character at a time! Try again.");
                    }
                    input = src.nextLine();
                }
                int correct = checkChar(input, movie, m.name);
                if (correct > 0) {
                    if (checkWin(movie)) {
                        won = true;
                        System.out.println("You Win!!!");
                        System.out.println("Movie name = " + m.name);
                        continue;
                    } else {
                        System.out.println("Found " + correct + " character slots");
                    }
                } else {
                    wrong++;
                    System.out.println("Bad Luck! Found nothing");
                }

            }
            if (!won) {
                System.out.println("Uh oh! you lost. the movie was = " + m.name);
            }
            System.out.println("Do you wanna play more? If No enter exit otherwise enter anything else!");
            response = src.nextLine();
        }
    }

    private static boolean checkWin(char[] movie) {

        for (char c : movie) {
            if (c == '_')
                return false;
        }
        return true;
    }

    private static int checkChar(String input, char[] movie, String name) {
        int correct = 0;
        name = name.toUpperCase();
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == input.charAt(0)) {
                movie[i] = input.charAt(0);
                correct++;
            }
        }
        return correct;
    }

    private static void printMovie(char[] movie, int wrong) {
        for (char c : movie) {
            System.out.print(c + " ");
        }
        System.out.println("\n Attempts Left = " + (6 - wrong));
    }

    private static char[] buildArray(char[] movie, String name) {
        name = name.toUpperCase();
        char ret[] = name.toCharArray();

        for (int i = 0; i < ret.length; i++) {
            if (ret[i] >= 65 && ret[i] <= 90) {
                ret[i] = '_';
            } else {
                if (ret[i] == ' ') {
                    ret[i] = '|';
                }
            }
        }
        return ret;
    }

    public void saveToDB(String fileName) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row;
        String line = bufferedReader.readLine();
        line = bufferedReader.readLine();

        CSVReader csvReader = new CSVReader(new FileReader(fileName));

        /*while(line!=null){
            StringTokenizer stringTokenizer = new StringTokenizer(line,",");
            String id = stringTokenizer.nextToken();

            String name = stringTokenizer.nextToken().trim();

            int year = Integer.parseInt(name.substring(name.lastIndexOf('(')+1,name.lastIndexOf(")")));

            name = name.substring(0,name.indexOf('('));

            String genre = stringTokenizer.nextToken();

            Movie movie = new Movie();

            movie.genre = genre;
            movie.name = name;
            movie.year = year;
            iMovieRepo.save(movie);

            line = bufferedReader.readLine();

        }*/

        int rows; // No of rows
        rows = sheet.getPhysicalNumberOfRows();

        String name;
        int year;
        String genre;
        for (int i = 1; i < rows; i++) {
            row = sheet.getRow(i);
            if (row != null) {
                    HSSFCell hssfCell = row.getCell(1);

                            name = hssfCell.getStringCellValue().trim();
                            name = preprocess(name);

                            if(name!=null){
                                 year = Integer.parseInt(name.substring(name.lastIndexOf('(')+1,name.lastIndexOf(")")));

                                name = name.substring(0,name.indexOf('('));
                                hssfCell = row.getCell(2);

                                genre = hssfCell.getStringCellValue().trim();

                                Movie movie = new Movie();

                                movie.genre = genre;
                                movie.name = name;
                                movie.year = year;
                                iMovieRepo.save(movie);
                            }

            }

        }
    }

    private String preprocess(String name) {

        //check if there are more than one (
        //and check if the characters are permitted or not.

        boolean bracket = false;
        for(char c: name.toCharArray()){
            if((c>='A' && c<='Z' ) || (c>='a' && c<='z') || (c>='0' && c<='9') || c=='?' || c==',' || c=='.' || c=='!' || c==' ' || c=='(' || c==')' || c=='\'' || c==':'){

            }else{
                return null;
            }
            if(c=='('){
                if(bracket){
                    return null;
                }
                bracket=true;
            }
        }


        return name;

    }
}

