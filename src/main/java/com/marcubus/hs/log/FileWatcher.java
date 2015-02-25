package com.marcubus.hs.log;

import java.nio.file.FileSystems;
import java.nio.file.WatchService;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileWatcher implements Observer {

  private final String filePath;
  private final ExecutorService executor;
  private final WatchService watcher;

  private boolean isChanged;
  private boolean isStarted;
  
  public FileWatcher(final String filePath) throws Exception {
    this(Executors.newSingleThreadExecutor(), FileSystems.getDefault().newWatchService(), filePath);
  }

  public FileWatcher(final ExecutorService executor, final WatchService watcher, final String filePath) throws Exception {
    this.executor = executor;
    this.watcher  = watcher;
    this.filePath = filePath;
  }

  @Override
  public void update(Observable o, Object arg) {
    setChanged(true);    
  }

  public void start() throws Exception {
    synchronized (executor) {
      if (!isStarted) {
        FileWatchRunner runner = new FileWatchRunner(watcher, filePath);
        runner.addObserver(this);
        executor.execute(runner);
        isStarted = true;
      }
    }
  }
  
  public void stop() throws Exception {
    synchronized (executor) {
      executor.shutdown();
    }    
  }
  
  public boolean isChanged() {
    return isChanged;
  }

  public void resetChanged() {
    isChanged = false;
  }
  
  private void setChanged(boolean isChanged) {
    this.isChanged = isChanged;
  }

}
