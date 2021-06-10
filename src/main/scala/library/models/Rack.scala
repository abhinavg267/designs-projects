package library.models

case class Rack(rackLocationId: Int, bookInRack: Set[Int], bookCopiesByBookId: Map[Int, BookCopy], nextBookCopyId: Int)
