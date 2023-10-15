package dictionary.base.algorithm.trie;
import java.util.ArrayList;


public class Trie
{
    private final int ASCIISIZE = 128;
    private char characterTable[] = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', ' ', '-', '\''};
    private Integer mapToCharacterPosition[] = new Integer[ASCIISIZE];
    private final TrieNode root;
    private ArrayList wordLists;

    private String currentWord = "";

    public Trie() { root = new TrieNode(); }

    /**
     * Adds a key-value pair to the Trie.
     *
     * @param key   The key to add.
     */
    public void add(final String key)
    {
        TrieNode currentNode = root;
        for (char ch : key.toCharArray()) {
            currentNode = currentNode.next(ch);
        }
        currentNode.setEnd(true);
    }

    /**
     * Removes a key from the Trie.
     *
     * @param key The key to remove.
     */
    public void remove(final String key)
    {
        TrieNode currentNode = root;

        // find the end node
        for (char ch : key.toCharArray()) {
            currentNode = currentNode.next(ch);
        }
        currentNode.setEnd(false);

        // go back to remove the footprint
        for (int i = key.length() - 1; i >= 0; i--) {
            final char currentChar = key.charAt(i);

            // check if there is any other way
            boolean isNull = true;
            for (int j = 0; j < characterTable.length; j++) {
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
    public ArrayList findWithPrefix(final String prefix)
    {
        wordLists = new ArrayList<>();

        TrieNode currentNode = root;
        for (char ch : prefix.toCharArray()) {
            currentWord += ch;
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
    private void findAllNodes(final TrieNode currentNode)
    {
        if (currentNode.isEnd() == true) {
            wordLists.add(currentWord);
        }

        for (int i = 0; i < characterTable.length; i++) {
            if (currentNode.getChild(i) != null) {
                currentWord += characterTable[i];
                findAllNodes(currentNode.next(characterTable[i]));
                currentWord = currentWord.substring(0, currentWord.length() - 1);
            }
        }
    }
}


