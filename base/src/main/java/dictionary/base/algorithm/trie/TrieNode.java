package dictionary.base.algorithm.trie;
import java.util.ArrayList;

public class TrieNode<V> {
    private TrieNode<V> parentNode;
    private TrieNode<V>[] childNodes = new TrieNode[30];
    private V nodeData = null;
    private boolean isEnd = false;

    public TrieNode(){
        for(int i = 0; i < 30; i++){
            childNodes[i] = null;
        }
        parentNode = null;
    }

    //Find the next child node contain the character
    public TrieNode<V> next(char nextChar){
        if(childNodes[(int) nextChar - (int) 'a'] == null) {
            childNodes[(int) nextChar - (int) 'a'] = new TrieNode<>();
        }
        return childNodes[(int) nextChar - (int) 'a'];
    }

    public TrieNode<V>[] getChildNodes() {
        return childNodes;
    }

    //Set the child node contain that character to null
    public void setChildNodesToNull(char currentChar) {
        this.childNodes[(int) currentChar - (int) 'a'] = null;
    }

    //Deep copy
    public void Copy(TrieNode<V> other){
        this.parentNode = other.parentNode;
        this.childNodes = other.childNodes;
        this.nodeData = other.nodeData;
        this.isEnd = other.isEnd;
    }

    public V getNodeData() {
        return nodeData;
    }

    public void setNodeData(V nodeData) {
        this.nodeData = nodeData;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public TrieNode<V> getParentNode() {
        return parentNode;
    }

    public void setParentNode(TrieNode<V> parentNode) {
        this.parentNode = parentNode;
    }
}
