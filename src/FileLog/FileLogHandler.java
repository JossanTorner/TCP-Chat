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
        filePath = Path.of(filePath);
        Path filePath = Path.get(fileName)
        if(!Files.exists(filePath)) {
            Files.createFile(filePath);
        } else {
            writeToFile(filePath, userInfo);

        }
    }
    //hahahha nej det var jag, dör

    public static void writeToFile(String userInfo) {
        //har jag fått hjärnsläpp på riktigt
        //med gällande vad??
        //metoderna får helt enkelt använda sig av klassens redan förbestämda fil alltid
        //ja men det är väl inte konstigt? eller?
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            writer.write(userInfo);
            writer.newLine();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public static List<User> readLogFile() {
        List<Object> userInfo = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {


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
