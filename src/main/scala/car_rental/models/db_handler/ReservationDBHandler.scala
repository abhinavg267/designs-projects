package car_rental.models.db_handler

import car_rental.models.{Reservation, ReservationCompleted, ReservationInProgress}
import org.joda.time.DateTime

object ReservationDBHandler {
  private var allReservations: Map[Int, Reservation] = Map.empty
  private var nextReservationId: Int = 1

  def markReservationComplete(reservation: Reservation): Int = {
    val updatedReservationRecord = reservation.copy(status = ReservationCompleted)
    _addOrUpdateReservation(updatedReservationRecord)
  }

  def addNewReservation(userId: Int, vehicleId: Int, startTime: DateTime, endTime: DateTime,
                        price: BigDecimal): Int = {
    val newReservation = Reservation(getNextReservationId, userId, vehicleId, startTime, endTime, price,
      status = ReservationInProgress)
    _addOrUpdateReservation(newReservation)
  }

  def getActiveReservationByUser(userId: Int, vehicleId: Int): Reservation = {
    val filteredReservations = allReservations.filter { case (_, reservation) =>
      reservation.status == ReservationInProgress && reservation.userId == userId && reservation.vehicleId == vehicleId
    }.values

    filteredReservations.head
  }

  private def _addOrUpdateReservation(reservation: Reservation): Int = {
    allReservations += ((reservation.reservationId, reservation))
    reservation.reservationId
  }

  private def getNextReservationId: Int = {
    val res = nextReservationId
    nextReservationId += 1
    res
  }
}
