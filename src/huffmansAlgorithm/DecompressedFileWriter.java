package huffmansAlgorithm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static huffmansAlgorithm.FileNameCreator.createKeyFileName;
import static huffmansAlgorithm.FileNameCreator.createNameOfDecompressedFile;

public class DecompressedFileWriter {

    public static void writeDecompressedResultToFile(DecompressorResult decompressorResult) {

        try (FileOutputStream fos = new FileOutputStream(createNameOfDecompressedFile(decompressorResult.getFileName()))) {
            fos.write(decompressorResult.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(decompressorResult.getFileName());
        file.delete();
        file = new File(createKeyFileName(decompressorResult.getFileName()));
        file.delete();
    }
}
