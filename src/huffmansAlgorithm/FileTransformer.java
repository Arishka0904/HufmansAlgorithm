package huffmansAlgorithm;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class FileTransformer {
    private String path;
    List<Integer> buffer;

    public FileTransformer(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public List<Integer> getBuffer() {
        return buffer;
    }

    public List<Integer> readFile() {
        try (InputStream is = new FileInputStream(path)) {
            buffer = new LinkedList<>();

            int byteValue = is.read();
            while (byteValue != -1) {
                buffer.add(byteValue);
                byteValue = is.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public HashMap<Integer, Integer> countFrequency(List<Integer> buffer) {
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();
        for (Integer element : buffer) {
            if (frequencyMap.containsKey(element)) {
                frequencyMap.put(element, frequencyMap.get(element) + 1);
            } else {
                frequencyMap.put(element, 1);
            }
        }
        return frequencyMap;
    }

    public PriorityQueue<Node> makeQueue(HashMap<Integer, Integer> frequencyMap) {
        PriorityQueue nodes =
                new PriorityQueue<>(
                        Comparator.comparingInt(Node::getWeight));
        Node node;
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            node = new Node(entry.getKey(), entry.getValue(), null, null);
            nodes.add(node);
        }

        while (nodes.size() > 1) {
            Node first = (Node) nodes.poll();
            Node second = (Node) nodes.poll();

            Node megaNode = new Node(first.getValue() + second.getValue(),
                    first.getWeight() + second.getWeight(), first, second);

            nodes.add(megaNode);
        }

        return nodes;
    }


    public static void main(String[] args) {
        FileTransformer transformer = new FileTransformer("From.txt");
        List<Integer> buffer = transformer.readFile();
        HashMap<Integer, Integer> count = transformer.countFrequency(buffer);
        PriorityQueue<Node> queue = transformer.makeQueue(count);
        System.out.println(buffer);
        System.out.println(count);
        System.out.println(queue);
    }


}
