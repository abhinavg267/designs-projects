package car_rental.strategy

import car_rental.models.Vehicle

sealed trait VehicleSelectionStrategy {
  def selectVehicle(availableVehicles: Seq[(Vehicle, BigDecimal)]): (Vehicle, BigDecimal)
}

object DefaultVehicleSelectionStrategy extends VehicleSelectionStrategy {
  override def selectVehicle(availableVehicles: Seq[(Vehicle, BigDecimal)]): (Vehicle, BigDecimal) = {
    availableVehicles.sortBy(_._2).headOption.getOrElse(
      throw new Exception(s"")
    )
  }
}
