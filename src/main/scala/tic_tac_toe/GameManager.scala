package tic_tac_toe

import tic_tac_toe.models.{Game, Move, Player}

object GameManager {
  def getNewGame(size: Int, moveSeq: List[Player]): Game = {
    Game.prepareAndGetNewGame(size, moveSeq)
  }

  def getNextPlayer(game: Game): Option[Player] = {
    if(isGameOn(game)) {
      val lastPlayerToMakeMoveOpt = game.moves.lastOption.map(_.tile.player)
      val nextPlayerToMakeMove = lastPlayerToMakeMoveOpt match {
        case Some(lastPlayerToMakeMove) =>
          val playerIdx = game.moveSeq.indexWhere(_ == lastPlayerToMakeMove)
          val nextPlayerIdx = (playerIdx+1)%game.moveSeq.length
          game.moveSeq(nextPlayerIdx)

        case None => game.moveSeq.head
      }

      Some(nextPlayerToMakeMove)
    }
    else None
  }

  def makeAMove(game: Game, move: Move) = ???

  def isGameOn(game: Game): Boolean = {
    val lastMoveOpt = game.moves.lastOption
    lastMoveOpt match {
      case Some(lastMove) if GameBoardManager.isWinningMove(game.gameBoard, lastMove) => false
      case Some(_) | None => true
    }
  }
}
