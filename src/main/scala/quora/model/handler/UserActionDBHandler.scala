package quora.model.handler

import quora.model.Models.{UserAction, UserActionType}

object UserActionDBHandler {
  private var allUserActions: Map[Int, UserAction] = Map.empty
  private var nextUserActionId: Int = 1

  def addNewUserAction(userId: Int, postId: Int, actionType: UserActionType): Int = {
    validateUniqueAction(userId, postId, actionType)
    val newUserAction = UserAction(getNextUserActionId, userId, postId, actionType)
    _addNewUserAction(newUserAction)
  }

  private def validateUniqueAction(userId: Int, postId: Int, actionType: UserActionType): Unit = {
    val existingRecordOpt = allUserActions.collectFirst { case (i, action) if action.userId == userId &&
      action.postId == postId && action.actionType == actionType => action
    }

    assert(existingRecordOpt.isEmpty, s"User: $userId, has alredy done $actionType, on post: $postId")
  }

  private def _addNewUserAction(userAction: UserAction): Int = {
    allUserActions += ((userAction.userActionId, userAction))
    userAction.userActionId
  }

  private def getNextUserActionId: Int = {
    val res = nextUserActionId
    nextUserActionId += 1
    res
  }
}
