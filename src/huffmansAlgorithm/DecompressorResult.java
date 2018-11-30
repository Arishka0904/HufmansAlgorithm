package huffmansAlgorithm;

import java.util.List;

public class DecompressorResult {

    private final String fileName;
    private final List<Bit> bits;
    private final List<Integer> decompressResult;
    private final byte[] bytes;

    private DecompressorResult(String fileName, List<Bit> bits, List<Integer> decompressResult, byte[] bytes) {
        this.fileName = fileName;
        this.bits = bits;
        this.decompressResult = decompressResult;
        this.bytes = bytes;
    }

    public static DecompressorResultBuilder createBuilder(String fileName) {
        return new DecompressorResultBuilder(fileName);
    }

    public static DecompressorResult createDecompressorResult(DecompressorResultBuilder decompressorResultBuilder) {
        return new DecompressorResult(decompressorResultBuilder.getFileName(),
                decompressorResultBuilder.getBits(), decompressorResultBuilder.getDecompressedResult(), decompressorResultBuilder.getBytes());
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public List<Integer> getDecompressResult() {
        return decompressResult;
    }
}
