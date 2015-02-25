package com.marcubus.hs.log;

import static org.junit.Assert.*;

import org.junit.Test;

public class LogFactoryTest {

  @Test
  public void create() {
    LogFactory factory = new LogFactory();
    LogEntry entry = factory.create("string");
    assertNotNull(entry);
  }

  @Test
  public void rawSet() {
    LogFactory factory = new LogFactory();
    LogEntry entry = factory.create("string");
    assertEquals("string", entry.getRaw());
  }

  @Test
  public void testParseCreateGame() {
    LogFactory factory = new LogFactory();
    LogEntry entry = factory.create("[Power] GameState.DebugPrintPower() - CREATE_GAME");
    assertEquals(true, entry.isMatched());
    assertEquals("Power", entry.getLoggerName());
    assertEquals("CREATE_GAME", entry.getLog());    
  }

  @Test
  public void testParseStartMulligan() {
    LogFactory factory = new LogFactory();
    LogEntry entry = factory.create("[Power] GameState.DebugPrintPower() -         tag=NEXT_STEP value=BEGIN_MULLIGAN");
    assertEquals(true, entry.isMatched());
    assertEquals("Power", entry.getLoggerName());
    assertEquals("BEGIN_MULLIGAN", entry.getLog());    
  }

  
}
