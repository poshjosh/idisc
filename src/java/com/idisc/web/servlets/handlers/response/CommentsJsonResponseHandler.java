package com.idisc.web.servlets.handlers.response;

import com.idisc.pu.entities.Comment;

/**
 * @author poshjosh
 */
public class CommentsJsonResponseHandler extends EntityListJsonResponseHandler<Comment> {

  @Override
  public int getEstimatedBufferCapacity(Comment value) {
    return 500;
  }
}