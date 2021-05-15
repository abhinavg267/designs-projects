package init

import snake_and_ladder.GameManager
import snake_and_ladder.models.DataClasses.TileConnectorType.{LadderConnectorType, SnakeConnectorType}
import snake_and_ladder.models.DataClasses.{Move, Player, TileConnector}

object SnakeAndLadderDriver extends App {
  val a1 = Player("A1")
  val a2 = Player("A2")
  val a3 = Player("A3")

  val players = List[Player](a1, a2, a3)
  val tileConnectors = Map(
    0 -> TileConnector(LadderConnectorType, nextIndex = 4),
    3 -> TileConnector(LadderConnectorType, nextIndex = 8),
    4 -> TileConnector(SnakeConnectorType, nextIndex = 3)
  )

  var game = GameManager.startNewGame(players, tileConnectors = tileConnectors)

  game = GameManager.makeAMove(game, a1, Move(1))
  game = GameManager.makeAMove(game, a2, Move(2))
  game = GameManager.makeAMove(game, a3, Move(5))
  game = GameManager.makeAMove(game, a1, Move(2))

  println(GameManager.showGame(game))
}
