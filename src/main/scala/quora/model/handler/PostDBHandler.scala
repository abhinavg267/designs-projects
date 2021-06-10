package quora.model.handler

import quora.model.Models
import quora.model.Models.{Post, Posts, UserActionType}

object PostDBHandler {
  def createNewPost(userId: Int, content: String): Int = {
    val nextPostId = Posts.getNextPostId
    val newPost = Post(nextPostId, userId, 0, 0, content)
    Posts.addNewPost(newPost)
    newPost.postId
  }

  def getPostsByUpvotes: Seq[Post] = {
    Posts.getAllPosts.values.toSeq.sortBy(_.nuOfUpvotes)(implicitly[Ordering[Int]].reverse)
  }

  def updateVotesForPost(postId: Int, userActionType: UserActionType): Unit = {
    val post = Posts.getPostById(postId)

    val newPost = userActionType match {
      case Models.Upvote => post.copy(nuOfUpvotes = post.nuOfUpvotes+1)
      case Models.Downvote => post.copy(nuOfUpvotes = post.nuOfDownvotes+1)
    }

    Posts.updatePost(postId, newPost)
  }
}
