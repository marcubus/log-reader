package com.marcubus.nio.service;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;

import org.junit.After;
import org.junit.Test;

import com.marcubus.nio.service.FileWatcherService;
import com.marcubus.nio.service.ResourceWatcherService;

public class FileWatcherServiceTest {

  private static int SLEEP = 20;

  @After
  public void tearDown() {
    ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
    Thread[] threads = new Thread[threadGroup.activeCount()];
    threadGroup.enumerate(threads);
    Arrays.asList(threads).stream().filter(t -> t.getName().equals("watcher")).forEach(w -> w.interrupt());
  }
  
  @Test
  public void create() throws Exception {
    new FileWatcherService("src/test/resources/output_log.txt");
  }
  
  @Test 
  public void createIsChangeFalse() throws Exception {
    ResourceWatcherService fileWatcher = new FileWatcherService("src/test/resources/output_log.txt");
    assertFalse(fileWatcher.isChanged());
  }
  
  @Test 
  public void testStart() throws Exception {
    ResourceWatcherService fileWatcher = new FileWatcherService("src/test/resources/output_log.txt");
    fileWatcher.start();
  }

  @Test 
  public void testStop() throws Exception {
    ResourceWatcherService fileWatcher = new FileWatcherService("src/test/resources/output_log.txt");
    fileWatcher.stop();
  }
  
  
  @Test
  public void testChanged() throws Exception {
    ResourceWatcherService fileWatcher = new FileWatcherService("src/test/resources/output_log.txt");
    fileWatcher.start();
    Files.setLastModifiedTime(Paths.get("src/test/resources/output_log.txt"), FileTime.fromMillis(System.currentTimeMillis()));    
    Thread.sleep(SLEEP);
    assertTrue(fileWatcher.isChanged());
  }
  
  @Test
  public void testChangedReset() throws Exception {
    ResourceWatcherService fileWatcher = new FileWatcherService("src/test/resources/output_log.txt");
    fileWatcher.start();
    Files.setLastModifiedTime(Paths.get("src/test/resources/output_log.txt"), FileTime.fromMillis(System.currentTimeMillis()));
    Thread.sleep(SLEEP);
    fileWatcher.resetChanged();
    assertFalse(fileWatcher.isChanged());
  }
}
