package fri.diplomska.diplomska.helpers;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Helper {

    @Value("${upload.path}")
    private static String path;

    public static String getFileUploadFolder(HttpServletRequest request) {
        return "/var/lib/tomcat9/upload/";
    }

}
