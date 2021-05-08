package init

import scala.annotation.tailrec

object NavigationAlerts extends App {
  case class Location(latitude: Double, longitude: Double)

  val route: Seq[Location] = Seq(Location(1.11, 2.23), Location(1.12, 2.21), Location(1.10, 2))

  @tailrec
  def prepareNavigationAlerts(route: Seq[Location], navigationAlerts: Seq[String]): Seq[String] = {
    route match {
      case _ :: Nil => navigationAlerts ++ Seq("Reached Your Destination")
      case current :: next :: tail =>
        prepareNavigationAlerts(next +: tail, navigationAlerts :+ getNextAlert(current, next))
    }
  }

  def getNextAlert(currentLocation: Location, nextLocation: Location): String = {
    s"Turn ${getDirection(currentLocation, nextLocation)} and move ${calculateDistanceInMeters(currentLocation, nextLocation)} Meters"
  }

  def getDirection(currentLocation: Location, nextLocation: Location): String = {
    val h = nextLocation.latitude - currentLocation.latitude
    val b = nextLocation.longitude - currentLocation.longitude

    if(h<0 && b<0) {
      "Left"
    } else if (h<0 && b>0) {
      "Right"
    } else if (h>0 && b<0) {
      "Slight Left"
    } else {
      "Slight Right"
    }
  }

  def calculateDistanceInMeters(currentLocation: Location, nextLocation: Location) = {
    val R = 6371e3; // metres
    val φ1 = currentLocation.latitude * Math.PI/180 // φ, λ in radians
    val φ2 = nextLocation.latitude * Math.PI/180
    val Δφ = (nextLocation.latitude - currentLocation.latitude) * Math.PI/180
    val Δλ = (nextLocation.longitude - currentLocation.longitude) * Math.PI/180
    val a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
      Math.cos(φ1) * Math.cos(φ2) *
        Math.sin(Δλ/2) * Math.sin(Δλ/2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))

    R * c
  }

  // MAIN
  println(prepareNavigationAlerts(route, Seq.empty).mkString("\n"))
}
