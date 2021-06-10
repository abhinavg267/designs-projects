package quora.model

object Models {
  case class User(userId: Int, name: String)

  case class Post(postId: Int, userId: Int, nuOfUpvotes: Int, nuOfDownvotes: Int, content: String)
  object Posts {
    private var allPosts = Map.empty[Int, Post]
    private var nextPostId: Int = 1

    def getNextPostId: Int = {
      val res = nextPostId
      nextPostId += 1
      res
    }

    def addNewPost(post: Post): Unit = {
      allPosts += ((post.postId, post))
    }

    def getAllPosts: Map[Int, Post] = {
      allPosts
    }

    def getPostById(postId: Int): Post = {
      allPosts.getOrElse(postId, throw new Exception(s""))
    }

    def updatePost(postId: Int, newPost: Post): Unit = {
      allPosts += ((postId, newPost))
    }
  }

  sealed trait UserActionType
  case object Upvote extends UserActionType
  case object Downvote extends UserActionType

  case class UserAction(userActionId: Int, userId: Int, postId: Int, actionType: UserActionType)
}
