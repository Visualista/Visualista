package io.github.visualista.visualista.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.util.Date;
import java.util.Random;

public enum FileImporter {
    ;

    public static FileHandle importAndGetFile(File file) {
        if (file == null || file.isDirectory()) {
            return null;
        }
        String fileName = file.getName();
        String fileExtension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            fileExtension = fileName.substring(i + 1);
        } else {
            return null;
        }

        FileHandle newPosition = Gdx.files.local(fileName);
        while (newPosition.exists()) {
            newPosition = Gdx.files
                    .local(generateRandomFileName(fileExtension));
        }
        Gdx.files.absolute(file.getAbsolutePath()).copyTo(newPosition);
        return newPosition;
    }

    private static String generateRandomFileName(String fileExtension) {
        String timeStamp = generateTimeStamp();
        return timeStamp + "_" + new Random().nextInt(999)+"."+fileExtension;
    }

    private static String generateTimeStamp() {

        String DATE_FORMAT = "yyyyMMdd_HHmmss-SSS";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                DATE_FORMAT);
        return sdf.format(new Date());

    }
}