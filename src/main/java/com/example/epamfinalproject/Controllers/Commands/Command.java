package com.example.epamfinalproject.Controllers.Commands;

import javax.servlet.http.HttpServletRequest;

/** Main interface for the Command pattern implementation */
public interface Command {
  /*
   *
   * Execution method for command
   * @return Address to go once the command is executed
   */
  String execute(HttpServletRequest request);
}
