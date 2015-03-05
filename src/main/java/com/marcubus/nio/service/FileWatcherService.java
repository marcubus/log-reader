package com.marcubus.nio.service;

import java.nio.file.FileSystems;
import java.nio.file.WatchService;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileWatcherService implements ResourceWatcherService {

  private final String filePath;
  private final ExecutorService executor;
  private final WatchService watcher;

  private boolean isChanged;
  private boolean isStarted;
  
  public FileWatcherService(final String filePath) throws Exception {
    this(Executors.newSingleThreadExecutor(), FileSystems.getDefault().newWatchService(), filePath);
  }

  public FileWatcherService(final ExecutorService executor, final WatchService watcher, final String filePath) throws Exception {
    this.executor = executor;
    this.watcher  = watcher;
    this.filePath = filePath;
  }

  @Override
  public void update(Observable o, Object arg) {
    setChanged(true);    
  }

  @Override
  public void start() throws Exception {
    synchronized (executor) {
      if (!isStarted) {
        FileWatcher runner = new FileWatcher(watcher, filePath);
        runner.addObserver(this);
        executor.execute(runner);
        isStarted = true;
      }
    }
  }
  
  @Override
  public void stop() throws Exception {
    synchronized (executor) {
      executor.shutdown();
    }    
  }
  
  @Override
  public boolean isChanged() {
    return isChanged;
  }

  @Override
  public void resetChanged() {
    isChanged = false;
  }
  
  private void setChanged(boolean isChanged) {
    this.isChanged = isChanged;
  }

}
