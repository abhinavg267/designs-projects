## Storage 
- Board Layout Config i.e. all the connectors
- Game
- Player 
- Move - gameId, playerId 

## Entities 
- Board
    - Stores current state with
        - `Array[Slot]`
        - turnSeq
        - lastTurn
        - moves: `List[Move]`
        - state: `Map[Players, Piece]`
    
- Slot 
    - any metadata
    - next 
        - Connector
            - Snake Type Connector
            - Ladder Type Connector
            
- Player 
    - `N` players can play
    - each will have a turn 
    
- Piece 
    - Any Metadata 
    - Location 
    
## Points 
- Is it okay for a piece to know its Location? Or should board know it?
- Final position is 100, initial position is Null, i.e state will not have any mapping for the player
    
## SnakeAndLadder
- initiateAGame(players: `List[Players]`, turnSeq: `List[Players]`, moves: `List[Moves]`, connectors: `List[Connector]`): Board  
- makeAMove(player: Player, move: Move): Board 
    - validate it is players' turn
    - get players Position
    - applyMove 
- getNextTurn: Player
    - if last move was a 6, return lastTurn
    - else evaluate get nextTurn and update lastTurn 
        - use turnSeq - until the next player can move