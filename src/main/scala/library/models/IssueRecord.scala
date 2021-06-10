package library.models

sealed trait IssueStatus
case object Borrowed extends IssueStatus
case object Available extends IssueStatus


case class IssueRecord(issueRecordId: Int, userId: Int, libraryId: Int, rackLocationId: Int, bookCopyId: Int,
                       status: IssueStatus)
