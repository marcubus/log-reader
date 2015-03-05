package com.marcubus.nio;

import java.io.IOException;

public interface LogReader extends AutoCloseable {

  public abstract boolean hasChanged();

  public abstract String nextEntry() throws IOException;

}