package bearmaps.proj2d;

import java.util.*;

public class AbbreviatedTrie {
    private Node root;

    private class Node {
        private boolean keyValueExists;
        private Map<Character, Node> map;

        private Node(boolean keyValueExists) {
            this.keyValueExists = keyValueExists;
            map = new HashMap<>();
        }
    }

    public AbbreviatedTrie() {
        root = new Node(false);
    }

    public void add(String key) {
        Node curr = root;
        if (key == null || key.length() < 1) {
            return;
        }
        for (int i = 0; i < key.length(); i++) {
            char letter = key.charAt(i);
            if (curr.map.containsKey(letter)) {
                curr = curr.map.get(letter);
            }
            curr.map.put(letter, new Node(false));
        }
        curr.keyValueExists = true;
    }

    public List<String> keysWithPrefix(String prefix) {
        List<String> keys = new LinkedList<>();
        Node n = discover(prefix);
        collect(n, prefix, keys);
        return keys;
    }

    private void collect(Node n, String s, List<String> results) {
        if (n == null) {
            return;
        } else if (n.keyValueExists) {
            results.add(s);
        }
        for (char i : n.map.keySet()) {
            collect(n.map.get(i), i + s, results);
        }
    }

    private Node discover(String s) {
        Node curr = root;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (curr.map.containsKey(ch)) {
                curr = curr.map.get(ch);
            }
            return null;
        }
        return curr;
    }
}
