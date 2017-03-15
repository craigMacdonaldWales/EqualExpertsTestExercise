@createBooking

Feature: Create Booking

Scenario: creating new booking with deposit true

Given I am on the hotel booking homepage
When I create a new booking with the following data

| Firstname							| Surname				| Price			| Deposit		| CheckIn | CheckOut |
|	NewBooking 						|	Creationtest	|	100				|	true 			| today+1 | today+2		|

And I click the Save button

Then My booking is created

And I close the driver instance

Scenario: creating new booking with deposit false

Given I am on the hotel booking homepage
When I create a new booking with the following data

| Firstname							| Surname				| Price			| Deposit		| CheckIn | CheckOut |
|	NewBooking 						|	Creationtest	|	100				|	false 			| today+1 | today+2		|

And I click the Save button

Then My booking is created

And I close the driver instance
