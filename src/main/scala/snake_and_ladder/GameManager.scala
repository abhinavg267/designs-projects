package snake_and_ladder

import snake_and_ladder.models.DataClasses._

object GameManager {
  def startNewGame(players: List[Player], tileConnectors: Map[Int, TileConnector]): Game = {
    val gameBoard = BoardManager.prepareABoard(100, tileConnectors)
    Game(gameBoard, players, moves = List.empty[(Move, Player)], piecesOnBoard = Map.empty[Player, Piece])
  }

  def rollADice: Move = Move(1 + scala.util.Random.nextInt(6))

  def makeAMove(game: Game, player: Player, move: Move): Game = {
    if(isGameOn(game)) {
      val nextPlayerToMove = MoveManager.getNextPlayerToMove(game)
      if(nextPlayerToMove == player) {
        val playersPieceOpt = game.piecesOnBoard.get(player)
        val nextPosition: Tile = BoardManager.getNextTile(game, playersPieceOpt.map(_.currentPosition), move)
          .getOrElse(throw new Exception(s"Cannot move: $move from tile ${playersPieceOpt}"))

        // Update Game
        val moves = game.moves :+ (move, player)
        val newPositions = game.piecesOnBoard - player + (player -> Piece(nextPosition))
        Game(game.gameBoard, game.players, moves, newPositions)

      } else throw new Exception(s"Invalid player to move, the turn is for player: $nextPlayerToMove")
    } else throw new Exception(s"The Game is already over")
  }

  def isGameOn(game: Game): Boolean =
    !(game.piecesOnBoard.count(_._2.currentPosition == game.gameBoard.last) == game.players.length)

  def showGame(game: Game): String = {
    s"GameBoard: ${game.gameBoard.map(t => s"(${t.index}, ${t.next})").mkString(", ")}\n" +
      s"TurnSeq: ${game.players.map(p => s"($p)").mkString(", ")}\n" +
      s"Pieces: ${game.piecesOnBoard.map(p => s"($p)").mkString(", ")}\n"
  }
}
