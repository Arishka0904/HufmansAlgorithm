package huffmansAlgorithm;

import java.util.Scanner;

public class FilePathReader {


    public String readPath() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file path ");
        String path = scanner.nextLine();
        scanner.close();
        return path;
    }

    public  String getFileExtension(String path) {

        String fileExtension = "";
        int i = path.lastIndexOf('.');
        if (i > 0) fileExtension = path.substring(i + 1);
        return fileExtension;
    }
}
