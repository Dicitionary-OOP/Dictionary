package dictionary.base.algorithm.trie;

/**
 * Represents a node in a Trie data structure.
 *
 * @param <V> The type of data associated with the TrieNode.
 */
public class TrieNode<V>
{
    private final int ALPHABET_SIZE = 26;
    private TrieNode<V> parent;
    private V data;
    private final TrieNode<V>[] childs;
    private boolean isEnd;

    /**
     * Initializes a new TrieNode with an empty array of child nodes.
     */
    public TrieNode()
    {
        parent = null;
        data = null;
        childs = new TrieNode[ALPHABET_SIZE];
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            childs[i] = null;
        }
        isEnd = false;
    }

    /**
     * Gets the next child node associated with a character.
     *
     * @param nextChar The character to find or create the child node.
     * @return The next child TrieNode associated with the character.
     */
    public TrieNode<V> next(final char nextChar)
    {
        if (childs[(int)nextChar - (int)'a'] == null) {
            childs[(int)nextChar - (int)'a'] = new TrieNode<>();
        }
        return childs[(int)nextChar - (int)'a'];
    }

    /**
     * Gets the array of child nodes.
     *
     * @return The array of child TrieNodes.
     */
    public TrieNode<V>[] getChilds() { return childs; }

    /**
     * Gets the child node at a specific index.
     *
     * @param i The index of the child node to retrieve.
     * @return The child TrieNode at the specified index.
     */
    public TrieNode<V> getChild(final int i) { return childs[i]; }

    /**
     * Sets the child node associated with a character to null.
     *
     * @param currentChar The character to clear the child node.
     */
    public void setChildToNull(final char currentChar)
    {
        this.childs[(int)currentChar - (int)'a'] = null;
    }

    /**
     * Gets the data associated with the TrieNode.
     *
     * @return The data associated with the TrieNode.
     */
    public V getData() { return data; }

    /**
     * Sets the data associated with the TrieNode.
     *
     * @param data The data to set.
     */
    public void setData(final V data) { this.data = data; }

    /**
     * Sets the flag to indicate whether the TrieNode represents the end of a word.
     *
     * @param isEnd true if the TrieNode represents the end of a word, false otherwise.
     */
    public void setEnd(final boolean isEnd) { this.isEnd = isEnd; }

    /**
     * Checks if the TrieNode represents the end of a word.
     *
     * @return true if the TrieNode represents the end of a word, false otherwise.
     */
    public boolean isEnd() { return isEnd; }

    /**
     * Gets the parent TrieNode of the current TrieNode.
     *
     * @return The parent TrieNode of the current TrieNode.
     */
    public TrieNode<V> getParent() { return parent; }

    /**
     * Sets the parent TrieNode of the current TrieNode.
     *
     * @param parentNode The parent TrieNode to set.
     */
    public void setParent(final TrieNode<V> parentNode) { this.parent = parentNode; }
}
