package com.marcubus.nio.service;

import java.util.HashMap;
import java.util.Map;

import com.marcubus.nio.model.LogEntry;
import com.marcubus.nio.model.messages.CreateGame;
import com.marcubus.nio.model.messages.LogMessage;
import com.marcubus.nio.model.messages.StartMulligan;

public class MessageFactory {

  private Map<String, LogMessage> messageTypes;
  
  public MessageFactory() {
    messageTypes = new HashMap<String, LogMessage>();
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
