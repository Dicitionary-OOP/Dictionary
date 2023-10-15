package dictionary.base.algorithm.trie;


/**
 * Represents a node in a Trie data structure.
 */
public class TrieNode
{
    private final int ASCIISIZE = 128;
    private char characterTable[] = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', ' ', '-', '\''};
    private Integer mapToCharacterPosition[] = new Integer[ASCIISIZE];
    private TrieNode parent;
    private final TrieNode[] childs;
    private boolean isEnd;

    /**
     * Initializes a new TrieNode with an empty array of child nodes.
     */
    public TrieNode()
    {
        parent = null;
        childs = new TrieNode[characterTable.length];
        for (int i = 0; i < characterTable.length; i++) {
            mapToCharacterPosition[(int)characterTable[i]] = i;
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
    public TrieNode getNextAndCreate(final char nextChar)
    {
        if (childs[mapToCharacterPosition[nextChar]] == null) {
            childs[mapToCharacterPosition[nextChar]] = new TrieNode();
        }
        return childs[mapToCharacterPosition[nextChar]];
    }

    public TrieNode getNext(final char nextChar) {
        return childs[mapToCharacterPosition[nextChar]];
    }
    /**
     * Gets the array of child nodes.
     *
     * @return The array of child TrieNodes.
     */
    public TrieNode[] getChilds() { return childs; }

    /**
     * Gets the child node at a specific index.
     *
     * @param i The index of the child node to retrieve.
     * @return The child TrieNode at the specified index.
     */
    public TrieNode getChild(final int i) { return childs[i]; }

    /**
     * Sets the child node associated with a character to null.
     *
     * @param currentChar The character to clear the child node.
     */
    public void setChildToNull(final char currentChar)
    {
        this.childs[mapToCharacterPosition[currentChar]] = null;
    }

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
    public TrieNode getParent() { return parent; }

    /**
     * Sets the parent TrieNode of the current TrieNode.
     *
     * @param parentNode The parent TrieNode to set.
     */
    public void setParent(final TrieNode parentNode) { this.parent = parentNode; }
}
