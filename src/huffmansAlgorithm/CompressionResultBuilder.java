package huffmansAlgorithm;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static huffmansAlgorithm.Bit.ONE;
import static huffmansAlgorithm.Bit.ZERO;

public class CompressionResultBuilder {
    private String fileName;
    private byte[] source;
    private int[] frequency;
    private PriorityQueue<Node> nodes;
    private Map<Integer, List<Bit>> dictionaryTable;
    private List<Bit> bitsResult;


    public String getFileName() {
        return fileName;
    }

    public Map<Integer, List<Bit>> getDictionaryTable() {
        return dictionaryTable;
    }

    public List<Bit> getBitsResult() {
        return bitsResult;
    }

    CompressionResultBuilder readFileToByteArray(String fileName) {
        this.fileName = fileName;

        try (FileInputStream fis = new FileInputStream(fileName)) {
            source = new byte[fis.available()];
            fis.read(source);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    private static int[] convertByteArrayToIntArray(byte[] source) {
        int[] sourceInt = new int[source.length];
        for (int i = 0; i < sourceInt.length; i++) {
            sourceInt[i] = source[i] & 0xFF;
        }
        return sourceInt;
    }

    CompressionResultBuilder countFrequency() {
        frequency = new int[source.length];
        int[] sourceInt = convertByteArrayToIntArray(source);
        for (int letter : sourceInt) {
            frequency[letter]++;
        }
        return this;
    }

    CompressionResultBuilder buildHuffmanTree() {
        nodes = new PriorityQueue<>(
                Comparator.comparingInt(Node::getWeight));
        Node node;

        for (int i = 0; i < frequency.length; i++) {
            if (frequency[i] != 0) {
                node = new Node(i, frequency[i], null, null);
                nodes.add(node);
            }
        }

        while (nodes.size() > 1) {
            Node first = nodes.poll();
            Node second = nodes.poll();

            Node megaNode = new Node(first.getValue() + second.getValue(),
                    first.getWeight() + second.getWeight(), first, second);

            nodes.add(megaNode);
        }
        return this;
    }

    CompressionResultBuilder makeHuffmanDictionaryTable() {

        dictionaryTable = new TreeMap<>();
        makeHuffmanCode(dictionaryTable, nodes.peek(), "");
        return this;
    }

    private Map<Integer, List<Bit>> makeHuffmanCode(Map<Integer, List<Bit>> dictionaryTable, Node node, String huffmanCode) {
        if (node.getLeft() == null && node.getRight() == null) {
            dictionaryTable.put(node.getValue(), makeStringToBitsList(huffmanCode));
        } else {
            makeHuffmanCode(dictionaryTable, node.getLeft(), huffmanCode + "1");
            makeHuffmanCode(dictionaryTable, node.getRight(), huffmanCode + "0");
        }
        return dictionaryTable;
    }

    private List<Bit> makeStringToBitsList(String huffmanCode) {
        List<Bit> bits = new ArrayList<>();
        char[] huffmanCodeArray = huffmanCode.toCharArray();
        for (char bit : huffmanCodeArray) {

            if ((bit == '0')) {
                bits.add(ZERO);
            } else {
                bits.add(ONE);
            }
        }
        return bits;
    }

    CompressionResultBuilder makeIntBitsResult() {
        bitsResult = new ArrayList<>();
        int[] sourceInt = convertByteArrayToIntArray(source);
        for (int letter : sourceInt) {
            List<Bit> bitsOfLetter = dictionaryTable.get(letter);
            bitsResult.addAll(bitsOfLetter);
        }
        return this;
    }

    CompressionResult build() {
        return CompressionResult.createCompressionResult(this);
    }
}
