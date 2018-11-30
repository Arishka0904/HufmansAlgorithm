package huffmansAlgorithm;

import java.util.Scanner;

public class FileNameReader {


    public static String readFileName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter name of the file ");
        String fileName = scanner.nextLine();
        scanner.close();
        return fileName;
    }

    public static Boolean isFileCompressed(String fileName) {
        String fileExtension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) fileExtension = fileName.substring((i), (fileName.length()));
        if (fileExtension.equals(".hf")) return true;
        return false;
    }
}


