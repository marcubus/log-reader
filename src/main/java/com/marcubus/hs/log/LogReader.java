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
  
  public LogReader(final String fileName) throws Exception {
    this(new FileWatcher(fileName), fileName);
    init();
  }
  
  public LogReader(final FileWatcher watcher, final String fileName) throws Exception {
    this.watcher = watcher;
    this.filePath = Paths.get(fileName);
    InputStream is = Files.newInputStream(this.filePath, StandardOpenOption.READ);
    reader = new BufferedReader(new InputStreamReader(is));
  }

  @PostConstruct
  private void init() throws Exception {
    watcher.start();
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

  @PreDestroy
  @Override
  public void close() throws Exception {
    if (reader != null) 
      reader.close();
    watcher.stop();
  }

}
