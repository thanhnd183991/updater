import constant.UpdaterConstant;
import events.OverrideEvent;
import events.ProcessOverrideFileListenerImpl;
import utils.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static void runCmd(String cmd) {
        System.out.println(cmd);
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec("cmd /c start /b " + cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Thread.sleep(2000);
        Properties projectProps = FileUtils.getPropFileFromFolderContainApp(UpdaterConstant.PROJECT_PROPS);
        String newVersion = projectProps.getProperty("newVersion");
        String oldVersion = projectProps.getProperty("oldVersion");
        if (oldVersion == null && newVersion == null) {
            System.out.println(UpdaterConstant.NOT_FOUND_VERSION_IN_VERSION_FILE_MESSAGE);
            return;
        }
        if (newVersion.equals(oldVersion)) {
            System.out.println(UpdaterConstant.DUPLICATE_VERSION_MESSAGE);
            Main.runCmd("java -jar ./" + UpdaterConstant.APP_JAR_FILE);
            return;
        }
        String destPath = FileUtils.getFilePathInFolderContainApp(UpdaterConstant.APP_JAR_FILE);
        String sourcePath = FileUtils.getFilePathInFolderContainApp(UpdaterConstant.TEMP_APP_JAR_FILE);
        boolean success = FileUtils.overrideFile(sourcePath, destPath);
        if (success) {
            System.out.println("success override");
            //delete temp file
            try {
                File file = new File(sourcePath);
                boolean deleteTempFile = file.delete();
                if (deleteTempFile) {
                    System.out.println("deleted " + UpdaterConstant.TEMP_APP_JAR_FILE);
                } else {
                    System.out.println("cannot delete " + UpdaterConstant.TEMP_APP_JAR_FILE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // save file project.properties
            projectProps.put("oldVersion", newVersion);
            FileUtils.savePropFileFromFolderContainApp(projectProps, UpdaterConstant.PROJECT_PROPS);
            Main.runCmd("java -jar ./" + UpdaterConstant.APP_JAR_FILE);
            return;
        } else {
            JOptionPane.showMessageDialog(new JFrame(), UpdaterConstant.ERROR_WHEN_OVERRIDE_FILE_MESSAGE, UpdaterConstant.UPDATE_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }
}
