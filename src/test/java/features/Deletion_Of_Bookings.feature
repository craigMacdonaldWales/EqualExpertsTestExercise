@deletebooking

Feature: Delete booking
Clicking the delete button for a given booking will remove that booking from the booking display.

Scenario: Delete a booking

Given I am on the hotel booking homepage
When I create a new booking with the following data

| Firstname							| Surname				| Price			| Deposit		| CheckIn | CheckOut |
|	NewBooking 						|	Creationtest	|	100				|	true 			| today+1 | today+2		|

And I click the Save button

Then My booking is created

And I click the Delete button

Then My booking is deleted