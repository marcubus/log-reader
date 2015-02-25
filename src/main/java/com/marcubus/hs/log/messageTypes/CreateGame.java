package com.marcubus.hs.log.messageTypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.marcubus.hs.log.LogEntry;
import com.marcubus.hs.log.LogMessageType;

public class CreateGame extends LogMessageType {

  // [Power] GameState.DebugPrintPower() - CREATE_GAME
  private static final String PATTERN = "^\\[([A-Za-z]+)\\].*? - (CREATE_GAME).*$";  
  
  @Override
  public Pattern getPattern() {
    return Pattern.compile(PATTERN);
  }

  @Override
  public LogEntry processMatch(Matcher matcher) {
    LogEntry entry = null;
    if (matcher.groupCount() == 2) {
      entry = new LogEntry();
      entry.setLoggerName(matcher.group(1));
      entry.setLog(matcher.group(2));
    }
    return entry;
  }
  
}
