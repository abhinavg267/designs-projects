package library

import library.models.storageHandler.{BookStorageHandler, LibraryStorageHandler}

object LibraryManager {
  def registerNewLibrary(name: String): Int = LibraryStorageHandler.addNewLibrary(name)
  def addNewRack(libraryId: Int): Int = LibraryStorageHandler.addNewRack(libraryId)
  def addNewBook(title: String): Int = BookStorageHandler.addNewBook(title)
  def addBookCopy(bookId: Int, libraryId: Int): Int = BookStorageHandler.addNewBookCopy(bookId, libraryId)
}
