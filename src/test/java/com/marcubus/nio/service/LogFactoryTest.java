package com.marcubus.nio.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.marcubus.nio.model.LogEntry;
import com.marcubus.nio.service.MessageFactory;

public class LogFactoryTest {

  @Test
  public void create() {
    MessageFactory factory = new MessageFactory();
    LogEntry entry = factory.create("string");
    assertNotNull(entry);
  }

  @Test
  public void rawSet() {
    MessageFactory factory = new MessageFactory();
    LogEntry entry = factory.create("string");
    assertEquals("string", entry.getRaw());
  }

  @Test
  public void testParseCreateGame() {
    MessageFactory factory = new MessageFactory();
    LogEntry entry = factory.create("[Power] GameState.DebugPrintPower() - CREATE_GAME");
    assertEquals(true, entry.isMatched());
    assertEquals("Power", entry.getLoggerName());
    assertEquals("CREATE_GAME", entry.getLog());    
  }

  @Test
  public void testParseStartMulligan() {
    MessageFactory factory = new MessageFactory();
    LogEntry entry = factory.create("[Power] GameState.DebugPrintPower() -         tag=NEXT_STEP value=BEGIN_MULLIGAN");
    assertEquals(true, entry.isMatched());
    assertEquals("Power", entry.getLoggerName());
    assertEquals("BEGIN_MULLIGAN", entry.getLog());    
  }

  
}
