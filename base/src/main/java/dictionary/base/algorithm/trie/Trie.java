package dictionary.base.algorithm.trie;
import java.util.ArrayList;

/**
 * A data structure for implementing a Trie.
 *
 * @param <V> The type of values associated with Trie keys.
 */
public class Trie<V>
{
    private final int ALPHABET_SIZE = 26;
    private final TrieNode<V> root;
    private ArrayList<V> wordLists;

    public Trie() { root = new TrieNode<>(); }

    /**
     * Adds a key-value pair to the Trie.
     *
     * @param key   The key to add.
     * @param value The value associated with the key.
     */
    public void add(final String key, final V value)
    {
        TrieNode<V> currentNode = root;
        for (char ch : key.toCharArray()) {
            currentNode = currentNode.next(ch);
        }

        currentNode.setData(value);
        currentNode.setEnd(true);
    }

    /**
     * Removes a key from the Trie.
     *
     * @param key The key to remove.
     */
    public void remove(final String key)
    {
        TrieNode<V> currentNode = root;

        // find the end node
        for (char ch : key.toCharArray()) {
            currentNode = currentNode.next(ch);
        }
        currentNode.setData(null);
        currentNode.setEnd(false);

        // go back to remove the footprint
        for (int i = key.length() - 1; i >= 0; i--) {
            final char currentChar = key.charAt(i);

            // check if there is any other way
            boolean isNull = true;
            for (int j = 0; j < ALPHABET_SIZE; j++) {
                if (currentNode.getChilds()[j] != null) {
                    isNull = false;
                    break;
                }
            }

            // if there is no other way and the character is not end -> clear the character
            if (isNull && currentNode.isEnd() == false) {
                currentNode = currentNode.getParent();
                currentNode.setChildToNull(currentChar);
            }
        }
    }

    /**
     * Finds all values in the Trie with a given prefix.
     *
     * @param prefix The prefix to search for.
     * @return An ArrayList of values associated with keys that have the given prefix.
     */
    public ArrayList<V> findWithPrefix(final String prefix)
    {
        wordLists = new ArrayList<>();

        TrieNode<V> currentNode = root;
        for (char ch : prefix.toCharArray()) {
            currentNode = currentNode.next(ch);
        }
        findAllNodes(currentNode);

        return wordLists;
    }

    /**
     * Recursively finds all nodes with values in the Trie and adds them to the wordLists ArrayList.
     *
     * @param currentNode The current node to explore.
     */
    private void findAllNodes(final TrieNode<V> currentNode)
    {
        if (currentNode.isEnd() == true) {
            wordLists.add(currentNode.getData());
        }

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (currentNode.getChild(i) != null) {
                findAllNodes(currentNode.next((char)(i + (int)'a')));
            }
        }
    }
}
