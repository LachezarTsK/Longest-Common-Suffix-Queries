
class Solution {

    private val trie = Trie()

    fun stringIndices(wordsContainer: Array<String>, wordsQuery: Array<String>): IntArray {
        for (i in wordsContainer.size - 1 downTo 0) {
            trie.addWordInReverse(wordsContainer[i], i)
        }

        val indexLongestCommonSuffix = IntArray(wordsQuery.size)
        for (i in wordsQuery.indices) {
            indexLongestCommonSuffix[i] = trie.findIndexOfLongestCommonSuffix(wordsQuery[i])
        }

        return indexLongestCommonSuffix
    }
}

class TrieNode {

    companion object {
        const val ALPHABET_SIZE = 26
    }

    var indexOfMinLengthWord = 0
    var minLengthWord = Int.MAX_VALUE
    val branches = arrayOfNulls<TrieNode>(ALPHABET_SIZE)
}

class Trie {

    private val root = TrieNode()

    fun addWordInReverse(word: String, indexInWordsContainer: Int) {
        var current = root
        updateTrieNode(current, word.length, indexInWordsContainer)
        for (i in word.length - 1 downTo 0) {
            val index = word[i] - 'a'
            if (current.branches[index] == null) {
                current.branches[index] = TrieNode()
            }
            current = current.branches[index]!!
            updateTrieNode(current, word.length, indexInWordsContainer)
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
    private fun updateTrieNode(current: TrieNode, wordLength: Int, indexInWordsContainer: Int) {
        if (current.minLengthWord >= wordLength) {
            current.minLengthWord = wordLength
            current.indexOfMinLengthWord = indexInWordsContainer
        }
    }

    fun findIndexOfLongestCommonSuffix(suffix: String): Int {
        var current = root
        for (i in suffix.length - 1 downTo 0) {
            val index = suffix[i] - 'a'
            if (current.branches[index] == null) {
                break
            }
            current = current.branches[index]!!
        }
        return current.indexOfMinLengthWord
    }
}
