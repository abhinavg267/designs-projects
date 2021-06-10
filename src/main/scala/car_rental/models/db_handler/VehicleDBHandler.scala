package car_rental.models.db_handler

import car_rental.models.{Vehicle, VehicleType}

object VehicleDBHandler {
  private var allVehicles: Map[Int, Vehicle] = Map.empty
  private var nextVehicleId: Int = 1

  def updateExistingVehicle(branchId: Int, vehicleType: VehicleType, deltaNuOfVehicle: Int): Int = {
    val vehicle = getVehicleByTypeAndBranch(branchId, vehicleType)
    val newQuantity = vehicle.availableUnits + deltaNuOfVehicle
    assert(newQuantity>=0, s"")

    _addOrUpdateVehicle(vehicle.copy(availableUnits = newQuantity))
  }

  def updateExistingVehicle(vehicleId: Int, deltaNuOfVehicle: Int): Int = {
    val vehicle = getVehicleById(vehicleId)
    val newQuantity = vehicle.availableUnits + deltaNuOfVehicle
    assert(newQuantity>=0, s"")

    _addOrUpdateVehicle(vehicle.copy(availableUnits = newQuantity))
  }

  def addNewVehicle(branchId: Int, vehicleType: VehicleType, nuOfVehicles: Int): Int = {
    val newVehicle = Vehicle(getNextVehicleId, branchId, vehicleType, availableUnits = nuOfVehicles)
    _addOrUpdateVehicle(newVehicle)
  }

  def getAvailableVehicles(branchId: Int): Seq[Vehicle] = {
    allVehicles.filter { case (_, vehicle) =>
      vehicle.availableUnits > 0 && vehicle.branchId == branchId
    }.values.toSeq
  }

  def getAllVehiclesForBranch(branchId: Int): Seq[Vehicle] = {
    allVehicles.filter { case (_, vehicle) =>
      vehicle.branchId == branchId
    }.values.toSeq
  }

  def getVehicleById(vehicleId: Int): Vehicle = {
    allVehicles.getOrElse(vehicleId, throw new Exception(s""))
  }

  private def getVehicleByTypeAndBranch(branchId: Int, vehicleType: VehicleType): Vehicle = {
    val filteredVehicles = allVehicles.filter { case (_, vehicle) =>
      vehicle.vehicleType == vehicleType && vehicle.branchId == branchId
    }.values

    assert(filteredVehicles.size == 1, "")
    filteredVehicles.head
  }

  private def _addOrUpdateVehicle(vehicle: Vehicle): Int = {
    allVehicles += ((vehicle.vehicleId, vehicle))
    vehicle.vehicleId
  }

  private def getNextVehicleId: Int = {
    val res = nextVehicleId
    nextVehicleId += 1
    res
  }
}
