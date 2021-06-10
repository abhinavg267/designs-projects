package parking_lot

import car_rental.models.VehicleType
import parking_lot.models.Slot
import parking_lot.models.storage_handler.ParkingLotStorageHandler

object ParkingLotManager {
  def registerNewParkingLot: Int = ParkingLotStorageHandler.registerNewParkingLot

  def addNewFloor(parkingLotId: Int): Int = ParkingLotStorageHandler.addNewFloor(parkingLotId)

  def addNewSlot(parkingLotId: Int, floorLevel: Int, vehicleType: VehicleType): Int =
    ParkingLotStorageHandler.addNewSlot(parkingLotId, floorLevel, vehicleType)

  def updateSlotAvailability(parkingLotId: Int, floorLevel: Int, slotLocation: Int, availability: Boolean): Int = {
    ParkingLotStorageHandler.updateSlotAvailability(parkingLotId, floorLevel, slotLocation, availability)
  }

  def getAvailableSlotsByType(parkingLotId: Int, floorLevel: Int): Map[VehicleType, Seq[Slot]] = {
    ParkingLotStorageHandler.getAllSlots(parkingLotId: Int, floorLevel: Int, None, availability = true)
      .groupBy(_.vehicleType)
  }

  def getAvailableSlots(parkingLotId: Int, floorLevel: Int, vehicleType: VehicleType): Seq[Slot] = {
    ParkingLotStorageHandler.getAllSlots(parkingLotId: Int, floorLevel: Int, Some(vehicleType), availability = true)
  }

  def getOccupiedSlots(parkingLotId: Int, floorLevel: Int, vehicleType: VehicleType): Seq[Slot] = {
    ParkingLotStorageHandler.getAllSlots(parkingLotId: Int, floorLevel: Int, Some(vehicleType), availability = false)
  }
}
