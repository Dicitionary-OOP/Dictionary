package dictionary.base.algorithm.trie;
import org.w3c.dom.Node;

import java.util.ArrayList;


public class Trie
{
    private char characterTable[] = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', ' ', '-', '\''};
    private final TrieNode root;

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
            currentNode = currentNode.getNextAndCreate(ch);
        }
        currentNode.setEnd(true);
    }

    /**
     * Removes a key from the Trie.
     *
     * @param key The key to remove.
     */

    public TrieNode getTheEndNode(final String key){
        TrieNode currentNode = root;
        for (char ch : key.toCharArray()) {
            if(currentNode == null){
                return null;
            }
            currentNode = currentNode.getNext(ch);
        }
        return currentNode;
    }

    public void remove(final String key)
    {
        TrieNode currentNode = getTheEndNode(key);
        if(currentNode != null){
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
    }

    /**
     * Finds all values in the Trie with a given prefix.
     *
     * @param prefix The prefix to search for.
     * @return An ArrayList of values associated with keys that have the given prefix.
     */
    public ArrayList<String> getWordsStartWithPrefix(final String prefix)
    {
        ArrayList<String> wordLists =  new ArrayList<String>();
        TrieNode currentNode = getTheEndNode(prefix);

        if(currentNode == null){
            //If we cant find the end node, return empty array
            return new ArrayList<String>();
        }else {
            findAllNodesWithPrefix(currentNode, prefix, wordLists);
            return wordLists;
        }
    }

    /**
     * Recursively finds all nodes with values in the Trie and adds them to the wordLists ArrayList.
     *
     * @param currentNode The current node to explore.
     */
    private void findAllNodesWithPrefix(final TrieNode currentNode, String currentWord, ArrayList<String> wordLists)
    {
        if (currentNode.isEnd() == true) {
            wordLists.add(currentWord);
        }
        for (int i = 0; i < characterTable.length; i++) {
            if (currentNode.getChild(i) != null) {
                findAllNodesWithPrefix(currentNode.getNext(characterTable[i]), currentWord + characterTable[i], wordLists);
            }
        }
    }
}


