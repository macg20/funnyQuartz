package pl.funnyqrz.utils.resource;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Blob;
import java.sql.SQLException;

public class FilesUtils {

    private final static byte[] fileToByteArray(File file) throws IOException {
        if (file == null)
            return null;
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        byte[] byteArray = new byte[(int) randomAccessFile.length()];
        randomAccessFile.readFully(byteArray);
        return byteArray;
    }

    public final static Blob fileToBlob(File file) throws IOException, SQLException {
        if (file == null)
            return null;
        return new SerialBlob(fileToByteArray(file));
    }
}
