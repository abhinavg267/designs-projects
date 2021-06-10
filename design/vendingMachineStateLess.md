## Usage 
- Add new product 
    - product may have version 
- Add quantities 
- Get Product 
- Process Payment 
- Change Management / Cashier 

## Entities 
- Price
    - amount: BigDecimal

- Product 
    - ProductId 
    - ProductBaseId
    - Name
    - Price 
    
- ProductItem
    - ProductItemId
    - ProductId 
    - LocationId
    - Status
    
- Payment
    - PaymentId 
    - PaymentAmount

- Transactions 
    - TransactionId 
    - PaymentId 
    
- TransactionProductAssociation 
    - TransactionId 
    - ProductId  
    
- Location
    - LocationId 
    - rowIdx
    - columnIdx
    - size
    
## Services 

- ProductManager 
    - `addNewProduct(name: String, price: Price, quantity: Int): Product`
    - `addNewProductVersion(productBaseId: ProductBaseId, name: String, price: Price, quantity: Int): Product` 
    - `getAllAvailableProducts: List[Product]`
    
- LocationManager
    - ``
    
- Cashier
