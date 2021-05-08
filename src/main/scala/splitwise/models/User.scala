package splitwise.models

import slick.jdbc.H2Profile.api._

case class User(userId: Int, name: String)

class Users(tag: Tag) extends Table[User](tag, "users") {
  def userId = column[Int]("userId")
  def name = column[String]("name")

  // def uniqueIdxOnUserId = ???
  override def * = (userId, name) <> ((User.apply _).tupled, User.unapply)
}

object Users {
  val query = TableQuery[Users]
}
