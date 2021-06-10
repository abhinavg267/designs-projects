package parking_lot.models

import car_rental.models.VehicleType

case class Slot(slotLocation: Int, vehicleType: VehicleType, isAvailable: Boolean)
