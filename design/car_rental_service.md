## Usage 
1. Rental service has multiple branches throughout the city. Assume one city for now.
2. Each branch has a limited number of different kinds of vehicles.
3. Each vehicle can be booked with a predefined price. For simplicity, assume fixed pricing.
4. Each vehicle can be booked in multiples of 1-hour slots each. (For simplicity, assume slots of a single day)
5. No past bookings should be allowed.


## Requirements
1. Onboard a new branch with available vehicles
2. Onboard a new vehicle(s) of an existing type to a particular branch
3. Rent a vehicle for a time slot and a vehicle type(the lowest price as the default choice extendable to any other strategy).
4. Display available vehicles for a given branch sorted on price
5. The vehicle will have to be dropped at the same branch where it was picked up.


## Entity 
- User

- Branch

- Vehicles
    - Vehicle
        - VehicleId
        - VehicleType 

- BranchStores
    - BranchStore
        - BranchId
        - List[VehicleId, Int]
        
- Reservations
    - Reservation
        - ReservationId
        - UserId
        - BranchStorageId
        - StartTime
        - EndTime
        - AmountPaid 

## Services 
- ReservationManager 
    - rentAvailableVehicle(branchId, userId, vehicleType, startTime, endTime): Option[ReservationId]
    - returnAVehicle(userId, branchId, vehicleId)
    
- BranchManager 
    - Add new Branch (Branch, Map[VehicleId, Int])
    - Add more vehicles (Map[vehicleId, Int])
    
- Vehicle Manager
    - Add new vehicle