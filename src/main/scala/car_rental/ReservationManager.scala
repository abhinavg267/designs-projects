package car_rental

import car_rental.models.Vehicle
import car_rental.models.db_handler.{ReservationDBHandler, VehicleDBHandler}
import car_rental.strategy.{DefaultPricingStrategy, DefaultVehicleSelectionStrategy, PricingStrategy, VehicleSelectionStrategy}
import org.joda.time.DateTime

object ReservationManager {
  private val vehicleSelectionStrategy: VehicleSelectionStrategy = DefaultVehicleSelectionStrategy
  private val vehiclePricingStrategy: PricingStrategy = DefaultPricingStrategy

  /* 1. add new reservation
  *  2. Update available vehicle in db
  * */
  def createNewReservation(userId: Int, branchId: Int, startTime: DateTime, endTime: DateTime): Int = {
    assert(startTime.isBefore(endTime), s"")

    val (vehicleBeingReserved, price) = getAvailableVehicleAndPrice(branchId, startTime, endTime)

    ReservationDBHandler.addNewReservation(userId, vehicleBeingReserved.vehicleId, startTime, endTime,
      price)

    VehicleDBHandler.updateExistingVehicle(vehicleBeingReserved.vehicleId, deltaNuOfVehicle = -1)
  }


  def getAvailableVehicleAndPrice(branchId: Int, startTime: DateTime, endTime: DateTime): (Vehicle, BigDecimal) = {
    val availableVehicles = VehicleDBHandler.getAvailableVehicles(branchId)
    val vehiclesAndPrice = availableVehicles.map { vehicle =>
      val price = vehiclePricingStrategy.calculatePriceForReservation(vehicle.vehicleType, startTime, endTime)
      (vehicle, price)
    }

    vehicleSelectionStrategy.selectVehicle(vehiclesAndPrice)
  }

  def returnAVehicle(userId: Int, vehicleId: Int, branchId: Int): Int = {
    val vehicle = VehicleDBHandler.getVehicleById(vehicleId)
    assert(vehicle.branchId == branchId, s"")

    VehicleDBHandler.updateExistingVehicle(vehicleId, 1)
    val activeReservation = ReservationDBHandler.getActiveReservationByUser(userId, vehicle.vehicleId)
    ReservationDBHandler.markReservationComplete(activeReservation)
  }
}
