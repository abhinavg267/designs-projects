package snake_and_ladder

import snake_and_ladder.models.DataClasses.{Game, Move, Tile, TileConnector}

import scala.annotation.tailrec
import scala.collection.compat.immutable.ArraySeq

object BoardManager {
  def prepareABoard(size: Int, tileConnectors: Map[Int, TileConnector]): ArraySeq[Tile] = {
    (0 until size).foldLeft(ArraySeq.empty[Tile]) { case (boardYet, newTileIdx) =>
      val tileConnector = tileConnectors.get(newTileIdx)
      boardYet :+ Tile(newTileIdx, tileConnector)
    }
  }

  def getNextTile(game: Game, currentTileOpt: Option[Tile], move: Move): Option[Tile] = {
    currentTileOpt match {
      case Some(currentTile) =>
        val nextTileIndex = currentTile.index + move.value
        if(nextTileIndex < game.gameBoard.length)
          Some(evaluateTileConnectors(game, game.gameBoard(currentTile.index + move.value)))
        else None

      case None => Some(evaluateTileConnectors(game, game.gameBoard(move.value-1)))
    }
  }

  @tailrec
  private def evaluateTileConnectors(game: Game, tile: Tile): Tile = {
    tile.next match {
      case Some(nextTileConnector) => evaluateTileConnectors(game, game.gameBoard(nextTileConnector.nextIndex))
      case None => tile
    }
  }
}
