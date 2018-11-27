package huffmansAlgorithm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static huffmansAlgorithm.Bit.*;

public class Compressor {
    private Compressor() {
    }

    public static void compress(String fileName) {
        Compressor compressor = new Compressor();
        byte[] source = compressor.readFileToByteArray(fileName);
        int[] frequency=compressor.countFrequency(source);
        PriorityQueue<Node> nodes = compressor.buildHuffmanTree(frequency);
        Map<Integer, String> dictionaryTable = compressor.makeHuffmanDictionaryTable(nodes);
        String result = compressor.makeResultString(source,dictionaryTable);
        compressor.writeResultToFile(fileName,result);

    }


    public byte[] readFileToByteArray(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            byte[] source = new byte[fis.available()];
            fis.read(source);
            return source;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int[] countFrequency(byte[] source) {
        int[] frequency = new int[256];
        for (int letter : source) {
            frequency[letter]++;
        }
        return frequency;
    }

    public PriorityQueue<Node> buildHuffmanTree(int[] frequency) {
        PriorityQueue<Node> nodes = new PriorityQueue<>(
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
        return nodes;
    }

    public Map<Integer, String> makeHuffmanDictionaryTable(PriorityQueue<Node> nodes) {

        Map<Integer, String> dictionaryTable = new HashMap<>();
        makeHuffmanCode(dictionaryTable, nodes.peek(), "");
        return dictionaryTable;
    }

//    public Map<Integer, Bit[]> makeHuffmanCode(Map<Integer, Bit[]> dictionaryTable, Node node, String huffmanCode) {
//        if (node.getLeft() == null && node.getRight() == null) {
//            dictionaryTable.put(node.getValue(), makeStringToBitsArray(huffmanCode));
//        } else {
//            makeHuffmanCode(dictionaryTable, node.getLeft(), huffmanCode + "1");
//            makeHuffmanCode(dictionaryTable, node.getRight(), huffmanCode + "0");
//        }
//        return dictionaryTable;
//    }

//    public Bit[] makeStringToBitsArray(String huffmanCode) {
//        Bit[] bits = new Bit[huffmanCode.length()];
//        char[] huffmanCodeArray = huffmanCode.toCharArray();
//        for (int i = 0; i < huffmanCode.length(); i++) {
//            if ((huffmanCodeArray[i] == 0)) {
//                bits[i] = ZERO;
//            } else {
//                bits[i] = ONE;
//            }
//        }
//        return bits;
//    }

    public Map<Integer, String> makeHuffmanCode(Map<Integer, String> dictionaryTable, Node node, String huffmanCode) {
        if (node.getLeft() == null && node.getRight() == null) {
            dictionaryTable.put(node.getValue(), huffmanCode);
        } else {
            makeHuffmanCode(dictionaryTable, node.getLeft(), huffmanCode + "1");
            makeHuffmanCode(dictionaryTable, node.getRight(), huffmanCode + "0");
        }
        return dictionaryTable;
    }


    public String makeResultString(byte[] source, Map<Integer, String> dictionaryTable) {
        StringBuilder result = new StringBuilder("");

        for (int letter : source) {
            result.append(dictionaryTable.get(letter));
        }
        return result.toString();
    }
//    public List<Integer> makeIntResultFromString(String result){
//        List<Integer> resultInt = new ArrayList<>();
//        for (char c : result.toCharArray()) {
//            if (c == '0') resultInt.add(0);
//            if (c == '1') resultInt.add(1);
//        }
//        return resultInt;
//    }
public void writeResultToFile(String fileName, String result){
        try (FileOutputStream fos = new FileOutputStream(fileName+".hf")){

//            fileOutputStream.write(Integer.toBinaryString(compressionResult.getBytes().size()).getBytes());
//            fileOutputStream.write(String.valueOf('2').getBytes());

            int counter = 0;
            StringBuilder currentByte = new StringBuilder("");

            for (char c : result.toCharArray()) {
                currentByte.append(c);
                counter++;
                if(counter==8){
                    fos.write(Integer.parseInt(currentByte.toString(), 2));
                    counter=0;
                    currentByte.delete(0,7);
                }
            }
            if (counter > 0) {
                while (counter < 8) {
                    currentByte.append("0");
                    counter++;
                }
                fos.write(Integer.parseInt(currentByte.toString(), 2));
            }

        }
        catch (IOException e){

        }
}

    public static void main(String[] args) {

        compress("From.txt");
    }


}
