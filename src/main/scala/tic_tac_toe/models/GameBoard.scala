package tic_tac_toe.models

import scala.collection.compat.immutable.ArraySeq

sealed trait GameBoard {
  def board: ArraySeq[ArraySeq[Option[Tile]]]
}
object GameBoard {
  private case class GameBoardImpl(board: ArraySeq[ArraySeq[Option[Tile]]]) extends GameBoard

  def prepareAndGetEmptyBoard(size: Int): GameBoard = {
    val board: ArraySeq[ArraySeq[Option[Tile]]] = ArraySeq.fill[Option[Tile]](size, size)(None)
    GameBoardImpl(board)
  }

  def addNewTile(gameBoard: GameBoard, location: Location, tile: Tile): GameBoard = {
    val Location(rowIdx, columnIdx) = location
    val updatedBoard = gameBoard.board.updated(rowIdx,
      gameBoard.board(rowIdx).updated(columnIdx, Some(tile)))

    GameBoardImpl(updatedBoard)
  }
}

