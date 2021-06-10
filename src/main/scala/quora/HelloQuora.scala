package quora

import quora.controllers.{PostController, UserController}
import quora.model.Models.Upvote

object HelloQuora extends App {
  val u1Id = UserController.addNewUser("U1")
  val u2Id = UserController.addNewUser("U2")

  val p1Id = PostController.createNewPost(u1Id, "P1U1")
  val p2Id = PostController.createNewPost(u2Id, "P2U2")
  val p3Id = PostController.createNewPost(u1Id, "P3U1")

  PostController.updateVoteForAPost(u1Id, p2Id, Upvote)
  PostController.updateVoteForAPost(u1Id, p2Id, Upvote)
  PostController.updateVoteForAPost(u2Id, p1Id, Upvote)
  PostController.viewPostsByVotes.foreach(println)
}
