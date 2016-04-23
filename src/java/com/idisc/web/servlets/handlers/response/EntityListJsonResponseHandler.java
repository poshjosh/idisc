package com.idisc.web.servlets.handlers.response;

import java.util.List;

/**
 * @author poshjosh
 */
public class EntityListJsonResponseHandler<E> extends EntityJsonResponseHandler<List<E>> {

  private final int defaultCapacityPerListItem;

  public EntityListJsonResponseHandler() {
    this(16 * 16 * 2);
  }
    
  public EntityListJsonResponseHandler(int defaultCapacityPerListItem) {
    if(defaultCapacityPerListItem < 1) {
        throw new IllegalArgumentException("default capacity per list item < 1");
    }
    this.defaultCapacityPerListItem = defaultCapacityPerListItem;
  }
    
  @Override
  public int getEstimatedBufferCapacity(List<E> list, Object messageCreatedFromList) {
      int length;
      if(list == null || list.isEmpty()) {
          length = 0;
      }else{
          length = 0;
          for(E e:list) {
              length += this.getEstimatedBufferCapacity(e);
          }
      }
      final int size = list == null || list.isEmpty() ? 0 : list.size();
      return length + (16 * size) + 16;
  }
  
  public int getEstimatedBufferCapacity(E value) {
    return defaultCapacityPerListItem;
  }
}

