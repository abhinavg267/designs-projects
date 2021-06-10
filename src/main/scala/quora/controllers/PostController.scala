package quora.controllers

import quora.model.Models.{Post, UserActionType}
import quora.model.handler.{PostDBHandler, UserActionDBHandler, UserDBHandler}

object PostController {
  def createNewPost(userId: Int, content: String): Int = {
    val user = UserDBHandler.getUserById(userId)
    PostDBHandler.createNewPost(user.userId, content)
  }

  def updateVoteForAPost(userId: Int, postId: Int, userActionType: UserActionType): Unit = {
    val user = UserDBHandler.getUserById(userId)
    UserActionDBHandler.addNewUserAction(user.userId, postId, userActionType)
    PostDBHandler.updateVotesForPost(postId, userActionType)
  }

  def viewPostsByVotes: Seq[Post] = {
    PostDBHandler.getPostsByUpvotes
  }
}
