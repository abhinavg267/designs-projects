package car_rental.models

sealed trait VehicleType
object VehicleType {
  case object Bike extends VehicleType
  case object Car extends VehicleType
}

case class Vehicle(vehicleId: Int, branchId: Int, vehicleType: VehicleType, availableUnits: Int)
