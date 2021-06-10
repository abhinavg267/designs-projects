package tic_tac_toe

import tic_tac_toe.models.{GameBoard, Move}

object GameBoardManager {
  def isPossibleMove(board: GameBoard, move: Move): Boolean = {
    board.board(move.location.rowIdx)(move.location.columnIdx).isEmpty
  }

  def isWinningMove(board: GameBoard, move: Move): Boolean = {
    // TODO: Implementation
    false
  }
}
