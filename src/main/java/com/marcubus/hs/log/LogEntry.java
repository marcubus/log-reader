package com.marcubus.hs.log;

public class LogEntry {

  private String raw;
  private String logClass;
  private String loggerName;
  private String method;
  private String log;
  private boolean matched;
  
  public LogEntry() {
  }
  
  public LogEntry(String rawLog) {
    this.raw = rawLog;
  }

  public String getRaw() {
    return raw;
  }
  
  public void setRaw(String raw) {
    this.raw = raw;
  }
  
  public String getLogClass() {
    return logClass;
  }
  
  public void setLogClass(String logClass) {
    this.logClass = logClass;
  }
  
  public String getLoggerName() {
    return loggerName;
  }
  
  public void setLoggerName(String loggerName) {
    this.loggerName = loggerName;
  }

  public String getLog() {
    return log;
  }
  
  public void setLog(String log) {
    this.log = log;
  }
  
  public String getMethod() {
    return method;
  }
  
  public void setMethod(String method) {
    this.method = method;
  }

  public boolean isMatched() {
    return matched;
  }

  public void setMatched(boolean matched) {
    this.matched = matched;
  }
  
}
