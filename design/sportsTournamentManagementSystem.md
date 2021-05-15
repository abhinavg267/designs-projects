### UseCases 
- Player can register 
    - Accommodation 
- Team can register 
- Create new Tournament 
- Create New Game 
    - Teams 
    - Players 
    - Games can have types 


### Storage 
- Players 
    - PlayerId 
    - PlayerName 

- Teams
    - TeamId 
    - TeamName 

- TeamAndPlayerAssociations 
    - TeamId 
    - PlayerId 

- Tournaments
    - TournamentId 

- Games
    - GameId
    - TournamentId 
    - TeamA 
    - TeamB (validate that Players in TeamA and TeamB are exclusive)
    - ResultForTeamA 
    - 