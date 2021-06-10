package parking_lot.models

sealed trait BookingStatus
case object Occupied extends BookingStatus
case object Empty extends BookingStatus

case class Booking(bookingId: Int, parkingLotId: Int, floorLevel: Int, slotLocation: Int, status: BookingStatus)