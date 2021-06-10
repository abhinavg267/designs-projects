package quora.controllers

import quora.model.handler.UserDBHandler

object UserController {
  def addNewUser(name: String): Int = {
    UserDBHandler.addNewUser(name)
  }
}
