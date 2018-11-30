package huffmansAlgorithm;

public class FileNameCreator {


    public static String getFileNameWithoutExtension(String fileName) {
        String fileWithoutExtension = "";
        int i = fileName.indexOf('.');
        if (i == -1) fileWithoutExtension = fileName;
        else fileWithoutExtension = fileName.substring(0, i);
        return fileWithoutExtension;
    }


    public static String createKeyFileName(String fileName) {
        String keyFileName = getFileNameWithoutExtension(fileName) + ".key";
        return keyFileName;
    }


    public static String createNameOfCompressedFile(String fileName) {
        String newFileName = fileName + ".hf";
        return newFileName;
    }


    public static String createNameOfDecompressedFile(String fileName) {
        String newFileName = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) newFileName = fileName.substring(0, i);
        return newFileName;
    }
}
