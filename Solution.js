
/**
 * @param {string[]} wordsContainer
 * @param {string[]} wordsQuery
 * @return {number[]}
 */
var stringIndices = function (wordsContainer, wordsQuery) {
    const trie = new Trie();
    for (let i = wordsContainer.length - 1; i >= 0; --i) {
        trie.addWordInReverse(wordsContainer[i], i);
    }

    const indexLongestCommonSuffix = new Array(wordsQuery.length);
    for (let i = 0; i < wordsQuery.length; ++i) {
        indexLongestCommonSuffix[i] = trie.findIndexOfLongestCommonSuffix(wordsQuery[i]);
    }

    return indexLongestCommonSuffix;
};

class TrieNode {
    indexOfMinLengthWord = 0;
    minLengthWord = Number.MAX_SAFE_INTEGER;
}

class Trie {

    static ASCII_SMALL_CASE_A = 97;
    root = new TrieNode();

    /**
     * @param {string} word
     * @param {number} indexInWordsContainer
     * @return {void}
     */
    addWordInReverse(word, indexInWordsContainer) {
        let current = this.root;
        this.updateTrieNode(current, word.length, indexInWordsContainer);

        for (let i = word.length - 1; i >= 0; --i) {
            const letter = word.charAt(i);
            if (current[letter] === undefined) {
                current[letter] = new TrieNode();
            }
            current = current[letter];
            this.updateTrieNode(current, word.length, indexInWordsContainer);
        }
    }

    /*
     Logic for function updateTrieNode(current, wordLength, indexInWordsContainer):
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
    /**
     * @param {TrieNode} current
     * @param {number} wordLength
     * @param {number} indexInWordsContainer 
     * @return {void}
     */
    updateTrieNode(current, wordLength, indexInWordsContainer) {
        if (current.minLengthWord >= wordLength) {
            current.minLengthWord = wordLength;
            current.indexOfMinLengthWord = indexInWordsContainer;
        }
    }

    /**
     * @param {string} suffix
     * @return {number}
     */
    findIndexOfLongestCommonSuffix(suffix) {
        let current = this.root;
        for (let i = suffix.length - 1; i >= 0; --i) {
            const letter = suffix.charAt(i);
            if (current[letter] === undefined) {
                break;
            }
            current = current[letter];
        }
        return current.indexOfMinLengthWord;
    }
}
