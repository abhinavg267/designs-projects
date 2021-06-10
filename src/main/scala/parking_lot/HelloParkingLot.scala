package parking_lot

import car_rental.models.VehicleType
import car_rental.models.VehicleType.Car
import parking_lot.models.storage_handler.ParkingLotStorageHandler

object HelloParkingLot extends App {
  ParkingLotStorageHandler.printAllLots

  val l1Id: Int = ParkingLotManager.registerNewParkingLot
  ParkingLotManager.addNewFloor(l1Id)
  ParkingLotManager.addNewSlot(l1Id, 1, VehicleType.Bike)
  ParkingLotManager.addNewSlot(l1Id, 1, VehicleType.Bike)
  ParkingLotManager.addNewSlot(l1Id, 1, VehicleType.Car)

  ParkingLotManager.addNewFloor(l1Id)
  ParkingLotManager.addNewSlot(l1Id, 2, VehicleType.Bike)
  ParkingLotManager.addNewSlot(l1Id, 2, VehicleType.Car)
  ParkingLotManager.addNewSlot(l1Id, 2, VehicleType.Car)

  ParkingLotStorageHandler.printAllLots
  ParkingLotManager.getAvailableSlots(l1Id, 2, VehicleType.Car).foreach(println)
  ParkingLotManager.addNewSlot(l1Id, 2, VehicleType.Car)
  ParkingLotManager.getAvailableSlots(l1Id, 2, VehicleType.Car).foreach(println)

  BookingManager.bookNewSlot(l1Id, 2, Car)
  BookingManager.bookNewSlot(l1Id, 2, Car)
  BookingManager.bookNewSlot(l1Id, 2, Car)
  println("for level 2 all should be booked now")
  ParkingLotManager.getAvailableSlots(l1Id, 2, VehicleType.Car).foreach(println)
}
