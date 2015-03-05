package com.marcubus.nio.model.messages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.marcubus.nio.model.LogEntry;

public class StartMulligan extends LogMessage {

  // [Power] GameState.DebugPrintPower() -         tag=NEXT_STEP value=BEGIN_MULLIGAN
  private static final String PATTERN = "^\\[([A-Za-z]+)\\].*?=(BEGIN_MULLIGAN).*$";  
  
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
