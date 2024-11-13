package FileLog;

import client.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileLogHandler implements Serializable {

    private static User userInfo;
    private static String filename = "UserLog.txt";
    private static boolean online;


    public FileLogHandler(User userInfo) {
        this.userInfo = userInfo;
        this.online = false;

    }

    public static void createFile(User user) throws IOException {
        Path filePath = Path.of(filename);
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        } else { //har noll koll hahaha jag ba varför tar inte den här metoden en fil nu då.
            //precis vad jag kom hit för att ändra så den inte gjorde hahahahaha
            //det här är SÅ roligt. Alltså min highlight idag hahaha jag tror vi är klara här just nu.
            //SÅ vad vill d göra nu här i? xd
            //writeToFile(userInfo);

        }
    }

    public static void writeObjectToFile(User userInfo) {
        try (FileOutputStream fileOut = new FileOutputStream("users.ser");
             ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(String.valueOf(fileOut)))) {
            objectOut.writeObject(userInfo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeObjectToFile2(User userInfo) {
        try (FileOutputStream fileOut = new FileOutputStream("src/users.ser");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {  // Use fileOut directly here
            objectOut.writeObject(userInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //chatgpt sa att vår metod borde se ut såhär

    /*public static void writeToFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(logLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

   /* public static String logLine() {
        return userInfo + (online ? "Offline " : " Online ");

    }*/

    /*
    public static List<User> readObjectFile(){
        List<User> userInfo = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream("server/users.ser");
             ObjectInputStream objectIn = new ObjectInputStream((fileIn)) {
            while(true)
                 {
                     try {
                         Object readObject = objectIn.readObject();
                         if (readObject instanceof User user) {
                             userInfo.add(user);
                         }
                         break;
                     } catch (IOException e) {
                         throw new RuntimeException(e);
                     } catch (ClassNotFoundException e) {
                         throw new RuntimeException(e);
                     }

                 }
             }

             return userInfo;


    }*/

    public static List<User> readObjectFile() {
        List<User> userInfo = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream("server/users.ser");
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            while (objectIn.readObject() instanceof User user) {
                userInfo.add(user);
            }
        } catch (EOFException e) { //ja för när vi öppnar chattfönstret //fastnar den på detta "error" alltså?
            System.out.println("Hit end of File "); ///hit kommer vi för det här printas ut.
            return userInfo;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return userInfo;
    }

}
