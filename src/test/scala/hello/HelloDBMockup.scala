package hello

object HelloDBMockup extends App {
  case class User(name: String)

  class DataStorage {
    var users: List[User] = List.empty
  }

  val storage = new DataStorage
  storage.users = storage.users ++ List(User("A"), User("B"))
  storage.users.foreach(println)
}
