# Requirements and Clarifications

**TODO: Add Requirements and clarifications as specified in the README.md**
removegame - remove a game from a user's inventory or from being sold
The front end will prompt for the game name and if necessary, the game's owner.
This information is saved to the daily transaction file
Constraints:
semi-privileged transaction - Non-admins can only remove their own games. In admin mode, this allows any game to be removed from any user.
game can be in the owner's inventory OR one of those the user has put up for sale
cannot remove a game that was purchased or put up to sale on the same day

gift - give a user a game from another user
The front end will prompt for the game name, the game's receiver, and if necessary, the game's owner.
This information is saved to the daily transaction file
Constraints:
semi-privileged transaction - Non-admins can only gift their own games to another. In admin mode, this allows any game to be gifted from any user to any other.
game must be in the owner's inventory OR one of those the user has put up for sale
if the game is one the owner had purchased, it is removed from their inventory when gifted
cannot gift a game that was purchased or put up to sale on the same day
cannot gift a game to a user who already owns a game with the same name

Lines in the daily transaction file appear like the following:

XX IIIIIIIIIIIIIIIIIII UUUUUUUUUUUUUUU SSSSSSSSSSSSSSS

Where:

XX
is a two-digit transaction code: 08-removegame, 09-gift.

IIIIIIIIIIIIIIIIIII
is the game name

UUUUUUUUUUUUUUU
is the owner's username

SSSSSSSSSSSSSSSS
is the receiver's username

---------------------------
its possible to have no admins which means that any admin specific transactions cannot be completed (possible option
is a god user to create new Users for initial population only)

A users balance cannot go below 0

A admin/fullstandard user has a different list for games they bought/games they can sell 

you cannot gift a seller a game

it is possible to have no admin users in a system and still be valid

if no admins are present it is impossible to preform any privileged transactions

the max amount of added balance only applies to the privileged transaction add credits

A game can be in a users inventory yet also not on sale

A fatal error is anything that would cause a crash

A seller cannot refund if missing the credits for the transaction

games up for sale have infinite copies

Code keeps any whitespaces from daily.txt

remove game only removes from one person

game names can contain any characters

admins must follow any constraints unless otherwise specified

you need to keep a record of their UserType.

So if a FullStandard is trying to login as an Admin you log them in as FullStandard but show a warning.

admins dont need to be show in addcredit line just need to be logged in