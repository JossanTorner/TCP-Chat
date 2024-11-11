package FileLog;

import client.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileLogHandler {

    private String userInfo;
    private static String filename = "userLog.txt";


    public void createFile() throws IOException {
        Path filePath = Path.of(filename);
        if(!Files.exists(filePath)) {
            Files.createFile(filePath);
        } else { //har noll koll hahaha jag ba varför tar inte den här metoden en fil nu då.
            //precis vad jag kom hit för att ändra så den inte gjorde hahahahaha
            //det här är SÅ roligt. Alltså min highlight idag hahaha jag tror vi är klara här just nu. 
            //SÅ vad vill d göra nu här i
            writeToFile(userInfo);

        }
    }

    public static void writeToFile(String userInfo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(userInfo);
            writer.newLine();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public static List<User> readLogFile() {
        List<Object> userInfo = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //klient som connectar till server ska loggas i fil med request Connected/disconnected?


        //Söka efter klient med "nyckel" Connected/Disconnected? Lägga i en lista att loopa igenom och skriva ut?

        //hahahaha KÖÖÖR jag behöver ändå lära mig hashmaps
        //Hämta alla klienter från lista till hashmap med request som nyckel och på så sätt skriva ut?  (ja jag gillar fortfarande hashmap hehe)
        return userInfo;
    }
}
