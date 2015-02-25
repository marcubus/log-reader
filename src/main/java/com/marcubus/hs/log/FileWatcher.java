package com.marcubus.hs.log;

import java.nio.file.FileSystems;
import java.nio.file.WatchService;
import java.util.Observable;
import java.util.Observer;

public class FileWatcher implements Observer {

  private boolean isChanged;
  private Thread thread;
  
  public FileWatcher(String filePath) throws Exception {
    WatchService watcher = FileSystems.getDefault().newWatchService();
    FileWatchRunner runner = new FileWatchRunner(watcher, filePath);
    runner.addObserver(this);
    thread = new Thread(runner, "watcher");
  }

  @Override
  public void update(Observable o, Object arg) {
    setChanged(true);    
  }

  public void start() {
    thread.start();
  }

  public void stop() {
    thread.interrupt();
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
