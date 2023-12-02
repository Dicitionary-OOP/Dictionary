package base.algorithm.trie;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math3.util.Pair;

public class Trie {
    private final TrieNode root;
    private int size;

    public Trie() {
        this.root = new TrieNode();
        this.size = 0;
    }

    public void insert(final String word, final String wordID) {
        TrieNode currentNode = root;
        for (final char ch : word.toCharArray()) {
            currentNode = currentNode.addChild(ch);
        }
        currentNode.setValue(wordID);
        currentNode.setEnd(true);
        this.size += 1;
    }

    public TrieNode getEndNode(final String word) {
        TrieNode currentNode = root;
        for (final char ch : word.toCharArray()) {
            if (currentNode == null) {
                return null;
            }
            currentNode = currentNode.getChilds().get(ch);
        }
        return currentNode;
    }

    public void remove(final String word) {
        TrieNode node = getEndNode(word);
        if (node == null) {
            return;
        }
        node.setEnd(false);

        for (final char ch : word.toCharArray()) {
            if (!node.getChilds().isEmpty() || node.isEnd()) {
                break;
            }

            node = node.getParent();
            node.getChilds().remove(ch);
        }

        this.size -= 1;
    }

    public ArrayList<Pair<String, String>> getAllWordsStartWith(final String prefix) {
        final TrieNode node = getEndNode(prefix);
        if (node == null) {
            return new ArrayList<>();
        }

        final ArrayList<Pair<String, String>> wordLists = new ArrayList<Pair<String, String>>();
        retrieveWordsStartingWith(node, prefix, wordLists);
        return wordLists;
    }

    private void retrieveWordsStartingWith(final TrieNode node, final String word,
            final ArrayList<Pair<String, String>> wordLists) {
        if (node == null) {
            return;
        }

        if (node.isEnd()) {
            final Pair<String, String> curWord = new Pair<String, String>(word, node.getValue());
            wordLists.add(curWord);
        }

        for (final HashMap.Entry<Character, TrieNode> entry : node.getChilds().entrySet()) {
            if (entry.getValue() != null) {
                retrieveWordsStartingWith(entry.getValue(), word + entry.getKey(), wordLists);
            }
        }
    }

    public int size() {
        return size;
    }
}
