# Boolean Model for Information Retrieval

This is a Java implementation of a Boolean Model for Information Retrieval. This code was developed as a project for the Natural Language Processing and Information Retrieval class at Chung-Ang University during the 1st semester of 2023.

## Usage

To use this Boolean Model, create a new instance of the `BooleanModel` class and add documents to the index using the `addDocument` method. To search for documents, use the `search` method with a query as input.

## Class Overview

### BooleanModel

This class represents a Boolean Model for Information Retrieval. It contains an inverted index and methods to add documents to the index and search for documents based on a query.

#### Constructor

The `BooleanModel` constructor initializes the inverted index as an empty HashMap.

#### Methods

- `addDocument(int docId, String[] terms)`: adds a document to the inverted index.
- `removeDocument(int docId, String[] terms)`: removes a document from the inverted index.
- `search(String query)`: searches the inverted index for documents containing all terms in the query.

### Private Methods

- `intersect(List<Integer> list1, List<Integer> list2)`: computes the intersection of two postings lists.

