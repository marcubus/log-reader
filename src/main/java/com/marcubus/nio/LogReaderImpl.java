package com.marcubus.nio;

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

import com.marcubus.nio.service.FileWatcherService;
import com.marcubus.nio.service.ResourceWatcherService;


public class LogReaderImpl implements LogReader {

  private Path filePath;
  private BufferedReader reader;
  private ResourceWatcherService watcher;
  
  public LogReaderImpl(final String fileName) throws Exception {
    this(new FileWatcherService(fileName), fileName);
    init();
  }
  
  public LogReaderImpl(final ResourceWatcherService watcher, final String fileName) throws Exception {
    this.watcher = watcher;
    this.filePath = Paths.get(fileName);
    InputStream is = Files.newInputStream(this.filePath, StandardOpenOption.READ);
    reader = new BufferedReader(new InputStreamReader(is));
  }

  @PostConstruct
  private void init() throws Exception {
    watcher.start();
  }

  @Override
  public boolean hasChanged() {
    return watcher.isChanged();
  }

  @Override
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
