## Usage 
- Add new product 
    - product may have version 
- Add quantities 
- Get Product 
- Process Payment 
- Change Management / Cashier 

- one location will always have one type of product

## Entity 
- Place
    - size: `Int`
    - product: `Option[Product]`
    
- VendingMachine
    - places: `ArraySeq[Place]`
    - transactions: `List[Transactions]`

- Product 
    - id: ProductId
    - name: String
    - price: Price 
    - availableQuantity: Int
    
- Payment
    - amount: Price 

- Transactions
    - productId: `ProductId`
    - quantity: `Int`
    - transactionType
        - ADD_PRODUCT
        - BUY_PRODUCT 
    - payment: `Option[Payment]`
    
## Services 
- VendingMachineManager
    - `addproducts(products: Set[Product]): Unit`
    - `buyProducts(selectedProductsAndQuantities: List[(ProductId, Int)], amountPaid: Price): Unit`

- InfoManager 
    - `getAvailableProducts(): Set[Product]`
    - `calculatePrice(selectedProductsAndQuantities: List[(ProductId, Int)]): Price`
    
- PaymentManager
    - `createNewPayment(amount: Price): Payment`
    
- ProductManager
    - `addNewProducts(products: Set[Product]): Unit` 
    - `updateProductQuantities(selectedProductsAndDeltaQuantities: List[(ProductId, Int)]): Unit`
    - `getProducts(productIds: Set[ProductId]): Set[Product]`
    
- TransactionManager 
    - `createNewTransaction()`
    
## Workflow 
- VendingMachineManager.addProducts() 
    - add new products 
    - create transactions
- VendingMachineManager.addProducts()
    - add existing and new products 
- InfoManager.getAvailableProducts() 
- InfoManager.calculatePrice() 
- Cashier.buyProducts()
    - create transaction 
    - 
        
