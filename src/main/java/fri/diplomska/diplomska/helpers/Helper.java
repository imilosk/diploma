package fri.diplomska.diplomska.helpers;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Helper {

    public static String getProjectPath() {
        Path currentWorkingDir = Paths.get("").toAbsolutePath();
        return currentWorkingDir.normalize().toString() + "\\";
    }

}
