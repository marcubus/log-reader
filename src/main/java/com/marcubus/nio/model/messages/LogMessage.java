package com.marcubus.nio.model.messages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.marcubus.nio.model.LogEntry;

public abstract class LogMessage {

  public abstract Pattern getPattern();
  
  public LogEntry process(String log) {
    LogEntry entry = null;
    Matcher matcher = getPattern().matcher(log);    
    if (matcher.matches()) {
      entry = processMatch(matcher);
    }
    if (entry != null) {
      entry.setMatched(true);
      entry.setRaw(log);
    }
    return entry;
  }
  
  public abstract LogEntry processMatch(Matcher matcher);
  
}
