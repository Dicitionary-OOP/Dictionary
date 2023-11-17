package dictionary.base.algorithm.trie;

import dictionary.base.Word;
import edu.cmu.sphinx.fst.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Trie {
    private final TrieNode root;
    private int size;

    public Trie() {
        this.root = new TrieNode();
        this.size = 0;
    }

    /**
     * Insert word to Trie.
     * 
     * @param word word to insert
     */
    public void insert(final String word, final String wordID) {
        TrieNode currentNode = root;
        for (final char ch : word.toCharArray()) {
            currentNode = currentNode.addChild(ch);
        }
        currentNode.setWordID(wordID);
        currentNode.setEnd(true);
        this.size += 1;
    }

    /**
     * Get end node of given word.
     * 
     * @param word word
     * @return TrieNode endOfWord
     */
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

    /**
     * Remove word from Trie.
     * 
     * @param word word
     */
    public void remove(final String word) {
        TrieNode node = getEndNode(word);
        if (node == null) {
            return;
        }
        node.setEnd(false);

        for (int i = word.length() - 1; i >= 0; i--) {
            if (!node.getChilds().isEmpty() || node.isEnd()) {
                break;
            }

            node = node.getParent();
            node.getChilds().remove(word.charAt(i));
        }
        this.size -= 1;
    }

    /**
     * Get all word start with prefix.
     * 
     * @param prefix prefix
     * @return List of words
     */
    public ArrayList<ArrayList<String>> getAllWordsStartWith(final String prefix) {
        final TrieNode node = getEndNode(prefix);
        if (node == null) {
            return null;
        }

        final ArrayList<ArrayList<String>> wordLists = new ArrayList<ArrayList<String>>();
        retrieveWordsStartingWith(node, prefix, wordLists);
        return wordLists;
    }

    /**
     * Sub method for getAllWordsStartWith(),
     * 
     * @param node      TrieNode
     * @param word      currentWord
     * @param wordLists wordLists to fill
     */
    private void retrieveWordsStartingWith(final TrieNode node, final String word,
            final ArrayList<ArrayList<String>> wordLists) {
        if (node == null) {
            return;
        }

        if (node.isEnd()) {
            ArrayList<String> curWord = new ArrayList<String>();
            curWord.add(word);
            curWord.add(node.getWordID());
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
