package car_rental.strategy

import car_rental.models.VehicleType
import org.joda.time.DateTime

sealed trait PricingStrategy {
  def calculatePriceForReservation(vehicleType: VehicleType, startTime: DateTime, endTime: DateTime): BigDecimal
}

object DefaultPricingStrategy extends PricingStrategy {
  override def calculatePriceForReservation(vehicleType: VehicleType, startTime: DateTime, endTime: DateTime): BigDecimal = {
    vehicleType match {
      case VehicleType.Bike => 0.1 * (endTime.getMillis - startTime.getMillis)
      case VehicleType.Car => 0.15 * (endTime.getMillis - startTime.getMillis)
    }
  }
}
