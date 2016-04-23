package com.idisc.web;

import com.idisc.core.Service;

public class SendNewsAsEmailService
  extends Service
{
  @Override
  public Runnable newTask()
  {
    return new SendNewsAsEmailTask(3);
  }
}
