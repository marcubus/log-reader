package com.marcubus.nio;

import com.marcubus.nio.LogReaderImpl;

public class MonitorFile {

  public static void main(String[] args) throws Exception {
    try (LogReader log = new LogReaderImpl(args[0])) {
      while(true) {
        String line = log.nextEntry();
        if (line != null) 
          System.out.println(line);
        else 
          Thread.sleep(20);        
      }      
    }
  }

}
