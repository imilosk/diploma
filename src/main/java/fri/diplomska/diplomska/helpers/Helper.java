package fri.diplomska.diplomska.helpers;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Helper {

    public static String getProjectPath() {
        Path currentWorkingDir = Paths.get("").toAbsolutePath();
        return new File("").getAbsolutePath() + File.separator;
    }

}
