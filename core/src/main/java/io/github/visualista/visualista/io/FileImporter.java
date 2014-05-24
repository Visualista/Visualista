package io.github.visualista.visualista.io;

import java.io.File;
import java.util.Date;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public enum FileImporter {
    ;
    private static final int MAX_RANDOM_INT = 1000;
    private static final String DATE_FORMAT = "yyyyMMdd_HHmmss-SSS";

    public static FileHandle importAndGetFile(final File file) {
        if (file != null && !file.isDirectory()) {

            String fileName = file.getName();
            String fileExtension = "";

            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                fileExtension = fileName.substring(i + 1);

                FileHandle newPosition = Gdx.files.local(folderString()
                        + fileName);
                while (newPosition.exists()) {
                    newPosition = Gdx.files.local(folderString()
                            + generateRandomFileName(fileExtension));
                }
                Gdx.files.absolute(file.getAbsolutePath()).copyTo(newPosition);
                return newPosition;
            }
        }
        return null;
    }

    private static String generateRandomFileName(final String fileExtension) {
        String timeStamp = generateTimeStamp();
        return timeStamp + "_" + new Random().nextInt(MAX_RANDOM_INT) + "."
        + fileExtension;
    }

    private static String generateTimeStamp() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                DATE_FORMAT);
        return sdf.format(new Date());

    }

    private static String folderString() {
        return "files" + File.separator;
    }
}
