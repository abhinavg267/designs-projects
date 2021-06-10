package parking_lot.models

case class Floor(level: Int, parkingLotId: Int, slotsByLocation: Map[Int, Slot], nextSlotLocation: Int)
