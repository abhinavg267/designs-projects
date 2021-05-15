package init

import _2048.DataClasses.{Direction, Location}
import _2048.{Game, G2048}

import scala.util.{Failure, Success, Try}

object G2048Driver extends App {
  val game: Game = G2048.startANewGame(4)
  println(game.showCanvas)

  while(true) {
    Try {
      println(s"Enter row, column and direction (R, L, U, D), separated by space. For eg: 1 2 U or write ADD, to add a new tile: ")
      val input = scala.io.StdIn.readLine()
      val firstCommand :: restOfTheCommands = input.split(" ").toList

      if(firstCommand == "ADD") {
        game.addNewTile()
        println(game.showCanvas)
      } else {
        val (rowStr, columnStr :: directionStr :: _) = (firstCommand, restOfTheCommands)
        val direction: Direction = Direction.fromString(directionStr)

        game.moveTile(Location(rowStr.toInt, columnStr.toInt), direction)
        println(game.showCanvas)
      }
    } match {
      case Failure(exception) =>
        exception.printStackTrace()
        println(s"Error:Try again")
      case Success(_) => ()
    }
  }
}
