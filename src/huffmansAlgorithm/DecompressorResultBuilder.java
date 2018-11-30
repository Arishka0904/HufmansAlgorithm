package huffmansAlgorithm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static huffmansAlgorithm.Bit.ONE;
import static huffmansAlgorithm.Bit.ZERO;
import static huffmansAlgorithm.FileNameCreator.createKeyFileName;

public class DecompressorResultBuilder {
    private String fileName = "";
    private List<Bit> bits = new ArrayList<>();
    private List<Integer> decompressedResult = new ArrayList<>();
    private Map<Integer, List<Bit>> dictionaryTable = new TreeMap<>();
    private byte[] bytes;

    public DecompressorResultBuilder(String fileName) {
        this.fileName = fileName;
    }

    DecompressorResultBuilder loadDictionaryTable() {

        String keyFileName = createKeyFileName(fileName);

        try (FileInputStream fis = new FileInputStream(keyFileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            dictionaryTable = (Map<Integer, List<Bit>>) ois.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }

    DecompressorResultBuilder loadBitsFromCompressedFile() {

        List<Bit> bitsCounter = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(fileName)) {

            int tempRead = fis.read();

            while (tempRead != 50) {
                if (tempRead == 49) bitsCounter.add(ONE);
                else bitsCounter.add(ZERO);
                tempRead = fis.read();

            }
            int lengthOfResult = convertBitsListToInt(bitsCounter);

            while (fis.available() > 0) {
                String bin = Integer.toBinaryString(fis.read());

                while (bin.length() < 8) {
                    bin = "0" + bin;
                }
                for (char c : bin.toCharArray()) {
                    lengthOfResult--;
                    if (c == 49) bits.add(ONE);
                    else bits.add(ZERO);
                }
            }
            while (lengthOfResult != 0) {
                bits.remove(bits.size() - 1);
                lengthOfResult++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    DecompressorResultBuilder restoreOriginFile() {

        List<Bit> temporaryListOfBits = new ArrayList<>();
        for (Bit bit : bits) {
            temporaryListOfBits.add(bit);

            if (dictionaryTable.containsValue(temporaryListOfBits)) {

                for (Map.Entry<Integer, List<Bit>> entry : dictionaryTable.entrySet()) {

                    if (entry.getValue().equals(temporaryListOfBits)) {
                        decompressedResult.add(Integer.valueOf(entry.getKey()));
                        temporaryListOfBits.clear();
                        break;
                    }
                }

            }

        }

        return this;
    }

    DecompressorResultBuilder convertListToBytesArray() {

        bytes = new byte[decompressedResult.size()];
        for (int i = 0; i < bytes.length; i++) {
            int temp = decompressedResult.get(i);
            bytes[i] = (byte) temp;
        }

        return this;
    }

    DecompressorResult build() {
        return DecompressorResult.createDecompressorResult(this);
    }

    public String getFileName() {
        return fileName;
    }

    public List<Bit> getBits() {
        return bits;
    }

    public List<Integer> getDecompressedResult() {
        return decompressedResult;
    }

    public byte[] getBytes() {
        return bytes;
    }

    private int convertBitsListToInt(List<Bit> bits) {

        StringBuilder stringOfBits = new StringBuilder();

        for (Bit bit : bits) {
            if (bit == ONE) stringOfBits.append("1");
            else stringOfBits.append("0");
        }
        return Integer.parseInt(stringOfBits.toString(), 2);
    }
}


