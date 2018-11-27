package huffmansAlgorithm;

public class Main {
    public static void main(String[] args) {
        FilePathReader filePath = new FilePathReader();


        String path = filePath.readPath();
        String fileExtension = filePath.getFileExtension(path);

        if (fileExtension.equals("hf")) {
            DeCompressor deCompressor = new DeCompressor(path);

        } else if (fileExtension.equals("???")) { //todo
         //   Compressor compressor = new Compressor();//Не хранить путь в объекте


            System.out.println(path);
            System.out.println(fileExtension);
        }
    }
}

