package FileLog;

import client.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileLogHandler {

    private static User userInfo;
    private static String filename = "UserLog.txt";
    private static boolean online;


    public FileLogHandler(User userInfo) {
        this.userInfo = userInfo;
        this.online = false;

    }

    public static void createFile(User user) throws IOException {
        Path filePath = Path.of(filename);
        if(!Files.exists(filePath)) {
            Files.createFile(filePath);
        } else { //har noll koll hahaha jag ba varför tar inte den här metoden en fil nu då.
            //precis vad jag kom hit för att ändra så den inte gjorde hahahahaha
            //det här är SÅ roligt. Alltså min highlight idag hahaha jag tror vi är klara här just nu.
            //SÅ vad vill d göra nu här i? xd
            writeToFile(userInfo);

        }
    }

    public static void writeObjectToFile(User userInfo) {
        try (FileOutputStream fileOut = new FileOutputStream("server/users.dat");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(userInfo);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void writeToFile(User userInfo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(logLine());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static List<User> readObjectFile(){
        List<User> userInfo = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream("server/users.dat");
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            while (true) {
                try {
                    Object readObject = objectIn.readObject();
                    if (readObject instanceof User user) {
                        userInfo.add(user);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

   /* public static List<User> readLogFile() {
        List<User> userInfo = new ArrayList<>();
        try (

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userInfo;
    }*/


    public static String logLine(){
        return userInfo + (online ?  "Offline " : " Online ");
    }
}
