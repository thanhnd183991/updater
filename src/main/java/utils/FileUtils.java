package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class FileUtils {

    public static File getCurrentFolderContainAppUsingDot() {
        return new File(".");
    }

    public static String getCurrentFolderContainAppUsingUserDir() {
        return System.getProperty("user.dir");
    }

    public static boolean overrideFile(String source, String dest) {
        try {
            Files.copy(Paths.get(source), Paths.get(dest).resolveSibling(getFileNameFromAbsPath(dest)), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }
    }

    public static String getFilePathInFolderContainApp(String fileName) {
        String folder = getCurrentFolderContainAppUsingUserDir();
        String filePath = folder + "\\" + fileName;
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return folder + "\\" + fileName;
    }

    public static String getFileNameFromAbsPath(String input) {
        return input.substring(input.lastIndexOf("\\") + 1);
    }

    public static boolean savePropFileFromFolderContainApp(Properties props, String fileName) {
        String filePath = getFilePathInFolderContainApp(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            props.store(fos, null);
            fos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Properties getPropFileFromFolderContainApp(String fileName) {
        String filePath = getFilePathInFolderContainApp(fileName);
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            // load properties from file
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // close objects
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

//
//    public static Properties getPropFileFromClasspath(String fileName){
//        Properties properties = new Properties();
//        InputStream inputStream = null;
//        try {
//            inputStream = FileUtils.class.getClassLoader()
//                    .getResourceAsStream(fileName);
//
//            // load properties from file
//            properties.load(inputStream);
//            return properties;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            // close objects
//            try {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return properties;
//    }
}
