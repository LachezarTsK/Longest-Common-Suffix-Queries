
#include <array>
#include <limits>
#include <vector>
#include <memory>
#include <string>
#include <string_view>
using namespace std;

struct TrieNode {

    static const int ALPHABET_SIZE = 26;

    size_t indexOfMinLengthWord;
    size_t minLengthWord = numeric_limits<size_t>::max();
    array<shared_ptr<TrieNode>, ALPHABET_SIZE> branches{};
};

class Trie {

    const shared_ptr<TrieNode> root{ make_shared<TrieNode>() };

public:
    void addWordInReverse(string_view word, size_t indexInWordsContainer) const {
        shared_ptr<TrieNode> current{ root };
        updateTrieNode(current, word.length(), indexInWordsContainer);

        for (size_t i = word.length() - 1; i != variant_npos; --i) {
            int index = word[i] - 'a';
            if (current->branches[index] == nullptr) {
                current->branches[index] = make_shared<TrieNode>();
            }
            current = current->branches[index];
            updateTrieNode(current, word.length(), indexInWordsContainer);
        }
    }

    size_t findIndexOfLongestCommonSuffix(string_view suffix) const {
        shared_ptr<TrieNode> current{ root };
        for (size_t i = suffix.length() - 1; i != variant_npos; --i) {
            int index = suffix[i] - 'a';
            if (current->branches[index] == nullptr) {
                break;
            }
            current = current->branches[index];
        }
        return current->indexOfMinLengthWord;
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
private:
    void updateTrieNode(shared_ptr<TrieNode> current, size_t wordLength, size_t indexInWordsContainer) const {
        if (current->minLengthWord >= wordLength) {
            current->minLengthWord = wordLength;
            current->indexOfMinLengthWord = indexInWordsContainer;
        }
    }
};

class Solution {

    unique_ptr< Trie> trie = make_unique<Trie>();

public:
    vector<int> stringIndices(const vector<string>& wordsContainer, const vector<string>& wordsQuery) const {
        for (size_t i = wordsContainer.size() - 1; i != variant_npos; --i) {
            trie->addWordInReverse(wordsContainer[i], i);
        }

        vector<int> indexLongestCommonSuffix(wordsQuery.size());
        for (size_t i = 0; i < wordsQuery.size(); ++i) {
            indexLongestCommonSuffix[i] = trie->findIndexOfLongestCommonSuffix(wordsQuery[i]);
        }

        return indexLongestCommonSuffix;
    }
};
