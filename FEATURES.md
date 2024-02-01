# Features
This program runs a store when given a daily.txt file. This file must be placed in the src folder to be accesed by StoreSystem.java.

Our program uses the Strategy design pattern to execute most of its functionality.
This works by using StoreSystem.java to be the central "Controller" that calls the appropriate strategy for each transaction.
The strategy design pattern helps split implementation for different memebers easier, and ensures that we can easily add new
functionality to our program easily. This provides a highly efficient and highly flexible system that can be iterated on easier.


All strategies implement a base Strategy.java interface so that they all have a common .run() functionality. This ensures that
all strategies are given the appropriate input.
To call .run() for any strategies, you need the transaction line, the arraylist of all users in the system, and arraylist of all games
in the system.

The strategies, and how the StoreSystem works are broken down here:
SystemReadDatabaseStrategy:
	Reads all the information from two files in our database called UserInfo.txt and GameInfo.txt. These files are stored
	in the src folder. After reading all the information, appropriate users and games are created and populated.
	This is called at the start of the system, before reading anything from daily.txt.
	
UserInitialFunctionalityStrategy:
	This strategy handles the transaction type with the following code:
	XX UUUUUUUUUUUUUUU TT CCCCCCCCC
	 Where XX is a two-digit transaction code: 00-login, 01-create, 02-delete, 06-addcredit, 10-logout
       UUUUUUUUUUUUUUU is the username, which ends with whitespaces if it does not fill the whole space
       TT is the user type (AA=admin, FS=full-standard, BS=buy-standard, SS=sell-standard)
       CCCCCCCCC is the available credit
	
	It is given the string in the format XX UUUUUUUUUUUUUUU TT CCCCCCCCC, which is later split to determine what to do.
	The StoreSystem identifies this transaction type, and then calls this strategy.
	
UserBuyStrategy:	
	This strategy handles the transaction type with the following code:
	XX IIIIIIIIIIIIIIIIIII SSSSSSSSSSSSSSS UUUUUUUUUUUUUU
	Where XX is a two-digit transaction code: 04-buy.
		IIIIIIIIIIIIIIIIII is the game name
        SSSSSSSSSSSSSSS is the seller’s username
        UUUUUUUUUUUUUUU is the buyer's username
	
	The format of the transaction type is split appropriately, and then each part is read to determine what to do.
	It handles invalid seller and buyer user types, and ensures all users are in the system.
	The StoreSystem identifies this transaction type, and then calls this strategy.
	
UserGiftStrategy:
	This strategy handles the transaction type with the following code:
	XX IIIIIIIIIIIIIIIIIII UUUUUUUUUUUUUUU SSSSSSSSSSSSSSS
	Where XX is a two-digit code 09 - gift
	IIIIIIIIIIIIIIIIIII is the game name
	UUUUUUUUUUUUUUU is the owner's username
	SSSSSSSSSSSSSSSS is the receiver's username
	
	Again, all this information is split appropriately, as it is all one string, and then the strategy ensures all users
	are valid and the appropriate type.
	
UserSellStrategy:
	This strategy handles the transaction type with the following code:
	XX IIIIIIIIIIIIIIIIIII SSSSSSSSSSSSS DDDDD PPPPPP
	XX represents two-digit transaction code: 03-sell.
    IIIIIIIIIII is the gamename.
    SSSSSSSSSSS is the username.
	DDDDD is the discount percentage.
	PPPPPP is the saleprice.
	
	All information is split properly, and the strategy ensures that all users are the appropriate user type, and prints
	errors if anything is invalid.
	
UserRefundStrategy:
	This strategy handles the transaction type with the following code:
	XX UUUUUUUUUUUUUUU SSSSSSSSSSSSSSS CCCCCCCCC
	Where XX is a two-digit transaction code: 05-refund
       UUUUUUUUUUUUUUU is the buyer's username
       SSSSSSSSSSSSSSS is the seller’s username
      CCCCCCCCC is the available credit
	
	All information is split properly, and refund balance values are handled by in-built user functionality.
	
UserRemoveStrategy:
	XX IIIIIIIIIIIIIIIIIII UUUUUUUUUUUUUUU SSSSSSSSSSSSSSS
	XX represents two-digit transaction code: 08-remove game.
      IIIIIIII represents the gamename.
      UUUUUUUU represents the username.
      SSSSSSSS represents the receiver's username which is not necessary in this transaction type.
	  
	All information is split properly, and all users and game names are checked to be valid.
	
SystemEndStrategy:
	This strategy takes all the users and games in the system, including the states they are at and writes them to a file
	with a given format. Each game and user's information is written to the database with their toString(). Games don't have
	a seperator as each toString() is exactly 2 lines long, which is handled by the SystemReadDatabaseStrategy. Users
	have a seperator which is longer than their valid usernames to ensure that no issues can arise.
	This is called after reading the entire daily.txt file.
	
NOTE: If the system has been initialized, and no user is in the system, then a temporary admin is initialized to ensure
that new users can be created. This user is removed at the end of the day. This temporary user is given the username "godUser".

There are 4 user types, Admin, BuyStandardUser, FullStandardUser and SellStandardUser.
All users have some shared functionalities, such as logging in. To ensure code isn't duplicated, an abstract User.java
class is used to hold shared functionalities, and shared attributes.

The Purchase.java class is used to check if games have been purchased within 24 hours. This is updated when the database is
read through SystemReadDatabaseStrategy.

Note: When a new user is created from the daily.txt file, they are automatically online until logged off.

HOW TO RUN THE SYSTEM:
	To run the system, place the daily.txt file in the src folder. Then, in whatever class you please (ideally the StoreSystem.java),
	you create a StoreSystem object, and call StoreSystem.runSystem().
	The system will then run, handling all the lines in the daily.txt file.
	To run the next day's daily.txt file, replace the current daily.txt file with the new one. Then call runSystem() again.
	

	
