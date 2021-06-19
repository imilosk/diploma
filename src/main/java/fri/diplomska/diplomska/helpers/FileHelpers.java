package fri.diplomska.diplomska.helpers;

import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileHelpers {

    /**
     * Creates a temp directory and returns it's absolute path
     *
     * @return String
     * @throws IOException Throws an IOException
     */
    public String createTempDirectory() throws IOException {
        Path tmpDirNoPrefix = Files.createTempDirectory(null);
        return tmpDirNoPrefix.toAbsolutePath().toString();
    }

    /**
     * Deletes a directory
     *
     * @param directoryPath Absolute path to the directory
     * @throws IOException Throws an IOException
     */
    public void deleteDirectory(String directoryPath) throws IOException {
        FileUtils.deleteDirectory(new File(directoryPath));
    }

    /**
     * Unzips the file in the given directory
     *
     * @param file The file to unzip
     * @param unzipPath Where to unzip the file
     * @throws IOException Throws an IOException
     */
    public void unzipFile(MultipartFile file, String unzipPath) throws IOException {
        Path filePathToSave = Paths.get(unzipPath, "archive.zip");

        // first save zip file to disk
        byte[] bytes = file.getBytes();
        Files.write(filePathToSave, bytes);

        // then unzip the file
        new ZipFile(filePathToSave.toString()).extractAll(unzipPath);
    }

}
