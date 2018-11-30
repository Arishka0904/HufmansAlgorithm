package huffmansAlgorithm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static huffmansAlgorithm.Bit.ONE;
import static huffmansAlgorithm.Bit.ZERO;
import static huffmansAlgorithm.FileNameCreator.createKeyFileName;
import static huffmansAlgorithm.FileNameCreator.createNameOfCompressedFile;

public class CompressedFileWriter {

    private CompressedFileWriter() {

    }

    public static void writeCompressedFile(CompressionResult compressionResult) {

        CompressedFileWriter compressedFileWriter = new CompressedFileWriter();
        compressedFileWriter.writeToFile(compressionResult);
        compressedFileWriter.writeToFileDictionaryTable(compressionResult);
    }


    public void writeToFile(CompressionResult compressionResult) {

        String newFileName = createNameOfCompressedFile(compressionResult.getFileName());

        try (FileOutputStream fos = new FileOutputStream(newFileName)) {
            int bitsCounter = 0;

            fos.write(Integer.toBinaryString(compressionResult.getBytes().size()).getBytes());
            fos.write(String.valueOf('2').getBytes());

            Bit[] concreteByte = new Bit[8];

            for (Bit bit : compressionResult.getBytes()) {
                concreteByte[bitsCounter++] = bit;
                if (bitsCounter == 8) {
                    fos.write(convertToInteger(concreteByte));
                    bitsCounter = 0;
                }
            }
            if (bitsCounter > 0) {
                while (bitsCounter < 8) concreteByte[bitsCounter++] = ZERO;
                fos.write(convertToInteger(concreteByte));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int convertToInteger(Bit[] bits) {

        StringBuilder stringOfBits = new StringBuilder("");

        for (Bit bit : bits) {
            if (bit == ONE) stringOfBits.append("1");
            else stringOfBits.append("0");
        }
        return Integer.parseInt(stringOfBits.toString(), 2);
    }


    public void writeToFileDictionaryTable(CompressionResult compressionResult) {

        String keyFileName = createKeyFileName(compressionResult.getFileName());

        try (FileOutputStream fos = new FileOutputStream(keyFileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(compressionResult.getDictionaryTable());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}



