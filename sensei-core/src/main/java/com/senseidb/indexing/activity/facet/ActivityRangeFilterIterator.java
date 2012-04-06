package com.senseidb.indexing.activity.facet;

import java.io.IOException;

import org.apache.lucene.search.DocIdSetIterator;

public class ActivityRangeFilterIterator extends DocIdSetIterator {
  private int _doc;
  private final int[] fieldValues;
  private final int start;
  private final int end;
  private final int arrLength;
  private int[] indexes;

  public ActivityRangeFilterIterator(int[] fieldValues, int[] indexes,
      int start, int end) {
    this.fieldValues = fieldValues;
    this.start = start;
    this.end = end;
    this.indexes = indexes;
    arrLength = indexes.length;
    _doc = -1;
  }
  @Override
  final public int docID() {
    return _doc;
  }
  @Override
  public int nextDoc() throws IOException {  
   while (++_doc < arrLength && indexes[_doc] >= 0) {
     int value = fieldValues[indexes[_doc]];
     if (value >= start && value < end) {
       return _doc;
     }
   }
   return NO_MORE_DOCS;
  }

  @Override
  public int advance(int id) throws IOException {
    _doc = id - 1;
    return nextDoc();
  }
}