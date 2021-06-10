package parking_lot.models.storage_handler

import parking_lot.models.{Booking, Empty, Occupied}

object BookingStorageHandler {
  private var allBookings: Map[Int, Booking] = Map.empty
  private var nextBookingId: Int = 1

  def addNewBooking(parkingLotId: Int, floorLevel: Int, slotLocation: Int): Int = {
    val newBooking = Booking(getBookingId, parkingLotId, floorLevel, slotLocation, Occupied)
    addOrUpdateBooking(newBooking)
  }

  def markBookingEmpty(bookingId: Int): Int = {
    val booking = getBookingById(bookingId)
    val newBooking = booking.copy(status = Empty)
    addOrUpdateBooking(newBooking)
  }

  private def getBookingById(bookingId: Int): Booking = {
    allBookings.getOrElse(bookingId, throw new Exception(s""))
  }

  private def getBookingId: Int = {
    val res = nextBookingId
    nextBookingId += 1
    res
  }

  private def addOrUpdateBooking(booking: Booking): Int = {
    allBookings += ((booking.parkingLotId, booking))
    booking.bookingId
  }
}
