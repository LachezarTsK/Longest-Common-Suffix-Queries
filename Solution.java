
public class Solution {

    private final Trie trie = new Trie();

    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {
        for (int i = wordsContainer.length - 1; i >= 0; --i) {
            trie.addWordInReverse(wordsContainer[i], i);
        }

        int[] indexLongestCommonSuffix = new int[wordsQuery.length];
        for (int i = 0; i < wordsQuery.length; ++i) {
            indexLongestCommonSuffix[i] = trie.findIndexOfLongestCommonSuffix(wordsQuery[i]);
        }

        return indexLongestCommonSuffix;
    }
}

class TrieNode {

    private static final int ALPHABET_SIZE = 26;

    int indexOfMinLengthWord;
    int minLengthWord = Integer.MAX_VALUE;
    TrieNode[] branches = new TrieNode[ALPHABET_SIZE];
}

class Trie {

    private final TrieNode root = new TrieNode();

    void addWordInReverse(String word, int indexInWordsContainer) {
        TrieNode current = root;
        updateTrieNode(current, word.length(), indexInWordsContainer);

        for (int i = word.length() - 1; i >= 0; --i) {
            int index = word.charAt(i) - 'a';
            if (current.branches[index] == null) {
                current.branches[index] = new TrieNode();
            }
            current = current.branches[index];
            updateTrieNode(current, word.length(), indexInWordsContainer);
        }
    }

    /*
    Logic:
    If there are two or more strings in wordsContainer that share the longest common suffix, 
    find the string that is the smallest in length. If there are two or more such strings that 
    have the same smallest length, find the one that occurred earlier in wordsContainer.
    
    Example:
    word[0] = xyw
    word[1] = xyz
    word[2] = xyzab
    TrieNode for 'x':  minLengthWord = 3, indexOfMinLengthWord = 0
    TrieNode for 'y':  minLengthWord = 3, indexOfMinLengthWord = 0
    TrieNode for 'w':  minLengthWord = 3, indexOfMinLengthWord = 0
    TrieNode for 'z':  minLengthWord = 3, indexOfMinLengthWord = 1
    TrieNode for 'a':  minLengthWord = 5, indexOfMinLengthWord = 2
    TrieNode for 'b':  minLengthWord = 5, indexOfMinLengthWord = 2
     */
    private void updateTrieNode(TrieNode current, int wordLength, int indexInWordsContainer) {
        if (current.minLengthWord >= wordLength) {
            current.minLengthWord = wordLength;
            current.indexOfMinLengthWord = indexInWordsContainer;
        }
    }

    int findIndexOfLongestCommonSuffix(String suffix) {
        TrieNode current = root;
        for (int i = suffix.length() - 1; i >= 0; --i) {
            int index = suffix.charAt(i) - 'a';
            if (current.branches[index] == null) {
                break;
            }
            current = current.branches[index];
        }
        return current.indexOfMinLengthWord;
    }
}
