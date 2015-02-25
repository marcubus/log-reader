package com.marcubus.hs.log;

public class MonitorFile {

  public static void main(String[] args) throws Exception {
    try (LogReader log = new LogReader(args[0])) {
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
