package library.models.storageHandler

import library.models.{Available, Borrowed, IssueRecord}

object IssueRecordStorageHandler {
  private var allIssueRecords: Map[Int, IssueRecord] = Map.empty
  private var nextIssueRecordId: Int = 1

  def addNewIssueRecord(userId: Int, libraryId: Int, rackLocationId: Int, bookCopyId: Int): Int = {
    val newIssueRecord = IssueRecord(getNextIssueRecordId, userId, libraryId, rackLocationId, bookCopyId, status = Borrowed)
    addOrUpdateIssueRecord(newIssueRecord)
  }

  def markBookAvailable(issueRecordId: Int): Int = {
    val issueRecord = getIssueRecordById(issueRecordId)
    addOrUpdateIssueRecord(issueRecord.copy(status = Available))
  }

  def getIssueRecordById(issueRecordId: Int): IssueRecord = {
    allIssueRecords.getOrElse(issueRecordId, throw new Exception(s""))
  }

  private def getNextIssueRecordId: Int = {
    val res = nextIssueRecordId
    nextIssueRecordId += 1
    res
  }

  private def addOrUpdateIssueRecord(issueRecord: IssueRecord): Int = {
    allIssueRecords += ((issueRecord.issueRecordId -> issueRecord))
    issueRecord.issueRecordId
  }
}
