package sliptwise2.model.storage_handler

import sliptwise2.model.User

object UserStorageHandler {
  private var allUsers: Map[Int, User] = Map.empty
  private var nextUserId: Int = 1

  def addNewUser(name: String): Int = {
    val newUser = User(getUserId, name)
    addOrUpdateUser(newUser)
  }

  private def getUserById(userId: Int): User = {
    allUsers.getOrElse(userId, throw new Exception(s""))
  }

  private def getUserId: Int = {
    val res = nextUserId
    nextUserId += 1
    res
  }

  private def addOrUpdateUser(user: User): Int = {
    allUsers += ((user.userId, user))
    user.userId
  }
}
