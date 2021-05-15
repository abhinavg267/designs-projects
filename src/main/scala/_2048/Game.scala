package _2048

import _2048.DataClasses.{Direction, Location, Tile}

class Game(size: Int) {
  private val canvas: Array[Array[Option[Tile]]] = Array.fill[Option[Tile]](size, size)(None)

  def addNewTile(): Unit = {
    if(isFull) throw new Exception(s"Cannot add new Tile!")
    else _addNewTile()
  }

  def moveTile(fromLocation: Location, direction: Direction): Unit = {
    getTileAtLocation(fromLocation) match {
      case Some(tileToMove) =>
        val targetLocation = fromLocation.getNewLocation(direction)
        if(isLocationAvailable(targetLocation)) {
          getTileAtLocation(targetLocation) match {
            case Some(tileAtTargetLocation) =>
              if(tileAtTargetLocation.value == tileToMove.value) {
                updateTileAtLocation(fromLocation, None)
                updateTileAtLocation(targetLocation, Some(Tile(tileToMove.value*2)))
              } else throw new Exception(s"Cannot move since tile values are not equal")
            case None =>
              updateTileAtLocation(fromLocation, None)
              updateTileAtLocation(targetLocation, Some(tileToMove))
          }
        }
        else throw new Exception(s"Cannot move from location: $fromLocation to direction: $direction")

      case None => throw new Exception(s"Cannot move from location: $fromLocation to direction: $direction, since no tile present")
    }
  }

  def getTileAtLocation(location: Location): Option[Tile] = {
    canvas(location.row)(location.column)
  }

  def isLocationAvailable(location: Location): Boolean = {
    location.row < size && location.column < size && location.row >=0 && location.column >= 0
  }

  def showCanvas: String = {
    canvas.map(_.map {
      case Some(tile) => s"${tile.value}"
      case None => "."
    }.mkString(", ")).mkString("\n")
  }

  @scala.annotation.tailrec
  private def _addNewTile(): Unit = {
    val row = scala.util.Random.nextInt(size)
    val column = scala.util.Random.nextInt(size)
    if(canvas(row)(column).isEmpty) {
      val newTile = Tile(2)
      canvas(row)(column) = Some(newTile)
    } else {
      _addNewTile()
    }
  }

  private def updateTileAtLocation(location: Location, tile: Option[Tile]): Unit = {
    canvas(location.row)(location.column) = tile
  }

  private def isFull: Boolean = {
    val filledTiles = canvas.foldLeft(0) { case (cnt, newRow) =>
      cnt + newRow.foldLeft(0) { case (cnt1, tileOpt) =>
        if(tileOpt.isDefined) cnt1 + 1
        else cnt1
      }
    }

    filledTiles == size*size
  }
}


//sealed trait Canvas {
//  def isFull: Boolean
//  def show: String
//  def dimension: Int
//  def tiles: Array[Array[Option[Tile]]]
//}
//object Canvas {
//  private case class CanvasImpl(tiles: Array[Array[Option[Tile]]]) extends Canvas {
//    val dimension: Int = tiles.length
//
//    override def isFull: Boolean = {
//      val size = tiles.foldLeft(0) { case (cnt, newRow) =>
//        cnt + newRow.foldLeft(0) { case (cnt1, tileOpt) =>
//          if(tileOpt.isDefined) cnt1 + 1
//          else cnt1
//        }
//      }
//
//      size == capacity
//    }
//
//    override def show: String = {
//      tiles.map(_.map {
//        case Some(tile) => s"${tile.value}"
//        case None => "."
//      }.mkString(", ")).mkString("\n")
//    }
//
//    private val capacity: Int = dimension*dimension
//  }
//
//  def getEmptyCanvas(dimension: Int): Canvas = {
//    val tiles = Array.fill[Option[Tile]](dimension, dimension)(None)
//    CanvasImpl(tiles)
//  }
//
//  def getTileAtLocation(location: Location)(implicit canvas: Canvas): Option[Tile] = {
//    canvas.tiles(location.row)(location.column)
//  }
//
//  def updateTileAtLocation(location: Location, tile: Option[Tile])(implicit canvas: Canvas): Unit = {
//    canvas.tiles(location.row)(location.column) = tile
//  }
//
//  def isLocationAvailable(location: Location)(implicit canvas: Canvas): Boolean = {
//    location.row < canvas.dimension && location.column < canvas.dimension
//  }
//
//  def addNewTile(canvas: Canvas): Canvas = {
//    if(canvas.isFull) throw new Exception(s"Cannot add new Tile!")
//    else _addNewTile(canvas)
//  }
//
//  @scala.annotation.tailrec
//  private def _addNewTile(canvas: Canvas): Canvas = {
//    val row = scala.util.Random.nextInt(canvas.dimension)
//    val column = scala.util.Random.nextInt(canvas.dimension)
//    if(canvas.tiles(row)(column).isEmpty) {
//      val newTile = Tile(2)
//      canvas.tiles(row)(column) = Some(newTile)
//      CanvasImpl(canvas.tiles)
//    } else {
//      _addNewTile(canvas)
//    }
//  }
//}
