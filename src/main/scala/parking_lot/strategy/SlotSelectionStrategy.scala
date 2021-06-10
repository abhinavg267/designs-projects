package parking_lot.strategy

import parking_lot.models.Slot

trait SlotSelectionStrategy {
  def selectASlot(availableSlots: Seq[Slot]): Slot
}

object DefaultSlotSelectionStrategy extends SlotSelectionStrategy {
  override def selectASlot(availableSlots: Seq[Slot]): Slot =
    availableSlots.head
}


