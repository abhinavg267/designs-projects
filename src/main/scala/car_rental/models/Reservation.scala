package car_rental.models

import org.joda.time.DateTime

sealed trait ReservationStatus
case object ReservationInProgress extends ReservationStatus
case object ReservationCompleted extends ReservationStatus


case class Reservation(reservationId: Int, userId: Int, vehicleId: Int,
                       startTime: DateTime, endTime: DateTime, price: BigDecimal,
                       status: ReservationStatus)


