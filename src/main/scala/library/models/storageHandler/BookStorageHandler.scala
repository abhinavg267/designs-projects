package library.models.storageHandler

import library.models.{Book, BookCopy}

object BookStorageHandler {
  private var allBooks: Map[Int, Book] = Map.empty
  private var nextBookId: Int = 1

  def addNewBook(title: String): Int = {
    val newBook = Book(getNextBookId, title)
    addOrUpdateBook(newBook)
  }

  def addNewBookCopy(bookId: Int, libraryId: Int): Int = {
    val lib = LibraryStorageHandler.getLibraryById(libraryId)
    val availableRackOpt = lib.racksByLocation.collectFirst { case (i, rack) if !rack.bookInRack.contains(bookId) =>
      rack
    }

    availableRackOpt match {
      case Some(availableRack) =>
        val nextBookCopyId = availableRack.nextBookCopyId
        val newBookCopy = BookCopy(nextBookCopyId, bookId, availableRack.rackLocationId, isRented = false)
        val updatedRack = availableRack.copy(bookInRack = availableRack.bookInRack + bookId,
          bookCopiesByBookId = availableRack.bookCopiesByBookId + ((newBookCopy.bookCopyId, newBookCopy)),
          nextBookCopyId = availableRack.nextBookCopyId + 1)
        LibraryStorageHandler.updateRack(libraryId, updatedRack)
      case None => throw new Exception(s"There are no available rack for a bookCopy of bookId $bookId")
    }
  }

  def getBookCopies(libraryId: Int, bookIdOpt: Option[Int], availability: Boolean): Set[BookCopy] = {
    val lib = LibraryStorageHandler.getLibraryById(libraryId)
    lib.racksByLocation.flatMap(_._2.bookCopiesByBookId.values.filterNot { bookCopy =>
      bookCopy.isRented == availability && bookIdOpt.fold(true)(_ == bookCopy.bookId)
    }).toSet
  }

  def updateRentStatusOfBookCopy(libraryId: Int, rackLocationId: Int, bookCopyId: Int, isRented: Boolean): Int = {
    val lib = LibraryStorageHandler.getLibraryById(libraryId)
    val rack = lib.racksByLocation.getOrElse(rackLocationId, throw new Exception(s""))
    val bookCopy = rack.bookCopiesByBookId.getOrElse(bookCopyId, throw new Exception(s""))
    val updatedBookCopy = bookCopy.copy(isRented = isRented)
    val updatedRack =
      if(isRented) rack.copy(bookInRack = rack.bookInRack - bookCopy.bookId,
        bookCopiesByBookId = rack.bookCopiesByBookId + ((bookCopy.bookCopyId, updatedBookCopy)))
      else rack.copy(bookInRack = rack.bookInRack + bookCopy.bookId,
        bookCopiesByBookId = rack.bookCopiesByBookId + ((bookCopy.bookCopyId, updatedBookCopy)))

    LibraryStorageHandler.updateRack(libraryId, updatedRack)
  }

  def getBooksByIds(bookIds: Set[Int]): Seq[Book] = {
    allBooks.filter(b => bookIds.contains(b._2.bookId)).values.toSeq
  }

  private def getBookById(bookId: Int): Book = {
    allBooks.getOrElse(bookId, throw new Exception(s""))
  }

  private def getNextBookId: Int = {
    val res = nextBookId
    nextBookId += 1
    res
  }

  private def addOrUpdateBook(book: Book): Int = {
    allBooks += ((book.bookId -> book))
    book.bookId
  }
}
