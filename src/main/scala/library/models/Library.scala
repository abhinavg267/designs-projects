package library.models

case class Library(libraryId: Int, name: String, racksByLocation: Map[Int, Rack], nextRackLocation: Int)
