@bookingInputValidation

Feature: Input Validation
Entering numeric values in the firstname and surname fields, an alpha string in the price field, along with non-standard dates will 
result in the save button being disabled. This feature clicks the save button, and verifies that no new record (new row) is created.

Scenario: creating new booking with invalid field values

Given I am on the hotel booking homepage
When I create a new booking with the following data

| Firstname							| Surname				| Price			| Deposit			| CheckIn | CheckOut |
|	123 									|	123						|	ABC				|	false 			| abc 		| abc				|

And I click the Save button

Then Creation of the new record is rejected