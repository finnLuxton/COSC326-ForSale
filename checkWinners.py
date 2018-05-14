#!/usr/bin/python
import sys
'''
For Sale file to check winners.txt and output the winrate for included strategies.
winners.txt must be generated using the loop.sh script

Authors - Finn Luxton
          Nic Coles
          Dominik Pashcke
          Tom Kent-Peterson
'''

# Takes in a command line argument for the number of players
numPlayers = sys.argv[1]
file = open("winners.txt", "r")

count = 0
testOneWins = 0;
testTwoWins = 0;
testThreeWins = 0;
testFourWins = 0;
testFiveWins = 0;
testSixWins = 0;
randomWins = 0;
folderWins = 0;

# Finds the winner from each game results and increments their respective winner count
for line in file:
    if count % int(numPlayers) == 0:
        if line.split()[1] == "TheOne":
            testOneWins += 1
        if line.split()[1] == "TheTwo":
            testTwoWins += 1
        if line.split()[1] == "TheThree":
            testThreeWins += 1
        if line.split()[1] == "TheFour":
            testFourWins += 1
        if line.split()[1] == "TheFive":
            testFiveWins += 1
        if line.split()[1] == "TheSix":
            testSixWins += 1
        if line.split()[1] == "RA":
            randomWins += 1
        if line.split()[1] == "NA":
            folderWins += 1
    count += 1

    
# Prints the percentage of all the strategies 
if count % int(numPlayers) != 0:
    print("File contaminated, doesnt have correct number of lines")
if testOneWins != 0:
    print("The One: {:.02f}%".format(100*testOneWins/(count/int(numPlayers))))
if testTwoWins != 0:
    print("The Two: {:.02f}%".format(100*testTwoWins/(count/int(numPlayers))))
if testThreeWins != 0:
      print("The Three: {:.02f}%".format(100*testThreeWins/(count/int(numPlayers))))
if testFourWins != 0:
    print("The Four: {:.02f}%".format(100*testFourWins/(count/int(numPlayers))))
if testFiveWins != 0:
    print("The Five: {:.02f}%".format(100*testFiveWins/(count/int(numPlayers))))
if testSixWins != 0:
    print("The Six: {:.02f}%".format(100*testSixWins/(count/int(numPlayers))))
if randomWins != 0:
    print("Random: {:.02f}%".format(100*randomWins/(count/int(numPlayers))))
if folderWins != 0:
    print("Folder: {:.02f}%".format(100*folderWins/(count/int(numPlayers))))
