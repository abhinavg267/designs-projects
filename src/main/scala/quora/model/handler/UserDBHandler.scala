package quora.model.handler

import quora.model.Models.User

object UserDBHandler {
  private var allUsers = Map.empty[Int, User]
  private var nextUserId: Int = 1

  def addNewUser(name: String): Int = {
    val newUserId = getNextUserId
    addNewUser(User(newUserId, name))
    newUserId
  }

  def getUserById(userId: Int): User = {
    allUsers.getOrElse(userId, throw new Exception(s""))
  }

  // Private methods
  private def getNextUserId: Int = {
    val res = nextUserId
    nextUserId += 1
    res
  }

  private def addNewUser(user: User): Unit = {
    allUsers += ((user.userId, user))
  }
}
