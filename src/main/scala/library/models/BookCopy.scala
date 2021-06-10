package library.models

case class BookCopy(bookCopyId: Int, bookId: Int, rackLocationId: Int, isRented: Boolean)
