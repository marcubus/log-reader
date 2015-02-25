package com.marcubus.hs.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


public class LogReader implements AutoCloseable {

  private Path filePath;
  private BufferedReader reader;
  private FileWatcher watcher;
  
  public LogReader(String fileName) throws Exception {
    this.filePath = Paths.get(fileName);
    InputStream is = Files.newInputStream(this.filePath, StandardOpenOption.READ);
    reader = new BufferedReader(new InputStreamReader(is));
    watcher = new FileWatcher(fileName);
    init();
  }

  @PostConstruct
  private void init() {
    watcher.start();
  }

  @PreDestroy
  private void destroy() {
    watcher.stop();
  }

  public boolean hasChanged() {
    return watcher.isChanged();
  }

  public String nextEntry() throws IOException {
    String line = reader.readLine(); 
    if (line == null)
      watcher.resetChanged();
    return line;
  }

  @Override
  public void close() throws Exception {
    if (reader != null) 
      reader.close();
    destroy();
  }

}
