## Design 
### Entity
- `Player`  
- `Location`
- `Tile(player: Player)`
- `Move(location: Location, tile: Tile)`
- `GameBoard(board: Array[Array[Option[Tile]]])`
- `Game(gameBoard: GameBoard, moveSeq: List[Player], moves: Seq[Move])` 

### GameManager 
- `getNewGame(size: Int, moveSeq: List[Player]): Game`
- `getNextPlayer(game: Game): Option[Player]`
- `makeAMove(game: Game, move: Move): Game` 
    - validate that last move was not a winning move
    - add new tile in given location
- `isGameOn(game: Game): Boolean`
    - if last move was winning move, return true
    - else false

### GameBoardManager 
- `prepareAndGetEmptyBoard(size: Int): GameBoard`
- `isPossibleMove(board: GameBoard, move: Move): Boolean`
- `isWinningMove(board: GameBoard, move: Move): Boolean`




