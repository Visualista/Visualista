package io.github.visualista.visualista.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public enum FolderZipper {
    ;
    private static final int BUFFER_SIZE = 1024;

    public static void zipFolder(final File folder, final File outputFile)
            throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                outputFile));
        addFileOrFolderToZip(folder, out);
        out.close();
    }

    public static void addFileOrFolderToZip(final File dirObj,
            final ZipOutputStream out) throws IOException {
        addFileOrFolderToZip(dirObj, out, "");
    }

    static void addFileOrFolderToZip(final File dirObj,
            final ZipOutputStream out, String folderPath) throws IOException {
        File[] files = dirObj.listFiles();
        byte[] tmpBuf = new byte[BUFFER_SIZE];

        for (File file : files) {
            if (file.isDirectory()) {
                addFileOrFolderToZip(file, out, folderPath + file.getName()
                        + File.separator);
                continue;
            }
            FileInputStream in = new FileInputStream(file.getAbsolutePath());
            out.putNextEntry(new ZipEntry(folderPath + file.getName()));
            int len;
            while ((len = in.read(tmpBuf)) > 0) {
                out.write(tmpBuf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
    }
}
