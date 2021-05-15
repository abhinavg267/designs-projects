package _2048

object G2048 {
  def startANewGame(canvasSize: Int): Game = {
    val newGame = new Game(canvasSize)
    newGame.addNewTile()
    newGame
  }
}
