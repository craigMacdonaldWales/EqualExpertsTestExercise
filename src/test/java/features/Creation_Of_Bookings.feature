@createBooking

Feature: Create Booking
We are able to create new booking records by keying in valid values. A time string is appended to the Firstname value to guarantee exclusivity.
In this feature we test two simple variances. If Date range validation was subsequently added, we could implment more scenarios to support this.

Scenario: creating new booking with deposit true

Given I am on the hotel booking homepage
When I create a new booking with the following data

| Firstname							| Surname				| Price			| Deposit		| CheckIn | CheckOut |
|	NewBooking 						|	Creationtest	|	100				|	true 			| today+1 | today+2		|

And I click the Save button

Then My booking is created

Scenario: creating new booking with deposit false

Given I am on the hotel booking homepage
When I create a new booking with the following data

| Firstname							| Surname				| Price			| Deposit		| CheckIn | CheckOut |
|	NewBooking 						|	Creationtest	|	100				|	false 			| today+1 | today+2		|

And I click the Save button

Then My booking is created
