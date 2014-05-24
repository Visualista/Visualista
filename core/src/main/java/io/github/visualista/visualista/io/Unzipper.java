package io.github.visualista.visualista.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public enum Unzipper {
    ;

    private static final int BUFFER_SIZE = 1024;

    public static void unzip(File zipFile, File outputFolder)
            throws IOException {
        if (!outputFolder.exists()) {
            if (!outputFolder.mkdir()) {
                throw new IOException(
                        "Could not create directory for outputFolder");
            }
        } else if (outputFolder.isFile()) {
            throw new IllegalArgumentException("outputFolder can not be a file");
        } else {
            for (File c : outputFolder.listFiles())
                delete(c);
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile));
        extractFolder(outputFolder, zipIn, "");
        zipIn.close();
    }

    private static void extractFolder(File outputFolder, ZipInputStream zipIn,
            String folderPath) throws IOException {
        ZipEntry entry = zipIn.getNextEntry();
        while (entry != null) {
            System.out.println("" + entry);
            File filePath = new File(outputFolder + File.separator
                    + entry.getName());
            filePath.getParentFile().mkdirs();
            if (!entry.isDirectory()) {
                extractFile(zipIn, filePath);
            } else {
                filePath.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
    }

    private static void extractFile(ZipInputStream zipIn, File filePath)
            throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    static void delete(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : f.listFiles())
                delete(c);
        }
        if (!f.delete())
            throw new FileNotFoundException("Failed to delete file: " + f);
    }
}
