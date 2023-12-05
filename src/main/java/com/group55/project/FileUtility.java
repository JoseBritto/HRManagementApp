package com.group55.project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtility {
    
    public static boolean saveToFile(String fileName, Object object) {

        try {
            if(!fileName.endsWith(".txt")) {
                fileName += ".txt";
            }
            
            var fileOutputStream = new FileOutputStream(fileName);
            var objectOutputStream = new java.io.ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    
    public static Object loadFromFile(String fileName) {
        try {
            if(!fileName.endsWith(".txt")) {
                fileName += ".txt";
            }
            
            var fileInputStream = new java.io.FileInputStream(fileName);
            var objectInputStream = new java.io.ObjectInputStream(fileInputStream);
            var object = objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return object;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
    
}
