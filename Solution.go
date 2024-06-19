
package main

import (
    "fmt"
    "math"
)

const ALPHABET_SIZE = 26

func stringIndices(wordsContainer []string, wordsQuery []string) []int {
    var root *TrieNode = createTrieNode()
    var trie *Trie = &Trie{root}
    for i := len(wordsContainer) - 1; i >= 0; i-- {
        trie.addWordInReverse(&wordsContainer[i], i)
    }

    indexLongestCommonSuffix := make([]int, len(wordsQuery))
    for i := range wordsQuery {
        indexLongestCommonSuffix[i] = trie.findIndexOfLongestCommonSuffix(&wordsQuery[i])
    }

    return indexLongestCommonSuffix
}

type TrieNode struct {
    indexOfMinLengthWord int
    minLengthWord        int
    branches             [ALPHABET_SIZE]*TrieNode
}

type Trie struct {
    root *TrieNode
}

func createTrieNode() *TrieNode {
    trieNode := &TrieNode{
        indexOfMinLengthWord: 0,
        minLengthWord:        math.MaxInt,
        branches:             [ALPHABET_SIZE]*TrieNode{},
    }
    return trieNode
}

func (trie *Trie) addWordInReverse(word *string, indexInWordsContainer int) {
    current := trie.root
    trie.updateTrieNode(current, len(*word), indexInWordsContainer)
    for i := len(*word) - 1; i >= 0; i-- {
        index := (*word)[i] - 'a'
        if current.branches[index] == nil {
            current.branches[index] = createTrieNode()
        }
        current = current.branches[index]
        trie.updateTrieNode(current, len(*word), indexInWordsContainer)
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
func (trie *Trie) updateTrieNode(current *TrieNode, wordLength int, indexInWordsContainer int) {
    if current.minLengthWord >= wordLength {
        current.minLengthWord = wordLength
        current.indexOfMinLengthWord = indexInWordsContainer
    }
}

func (trie *Trie) findIndexOfLongestCommonSuffix(suffix *string) int {
    current := trie.root
    for i := len(*suffix) - 1; i >= 0; i-- {
        index := (*suffix)[i] - 'a'
        if current.branches[index] == nil {
            break
        }
        current = current.branches[index]
    }
    return current.indexOfMinLengthWord
}
