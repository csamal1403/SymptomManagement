package com.coursera.capstone.data;


import java.io.IOException;


public final class InvalidRequestException extends IOException {

  private final int status;
  private final String description;

  public InvalidRequestException(int status) {
    this(status, null);
  }

  public InvalidRequestException(int status, String description) {
    super(getMessage(status, description));
    this.status = status;
    this.description = description;
  }

  private static String getMessage(int status, String description) {
    StringBuilder base = new StringBuilder("HTTP Status Code: ").append(status);
    if (description != null) {
      base.append("(").append(description).append(")");
    }
    return base.toString();
  }

  public int getHttpStatusCode() {
   return status;
  }

  
  public String getDescription() {
    return description;
  }

}
