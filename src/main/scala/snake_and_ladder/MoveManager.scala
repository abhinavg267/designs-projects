package snake_and_ladder

import snake_and_ladder.models.DataClasses.{Game, Move, Player}

object MoveManager {
  def getNextPlayerToMove(game: Game): Player = {
    val lastMoveOpt: Option[(Move, Player)] = game.moves.lastOption

    lastMoveOpt match {
      case Some((lastMove, lastPlayer)) =>
        if(lastMove == Move(6)) getPossiblePlayer(game, lastPlayer)
        else getPossiblePlayer(game, getNextPlayer(game.players, lastPlayer))

      case None => game.players.head
    }
  }

  @scala.annotation.tailrec
  private def getPossiblePlayer(game: Game, possiblePlayer: Player): Player = {
    if(game.piecesOnBoard.get(possiblePlayer).fold(true)(t => !(t.currentPosition == game.gameBoard.last))) possiblePlayer
    else getPossiblePlayer(game, getNextPlayer(game.players, possiblePlayer))
  }

  private def getNextPlayer(allPlayers: List[Player], lastPlayer: Player): Player = {
    val s = allPlayers.indexWhere(_ == lastPlayer)
    if(s == -1) throw new Exception(s"Cannot find turn seq for last player: $lastPlayer")
    else allPlayers((s+1)%allPlayers.length)
  }
}
