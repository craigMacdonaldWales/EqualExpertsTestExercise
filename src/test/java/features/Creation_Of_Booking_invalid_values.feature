@bookingInputValidation

Feature: Input Validation

Scenario: creating new booking with invalid field values

Given I am on the hotel booking homepage
When I create a new booking with the following data

| Firstname							| Surname				| Price			| Deposit			| CheckIn | CheckOut |
|	123 									|	123						|	ABC				|	false 			| abc 		| abc				|

And I click the Save button

Then Creation of the new record is rejected

And I close the driver instance