package parking_lot

import car_rental.models.VehicleType
import parking_lot.models.storage_handler.BookingStorageHandler
import parking_lot.strategy.{DefaultSlotSelectionStrategy, SlotSelectionStrategy}

object BookingManager {
  private val slotSelectionStrategy: SlotSelectionStrategy = DefaultSlotSelectionStrategy

  def bookNewSlot(parkingLotId: Int, floorLevel: Int, vehicleType: VehicleType): Int = {
    val availableSlots = ParkingLotManager.getAvailableSlots(parkingLotId, floorLevel, vehicleType)
    val selectedSlot = slotSelectionStrategy.selectASlot(availableSlots)

    ParkingLotManager.updateSlotAvailability(parkingLotId, floorLevel, selectedSlot.slotLocation, availability = false)
    BookingStorageHandler.addNewBooking(parkingLotId, floorLevel, slotLocation = selectedSlot.slotLocation)
  }
}
