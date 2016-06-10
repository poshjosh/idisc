package com.idisc.web.servlets.handlers.response;

import com.idisc.pu.entities.Comment;

/**
 * @author poshjosh
 */
public class CommentsJsonResponseHandler<E extends Comment> extends ListToJsonResponseHandler<E> {

  @Override
  public int getEstimatedLengthChars(Comment value) {
    return 500;
  }
}