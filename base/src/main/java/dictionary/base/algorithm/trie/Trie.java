package dictionary.base.algorithm.trie;
import java.util.ArrayList;

public class Trie<V> {
    private TrieNode<V> root = new TrieNode();
    int count = 0;

    private ArrayList<V> wordLists;

    public void add(String key,V value){
        TrieNode<V> currentNode = root;

        //find the end node
        for (int i = 0; i < key.length(); i++){
            char currentChar = key.charAt(i);
            currentNode.next(currentChar).setParentNode(currentNode);
            currentNode = currentNode.next(currentChar);
        }
        currentNode.setNodeData(value);
        currentNode.setEnd(true);
    }

    public void remove(String key){
        TrieNode<V> currentNode = root;

        //find the end node
        for (int i = 0; i < key.length(); i++){
            char currentChar = key.charAt(i);
            currentNode = currentNode.next(currentChar);
        }
        currentNode.setNodeData(null);
        currentNode.setEnd(false);

        //go back to remove the footprint
        for (int i = key.length() - 1; i >= 0; i--){
            char currentChar = key.charAt(i);

            //check if there is any other way
            boolean isNull = true;
            for (int j = 0; j < 26; j++){
                if (currentNode.getChildNodes()[j] != null){
                    isNull = false;
                }
            }

            //if there is no other way and the character is not end -> clear the character
            if(isNull && currentNode.isEnd() == false){
                currentNode = currentNode.getParentNode();
                currentNode.setChildNodesToNull(currentChar);
            }else{
                break;
            }
        }
    }

    public ArrayList<V> findWithPrefix(String prefix){
        wordLists = new ArrayList<>();

        //find the prefix
        TrieNode<V> currentNode = root;
        for (int i = 0; i < prefix.length(); i++){
            char c = prefix.charAt(i);
            currentNode = currentNode.next(c);
        }
        findAllNodes(currentNode);
        return wordLists;
    }

    //This function find any path exist as a suffix
    private void findAllNodes(TrieNode<V> currentNode){
        if (currentNode.isEnd() == true){
            wordLists.add(currentNode.getNodeData());
        }
        for (int i = 0; i < 26; i++) {
            if(currentNode.getChildNodes()[i] != null) {
                findAllNodes(currentNode.next((char) (i + (int) 'a')));
            }
        }
    }
}
