package snake_and_ladder.models

object DataClasses {
  case class Game(gameBoard: Array[Tile], players: List[Player],
                  moves: List[(Move, Player)], piecesOnBoard: Map[Player, Piece])

  case class Move(value: Int) {
    assert(value <= 6 && value >= 1, s"Cannot move a piece by less then 1 places and more then 6 places")
  }

  sealed trait TileConnectorType
  object TileConnectorType {
    case object SnakeConnectorType extends TileConnectorType
    case object LadderConnectorType extends TileConnectorType
  }

  case class TileConnector(tileConnectorType: TileConnectorType, nextIndex: Int)

  case class Tile(index: Int, next: Option[TileConnector])

  case class Piece(currentPosition: Tile)

  case class Player(name: String)
}
