package huffmansAlgorithm;

import java.util.List;
import java.util.Map;

public class CompressionResult {
    private final String fileName;
    private final List<Bit> bytes;
    private final Map<Integer, List<Bit>> dictionaryTable;

    private CompressionResult(String fileName, List<Bit> bytes, Map<Integer, List<Bit>> dictionaryTable) {
        this.fileName = fileName;
        this.bytes = bytes;
        this.dictionaryTable = dictionaryTable;
    }

    public String getFileName() {
        return fileName;
    }

    public List<Bit> getBytes() {
        return bytes;
    }

    public Map<Integer, List<Bit>> getDictionaryTable() {
        return dictionaryTable;
    }

    public static CompressionResultBuilder createBuilder() {
        return new CompressionResultBuilder();
    }

    static CompressionResult createCompressionResult(CompressionResultBuilder resultBuilder) {
        return new CompressionResult(resultBuilder.getFileName(), resultBuilder.getBitsResult(), resultBuilder.getDictionaryTable());
    }
}


