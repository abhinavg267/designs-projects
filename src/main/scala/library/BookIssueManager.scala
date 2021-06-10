package library

import library.models.storageHandler.{BookStorageHandler, IssueRecordStorageHandler}

object BookIssueManager {
  def issueABook(userId: Int, libraryId: Int, bookId: Int): Int = {
    val allBookCopies = BookStorageHandler.getBookCopies(libraryId, bookIdOpt = Some(bookId),
      availability = true)

    allBookCopies.headOption match {
      case Some(selectedBookCopy) =>
        BookStorageHandler.updateRentStatusOfBookCopy(libraryId, selectedBookCopy.rackLocationId, selectedBookCopy.bookCopyId, isRented = true)
        IssueRecordStorageHandler.addNewIssueRecord(userId, libraryId, selectedBookCopy.rackLocationId, selectedBookCopy.bookCopyId)
      case None => throw new Exception(s"No book available of bookId: $bookId in library: $libraryId")
    }
  }

  def returnABook(userId: Int, libraryId: Int, issueRecordId: Int): Int = {
    val issueRecord = IssueRecordStorageHandler.getIssueRecordById(issueRecordId)
    assert(issueRecord.userId == userId && issueRecord.libraryId == libraryId, s"")
    BookStorageHandler.updateRentStatusOfBookCopy(libraryId, issueRecord.rackLocationId, issueRecord.bookCopyId, isRented = false)
    IssueRecordStorageHandler.markBookAvailable(issueRecord.issueRecordId)
  }
}
