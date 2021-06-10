package tic_tac_toe.models

sealed trait Game {
  def gameBoard: GameBoard
  def moveSeq: List[Player]
  def moves: List[Move]
}

object Game {
  private case class GameImpl(gameBoard: GameBoard, moveSeq: List[Player],
                              moves: List[Move]) extends Game

  def prepareAndGetNewGame(size: Int, moveSeq: List[Player]): Game = {
    val gameBoard = GameBoard.prepareAndGetEmptyBoard(size)
    GameImpl(gameBoard, moveSeq, moves = List.empty[Move])
  }

  def addNewMove(game: Game, move: Move): Game = {
    val updatedBoard = GameBoard.addNewTile(game.gameBoard, move.location, move.tile)
    val updatedMoves = game.moves :+ move
    GameImpl(updatedBoard, moveSeq = game.moveSeq, updatedMoves)
  }
}
