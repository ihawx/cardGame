package com.assemblewars.filehandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandling {

    public static String readLine(int number, String path) {

        FileHandle file = Gdx.files.internal(path);
        BufferedReader reader = new BufferedReader(file.reader());
        String line = "";
        for (int i = 0; i < (number) + 1; i++) {
            try {
                line = reader.readLine();
            } catch (IOException ex) {
                Logger.getLogger(FileHandling.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return line;
    }

    public static int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }
    
    public static int countChars(char c,String s){
        int count=0;
        for(int i = s.indexOf(Character.toString(c)); i<s.lastIndexOf(Character.toString(c))+1;i++){
            if(String.valueOf(s.charAt(i)).equals(Character.toString(c))){
                count++;
            }
        }
        return count;
    }
}
