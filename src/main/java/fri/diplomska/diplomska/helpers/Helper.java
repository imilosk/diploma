package fri.diplomska.diplomska.helpers;

import org.apache.commons.lang.SystemUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Helper {

    public static String getProjectPath() {
        if (SystemUtils.IS_OS_WINDOWS) {
            Path currentWorkingDir = Paths.get("").toAbsolutePath();
            return Paths.get(currentWorkingDir.normalize().toString(), "docker").toString() + File.separator;
        }
        return "/tmp/";
    }

}
