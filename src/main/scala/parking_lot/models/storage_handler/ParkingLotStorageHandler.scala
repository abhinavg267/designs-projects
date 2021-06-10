package parking_lot.models.storage_handler

import car_rental.models.VehicleType
import parking_lot.models.{Floor, ParkingLot, Slot}

object ParkingLotStorageHandler {
  private var allParkingLots: Map[Int, ParkingLot] = Map.empty
  private var nextParkingLotId: Int = 1

  def registerNewParkingLot: Int = {
    val newParkingLot = ParkingLot(getParkingLotId, Map.empty)
    addOrUpdateParkingLot(parkingLot = newParkingLot)
  }

  def addNewFloor(parkingLotId: Int): Int = {
    val parkingLot = getParkingLotById(parkingLotId)
    val maxFloorLevel = parkingLot.floorsByLevel.maxByOption(_._1).map(_._1).getOrElse(0)
    val newFloor = Floor(maxFloorLevel + 1, parkingLotId, slotsByLocation = Map.empty, nextSlotLocation = 1)
    addOrUpdateParkingLot(parkingLot.copy(floorsByLevel = parkingLot.floorsByLevel + ((newFloor.level, newFloor))))
  }

  def addNewSlot(parkingLotId: Int, floorLevel: Int, vehicleType: VehicleType): Int = {
    val parkingLot = getParkingLotById(parkingLotId)
    val floor = parkingLot.floorsByLevel.getOrElse(floorLevel, throw new Exception(s""))

    val newSlotLocation = floor.nextSlotLocation
    val newSlot = Slot(newSlotLocation, vehicleType, isAvailable = true)

    val newFloor = floor.copy(slotsByLocation = floor.slotsByLocation + ((floor.nextSlotLocation, newSlot)),
      nextSlotLocation = floor.nextSlotLocation + 1)

    addOrUpdateParkingLot(parkingLot.copy(floorsByLevel = parkingLot.floorsByLevel + ((floor.level, newFloor))))
  }

  def updateSlotAvailability(parkingLotId: Int, floorLevel: Int, slotLocation: Int, availability: Boolean): Int = {
    val parkingLot = getParkingLotById(parkingLotId)
    val floor = parkingLot.floorsByLevel.getOrElse(floorLevel, throw new Exception(s""))
    val slot = floor.slotsByLocation.getOrElse(slotLocation, throw new Exception(s""))

    val newFloor = floor.copy(slotsByLocation = floor.slotsByLocation +
      ((slot.slotLocation, slot.copy(isAvailable = availability))))

    addOrUpdateParkingLot(parkingLot.copy(floorsByLevel = parkingLot.floorsByLevel + ((floor.level, newFloor))))
  }

  def getAllSlots(parkingLotId: Int, floorLevel: Int, vehicleTypeOpt: Option[VehicleType], availability: Boolean): Seq[Slot] = {
    val parkingLot = getParkingLotById(parkingLotId)
    val floor = parkingLot.floorsByLevel.getOrElse(floorLevel, throw new Exception(s""))
    floor.slotsByLocation.filter {
      case (_, slot) => slot.isAvailable == availability && vehicleTypeOpt.fold(true)(_ == slot.vehicleType)
    }.values.toSeq
  }

  def printAllLots: Unit = allParkingLots.foreach(println)

  private def getParkingLotById(parkingLotId: Int): ParkingLot = {
    allParkingLots.getOrElse(parkingLotId, throw new Exception(s""))
  }

  private def getParkingLotId: Int = {
    val res = nextParkingLotId
    nextParkingLotId += 1
    res
  }

  private def addOrUpdateParkingLot(parkingLot: ParkingLot): Int = {
    allParkingLots += ((parkingLot.parkingLotId, parkingLot))
    parkingLot.parkingLotId
  }
}
