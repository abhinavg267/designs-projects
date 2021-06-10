package library.models.storageHandler

import library.models.{Library, Rack}

object LibraryStorageHandler {
  private var allLibraries: Map[Int, Library] = Map.empty
  private var nextLibraryId: Int = 1

  def addNewLibrary(name: String): Int = {
    val newLibrary = Library(getNextLibraryId, name, racksByLocation = Map.empty, nextRackLocation = 1)
    addOrUpdateLibrary(newLibrary)
  }

  def addNewRack(libraryId: Int): Int = {
    val lib = getLibraryById(libraryId)
    val newRack = Rack(lib.nextRackLocation, bookInRack = Set.empty, bookCopiesByBookId = Map.empty, nextBookCopyId = 1)
    val newLib = lib.copy(racksByLocation = lib.racksByLocation + (newRack.rackLocationId -> newRack),
      nextRackLocation = lib.nextRackLocation + 1)
    addOrUpdateLibrary(newLib)
  }

  def getLibraryById(libraryId: Int): Library = {
    allLibraries.getOrElse(libraryId, throw new Exception(s""))
  }

  def updateRack(libraryId: Int, rack: Rack): Int = {
    val lib = getLibraryById(libraryId)
    assert(lib.racksByLocation.exists(_._1 == rack.rackLocationId),
      s"No rack found to be updated with rack: $rack in library: $libraryId")

    val updateLib = lib.copy(racksByLocation =  lib.racksByLocation + ((rack.rackLocationId, rack)))
    addOrUpdateLibrary(updateLib)
  }

  private def getNextLibraryId: Int = {
    val res = nextLibraryId
    nextLibraryId += 1
    res
  }

  private def addOrUpdateLibrary(library: Library): Int = {
    allLibraries += ((library.libraryId -> library))
    library.libraryId
  }
}
