package car_rental

import car_rental.models.VehicleType
import car_rental.models.db_handler.{BranchDBHandler, VehicleDBHandler}

object BranchManager {
  def addNewBranch(name: String, vehicles: Map[VehicleType, Int]): Int = {
    val newBranchId = BranchDBHandler.addNewBranch(name)
    vehicles.map { case (vehicleType, cnt) =>
      VehicleDBHandler.addNewVehicle(newBranchId, vehicleType, cnt)
    }
    newBranchId
  }

  def addVehicles(branchId: Int, vehicles: Map[VehicleType, Int]): Seq[Int] = {
    vehicles.map { case (vehicleType, cnt) =>
      VehicleDBHandler.updateExistingVehicle(branchId, vehicleType, cnt)
    }.toSeq
  }

  def getBranchDetails(branchId: Int): String = {
    BranchDBHandler.getBranchDetailsById(branchId).toString()
  }
}
