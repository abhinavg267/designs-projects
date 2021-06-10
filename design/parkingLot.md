## Usage
- Parking Lot 
    - system handles single parking lot 
    - there can be multiple user interacting with the system 
- There can be multiple parking spots 
    - spot type 
    - location 
    - dimension
- We can have a pricing model based on spot type for now, can add more complex pricing model later (this should be cofig driven)
- User should be able to register to the system
- User should be able to Reserve a spot 
    - cancel the reservation 
- User needs to pay 

## Entity 
- User 
    - userId 
    - name 
    - other metadata
- Spot 
    - SpotId
    - SpotType 
        - Dimension 
    - location 
- Reservation 
    - ReservationId 
    - UserId 
    - `Option[SpotId]`
    - `Option[StartTime]`
    - `Option[EndTime]`
    - PaymentId
    - Type
        - Reservation
        - Cancellation
- Payment 
    - PaymentId 
    - Amount 
    - Status
        - Pending 
        - Processed
        
## Services 
- UserManager
    - `registerNewUser(name: String): UserId`
- SpotManager
    - `registerNewSpot(spotType: SpotType, location: Location): UserId`
    - `calculatePrice(spotType: SpotType): Price`
    - `getAllSpots(spotType: SpotType): List[Spot]`
- PaymentManager
    - `initiateNewPayment(amount: Price): PaymentId`
    - `confirmPayment(paymentId: PaymentId): Unit`
- ReservationManager
    - `getAvailableSpots(spotType: SpotType, startTime: DateTime, endTime: DateTime): List[(Spot, Price)]`
    - `reserveSpot(userId: UserId, spotId: SpotId, startTime: DateTime, endTime: DateTime): Reservation`
    - `cancelReservation(reservationId: ReservationId): Unit`
    - `addNewReservationOrCancallation(userId: UserId, reservationType: ReservationType, paymentId: PaymentId, 
            spotId: Option[SpotId], startTime: Option[DateTime], endTime: Option[DateTime]): ReservationId` 