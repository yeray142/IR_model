package irmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a Boolean Model for Information Retrieval.
 * It contains an inverted index and methods to add documents to the index and search for documents based on a query.
 */
public class BooleanModel {
    // The inverted index is a map from terms to a list of document IDs that contain the term
    private Map<String, List<Integer>> invertedIndex;

    /**
     * Constructor for the BooleanModel class.
     * Initializes the inverted index as an empty HashMap.
     */
    public BooleanModel() {
        invertedIndex = new HashMap<>();
    }

    /**
     * Adds a document to the inverted index.
     * @param docId The ID of the document being added.
     * @param terms An array of terms in the document.
     */
    public void addDocument(int docId, String[] terms) {
        for (String term : terms) {
            term = term.toLowerCase(); // convert term to lowercase
            term = term.replaceAll("[^a-zA-Z0-9]", ""); // remove all non-alphanumeric characters
            if (!invertedIndex.containsKey(term)) {
                invertedIndex.put(term, new ArrayList<>());
            }
            List<Integer> postingsList = invertedIndex.get(term);
            if (postingsList.size() == 0 || postingsList.get(postingsList.size() - 1) != docId) {
                postingsList.add(docId);
            }
        }
    }

    /**
     * Removes a document from the inverted index.
     * @param docId The ID of the document being removed.
     * @param terms An array of terms in the document.
     */
    public void removeDocument(int docId, String[] terms) {
        for (String term : terms) {
            term = term.toLowerCase(); // convert term to lowercase
            term = term.replaceAll("[^a-zA-Z0-9]", ""); // remove all non-alphanumeric characters
            if (invertedIndex.containsKey(term)) {
                List<Integer> postingsList = invertedIndex.get(term);
                postingsList.remove(Integer.valueOf(docId));
                if (postingsList.isEmpty()) {
                    invertedIndex.remove(term);
                }
            }
        }
    }


    /**
     * Searches the inverted index for documents containing all terms in the query.
     * @param query The query to search for.
     * @return A list of document IDs containing all terms in the query.
     */
    public List<Integer> search(String query) {
        String[] terms = query.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+"); // convert query to lowercase, remove all non-alphanumeric characters except spaces, and split into terms
        List<Integer> result = new ArrayList<>();
        if (terms.length == 0) {
            return result;
        }
        result = invertedIndex.get(terms[0]);
        for (int i = 1; i < terms.length; i++) {
            String term = terms[i];
            List<Integer> postingsList = invertedIndex.get(term);
            if (postingsList == null) {
                return new ArrayList<>(); // return empty list if any term is not in the index
            }
            result = intersect(result, postingsList);
        }
        return result;
    }

    /**
     * Computes the intersection of two postings lists.
     * @param list1 The first postings list.
     * @param list2 The second postings list.
     * @return A list of document IDs that appear in both postings lists.
     */
    private List<Integer> intersect(List<Integer> list1, List<Integer> list2) {
        List<Integer> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size()) {
            if (list1.get(i).equals(list2.get(j))) {
                result.add(list1.get(i));
                i++;
                j++;
            } else if (list1.get(i) < list2.get(j)) {
                i++;
            } else {
                j++;
            }
        }
        return result;
    }
}
