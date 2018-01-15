package pl.funnyqrz.tests.utils;

import org.junit.jupiter.api.Test;
import pl.funnyqrz.utils.resource.FilesUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;


public class FilesUtilsTest {

    @Test
    public void fileToByteArrayTest() throws IOException {
        // given
        File dummyFile = readDummyFile();

        // when
        byte[] dummyByteArray = FilesUtils.fileToByteArray(dummyFile);

        //then
        assertThat(dummyByteArray).isNotEmpty();
    }


    @Test
    public void nullFileToByteArrayTest() throws IOException {
        // given
        File dummyFile = null;

        // when
        byte[] dummyByteArray = FilesUtils.fileToByteArray(dummyFile);

        //then
        assertThat(dummyByteArray).isNullOrEmpty();
    }

    @Test
    public void fileToBlobTest() throws IOException, SQLException {
        // given
        File dummyFile = readDummyFile();

        // when
        Blob dummyBlob = FilesUtils.fileToBlob(dummyFile);

        // then
        assertThat(dummyBlob).isNotNull();
    }

    @Test
    public void nullFileToBlobTest() throws IOException, SQLException {
        // given
        File dummyFile = null;

        // when
        Blob dummyBlob = FilesUtils.fileToBlob(dummyFile);

        // then
        assertThat(dummyBlob).isNull();
    }

    private File readDummyFile() {
        Path resourceDirectory = Paths.get("src", "test", "resources", "test.txt");
        return resourceDirectory.toFile();
    }

}
