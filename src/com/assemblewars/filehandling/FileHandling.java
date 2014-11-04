package com.assemblewars.filehandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandling {

    public static String readLine(int number,String path) {

        FileHandle file = Gdx.files.internal(path);
        BufferedReader reader = new BufferedReader(file.reader());
        String line = "";
        for (int i = 0; i < (number)+1; i++) {
            try {
                line = reader.readLine();
            } catch (IOException ex) {
                Logger.getLogger(FileHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return line;
    }
}
