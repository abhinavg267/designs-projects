package library

import library.models.Book
import library.models.storageHandler.BookStorageHandler

object LibraryInfoManager {
  def getAllAvailableBooks(libraryId: Int): Seq[Book] = {
    val allBookIds = BookStorageHandler.getBookCopies(libraryId, bookIdOpt = None, availability = true).map(_.bookId)
    BookStorageHandler.getBooksByIds(allBookIds)
  }
}
