package com.marcubus.hs.log;

import java.util.HashMap;
import java.util.Map;

import com.marcubus.hs.log.messageTypes.CreateGame;
import com.marcubus.hs.log.messageTypes.StartMulligan;

public class LogFactory {

  private Map<String, LogMessageType> messageTypes;
  
  public LogFactory() {
    messageTypes = new HashMap<String, LogMessageType>();
    init();
  }
  
  private void init() {
    messageTypes.put("CreateGame", new CreateGame());
    messageTypes.put("StartMulligan", new StartMulligan());
  }

  public LogEntry create(String rawLog) {
    for (String messageType : messageTypes.keySet()) {
      LogEntry entry = messageTypes.get(messageType).process(rawLog);
      if (entry != null)
        return entry;
    }
    return new LogEntry(rawLog);
  }

}
