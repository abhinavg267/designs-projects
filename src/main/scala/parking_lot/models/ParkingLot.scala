package parking_lot.models

case class ParkingLot(parkingLotId: Int, floorsByLevel: Map[Int, Floor])
