package car_rental

import car_rental.models.VehicleType.{Bike, Car}
import org.joda.time.DateTime

object HelloCarRental extends App {
  val u1Id = 1

  val b1Id: Int = BranchManager.addNewBranch("B1", Map((Car, 2), (Bike, 3)))
  BranchManager.addVehicles(b1Id, Map((Car, 1), (Bike, 2)))
  println(BranchManager.getBranchDetails(b1Id))

  println(ReservationManager.getAvailableVehicleAndPrice(b1Id, DateTime.now(), DateTime.now().plusHours(2)))

  val v1Id = ReservationManager.createNewReservation(u1Id, b1Id, DateTime.now(), DateTime.now().plusHours(2))
  println(BranchManager.getBranchDetails(b1Id))

  ReservationManager.returnAVehicle(u1Id, v1Id, b1Id)
  println(BranchManager.getBranchDetails(b1Id))
}
