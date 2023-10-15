package dictionary.base.algorithm.trie;

import java.util.HashMap;

public class TrieNode {
    private TrieNode parent;
    private final HashMap<Character, TrieNode> childs;
    private boolean isEnd;

    /**
     * Initializes a new TrieNode.
     */
    public TrieNode() {
        this.parent = null;
        this.childs = new HashMap<>();
        this.isEnd = false;
    }

    public TrieNode(TrieNode parent) {
        this();
        this.parent = parent;
    }

    /**
     * Gets the child node associated with a character or creates a new one if it
     * doesn't exist.
     *
     * @param ch The character to find or create the child node.
     * @return The child TrieNode associated with the character.
     */
    public TrieNode addChild(final char ch) {
        if (childs.get(ch) == null) {
            childs.put(ch, new TrieNode(this));
        }
        return childs.get(ch);
    }

    /**
     * Gets the child nodes of this TrieNode.
     *
     * @return A HashMap containing the child nodes.
     */
    public HashMap<Character, TrieNode> getChilds() {
        return childs;
    }

    /**
     * Sets a flag to indicate whether the TrieNode represents the end of a word.
     *
     * @param isEnd true if the TrieNode represents the end of a word, false
     *              otherwise.
     */
    public void setEnd(final boolean isEnd) {
        this.isEnd = isEnd;
    }

    /**
     * Checks if the TrieNode represents the end of a word.
     *
     * @return true if the TrieNode represents the end of a word, false otherwise.
     */
    public boolean isEnd() {
        return isEnd;
    }

    /**
     * Gets the parent TrieNode of the current TrieNode.
     *
     * @return The parent TrieNode of the current TrieNode.
     */
    public TrieNode getParent() {
        return parent;
    }

    /**
     * Sets the parent TrieNode of the current TrieNode.
     *
     * @param parentNode The parent TrieNode to set.
     */
    public void setParent(final TrieNode parentNode) {
        this.parent = parentNode;
    }
}
