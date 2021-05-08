## Splitwise 

### Usages 
- Register a new user
- Add Expenses with different splitting strategies
    - Equal Split 
    - Exact Split
    - Percentage Split
- Get Balance Details 
    - Money Owed to other users 
    - Money Owed to self  
    
### DB 
- Users
    - UserId
    - Name
    
- Transactions 
    - TransactionId 
    - User 
    - Splitting Strategy 
    - Transaction Amount
    - Transaction Type 
        - Lending
        - Borrowing
    
- Transaction Components 
    - TransactionComponentId
    - TransactionId
    - Lender
    - Borrower
    - Transaction Component Amount
    
### Services 
- UserService 
    - `addNewUser(name: String): UserId`
- InfoService 
    - `getUserBalance(uid: Int)`
    - `getRequiredSettlement(uid: Int, settlingFor: UserId)`
    - `getUserBalanceComponents(uid: Int): Map[Int, Price]`
- TransactionManager 
    - `addNewTransaction(uid: Int, transactionType: TransactionType, transactionAmount: Price, split: Split)`
    
    
### DataClasses 
- User
- Price - amount, currency
- Transaction 
- TransactionComponent 
- TransactionType 
- Split
    - `ExactSplit(amountByUser: Map[UserId, Price])`
    - `PercentageSplit(percentageByUser: Map[UserId, BigDecimal])`
    - `EqualSplit`
    
    
    
    
    
    
    
    
    
    
    
    
    