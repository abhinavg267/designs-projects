package car_rental.models.db_handler

import car_rental.models.{Branch, Vehicle}

object BranchDBHandler {
  private var allBranches: Map[Int, Branch] = Map.empty
  private var nextBranchId: Int = 1

  def addNewBranch(branchName: String): Int = {
    val newBranch = Branch(getNextBranchId, branchName)
    _addNewBranch(newBranch)
  }

  def getBranchDetailsById(branchId: Int): (Branch, Seq[Vehicle]) = {
    val branch = allBranches.getOrElse(branchId, throw new Exception(s""))
    val vehicles = VehicleDBHandler.getAllVehiclesForBranch(branch.branchId)
    (branch, vehicles)
  }

  private def _addNewBranch(branch: Branch): Int = {
    allBranches += ((branch.branchId, branch))
    branch.branchId
  }

  private def getNextBranchId: Int = {
    val res = nextBranchId
    nextBranchId += 1
    res
  }
}
