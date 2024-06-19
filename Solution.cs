
using System;

public class Solution
{
    private readonly Trie trie = new Trie();

    public int[] StringIndices(string[] wordsContainer, string[] wordsQuery)
    {
        for (int i = wordsContainer.Length - 1; i >= 0; --i)
        {
            trie.AddWordInReverse(wordsContainer[i], i);
        }

        int[] indexLongestCommonSuffix = new int[wordsQuery.Length];
        for (int i = 0; i < wordsQuery.Length; ++i)
        {
            indexLongestCommonSuffix[i] = trie.FindIndexOfLongestCommonSuffix(wordsQuery[i]);
        }

        return indexLongestCommonSuffix;
    }
}

class TrieNode
{
    private static readonly int ALPHABET_SIZE = 26;

    public int indexOfMinLengthWord;
    public int minLengthWord = int.MaxValue;
    public TrieNode[] branches = new TrieNode[ALPHABET_SIZE];
}

class Trie
{
    private readonly TrieNode root = new TrieNode();

    public void AddWordInReverse(String word, int indexInWordsContainer)
    {
        TrieNode current = root;
        UpdateTrieNode(current, word.Length, indexInWordsContainer);

        for (int i = word.Length - 1; i >= 0; --i)
        {
            int index = word[i] - 'a';
            if (current.branches[index] == null)
            {
                current.branches[index] = new TrieNode();
            }
            current = current.branches[index];
            UpdateTrieNode(current, word.Length, indexInWordsContainer);
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
    private void UpdateTrieNode(TrieNode current, int wordLength, int indexInWordsContainer)
    {
        if (current.minLengthWord >= wordLength)
        {
            current.minLengthWord = wordLength;
            current.indexOfMinLengthWord = indexInWordsContainer;
        }
    }

    public int FindIndexOfLongestCommonSuffix(String suffix)
    {
        TrieNode current = root;
        for (int i = suffix.Length - 1; i >= 0; --i)
        {
            int index = suffix[i] - 'a';
            if (current.branches[index] == null)
            {
                break;
            }
            current = current.branches[index];
        }
        return current.indexOfMinLengthWord;
    }
}
