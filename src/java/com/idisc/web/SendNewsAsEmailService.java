package com.idisc.web;

import com.idisc.core.Service;













public class SendNewsAsEmailService
  extends Service
{
  public Runnable newTask()
  {
    return new SendNewsAsEmailTask(3);
  }
}
