package com.marcubus.nio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.marcubus.nio.LogReaderImpl;

public class ANewLogReaderImplInstanceTest {

  private LogReader in;

  @Before
  public void setup() throws Exception {
    in = new LogReaderImpl("src/test/resources/output_log.txt");
  }
  
  @Test
  public void canBeInstantiated() {
    assertNotNull(in);
  }
  
  @Test
  public void hasNoChanges() {
    assertFalse(in.hasChanged());
  }
 
  @Test
  public void canReadLine() throws Exception {
    String input = in.nextEntry();
    assertNotNull(input);    
  }
  
  @Test
  public void canClose() throws Exception {
    in.close();    
  }
}
