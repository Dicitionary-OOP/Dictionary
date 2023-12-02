package base.algorithm.trie;

import java.util.HashMap;

public class TrieNode {
    private final HashMap<Character, TrieNode> childs;
    private TrieNode parent;
    private boolean isEnd;
    private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
