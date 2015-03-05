package com.marcubus.nio.service;

import java.util.Observer;

public interface ResourceWatcherService extends Observer {

  public abstract void start() throws Exception;

  public abstract void stop() throws Exception;

  public abstract boolean isChanged();

  public abstract void resetChanged();

}