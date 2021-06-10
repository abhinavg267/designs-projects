package library

object HelloLibrary extends App {
  val l1Id = LibraryManager.registerNewLibrary("L1")
  LibraryManager.addNewRack(l1Id)

  val b1Id = LibraryManager.addNewBook(title = "B1")
  LibraryManager.addBookCopy(b1Id, l1Id)

  val ir1Id = BookIssueManager.issueABook(userId = 1, l1Id, b1Id)
  BookIssueManager.returnABook(userId = 1, l1Id, ir1Id)
  val ir2Id = BookIssueManager.issueABook(userId = 1, l1Id, b1Id)
  // val ir3Id = BookIssueManager.issueABook(userId = 1, l1Id, b1Id)
}
