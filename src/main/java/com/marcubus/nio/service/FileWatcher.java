package com.marcubus.nio.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class FileWatcher extends RunnableWatcher {

  private Path file;
  private Path directory;
  private WatchService watcher;
  
  public FileWatcher(WatchService watcher, String filePath) throws Exception {
    this.watcher = watcher;
    Path path = Paths.get(filePath);
    file = path.getFileName();
    directory = path.getParent() != null ? path.getParent() : Paths.get(".");
    directory.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
  }

  @Override
  public void run() {
    try {
      WatchKey key;
      while ((key = watcher.take()) != null) {
        for (WatchEvent<?> event : key.pollEvents()) {
          if (hasChanged(event)) {
            setChanged();
            notifyObservers();
          }
        }
        key.reset();
      }
    } catch (InterruptedException e) {
      // do nothing.
    }
  }
  
  protected boolean hasChanged(WatchEvent<?> event) {
    return event.context().equals(file);
  }
  
}
