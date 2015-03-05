package com.marcubus.nio.service;

import static org.junit.Assert.assertTrue;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.WatchService;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.marcubus.nio.service.FileWatcher;

public class FileWatchRunnerTest {

  private static int SLEEP = 20;
  
  private WatchService watcher;
  
  @Before
  public void setup() throws Exception {
    watcher = FileSystems.getDefault().newWatchService();
  }
  
  @After
  public void tearDown() throws Exception {
    ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
    Thread[] threads = new Thread[threadGroup.activeCount()];
    threadGroup.enumerate(threads);
    Arrays.asList(threads).stream().filter(t -> t.getName().equals("watcher")).forEach(w -> w.interrupt());
    if (watcher != null)
      watcher.close();
  }
  
  @Test
  public void testHasChanged() throws Exception {
    FileWatcher runner = new FileWatcher(watcher, "src/test/resources/output_log.txt");
    Thread thread = new Thread(runner, "watcher");
    
    TestObserver observer = new TestObserver();
    runner.addObserver(observer);
    
    thread.start();    
    Files.setLastModifiedTime(Paths.get("src/test/resources/output_log.txt"), FileTime.fromMillis(System.currentTimeMillis()));    
    Thread.sleep(SLEEP);
    
    assertTrue(observer.change);
  }
  
  @Test
  public void testCreateFileNotExists() throws Exception {
    new FileWatcher(watcher, "src/test/resources/aaoutput_log.txt");
  }
  
  private class TestObserver implements Observer {
    public boolean change = false;
    @Override
    public void update(Observable o, Object arg) {
      change = true;
    }
  }
    
}
