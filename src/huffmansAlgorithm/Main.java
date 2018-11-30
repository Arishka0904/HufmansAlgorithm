package huffmansAlgorithm;

import static huffmansAlgorithm.CompressedFileWriter.writeCompressedFile;
import static huffmansAlgorithm.DecompressedFileWriter.writeDecompressedResultToFile;
import static huffmansAlgorithm.FileNameReader.isFileCompressed;
import static huffmansAlgorithm.FileNameReader.readFileName;

public class Main {
    public static void main(String[] args) {

        String fileName = readFileName();

        if (isFileCompressed(fileName)) {
            DecompressorResult deCompressor = DecompressorResult
                    .createBuilder(fileName)
                    .loadDictionaryTable()
                    .loadBitsFromCompressedFile()
                    .restoreOriginFile()
                    .convertListToBytesArray()
                    .build();

            writeDecompressedResultToFile(deCompressor);
        }

        else {
            CompressionResult result = CompressionResult
                    .createBuilder()
                    .readFileToByteArray(fileName)
                    .countFrequency()
                    .buildHuffmanTree()
                    .makeHuffmanDictionaryTable()
                    .makeIntBitsResult()
                    .build();

            writeCompressedFile(result);
        }
    }
}


