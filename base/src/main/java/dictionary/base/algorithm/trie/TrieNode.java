package dictionary.base.algorithm.trie;

import java.util.HashMap;

public class TrieNode {
    private TrieNode parent;
    private final HashMap<Character, TrieNode> childs;
    private boolean isEnd;

    private String wordID;

    public TrieNode(final TrieNode parent) {
        this();
        this.parent = parent;
    }

    public TrieNode() {
        this.parent = null;
        this.childs = new HashMap<>();
        this.isEnd = false;
    }

    public TrieNode addChild(final char ch) {
        if (childs.get(ch) == null) {
            childs.put(ch, new TrieNode(this));
        }
        return childs.get(ch);
    }

    public HashMap<Character, TrieNode> getChilds() {
        return childs;
    }

    public void setEnd(final boolean isEnd) {
        this.isEnd = isEnd;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public TrieNode getParent() {
        return parent;
    }

    public void setParent(final TrieNode parentNode) {
        this.parent = parentNode;
    }

    public String getWordID() {
        return wordID;
    }

    public void setWordID(final String wordID) {
        this.wordID = wordID;
    }
}
