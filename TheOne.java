package forsale;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/*      LIST OF TODOS
        
        Standard Deviation - Range of cards
        High S.D = large spread
        Low S.D = small spread.
        A small spread should have a smaller relative bid, a higher spread a larger bid
   
        Test that our highest limit works
        Play around with final bid over 14
*/

public class TheOne implements Strategy {

    public int bid(PlayerRecord p, AuctionState a) {
        int currentCash = p.getCash();
        int cardsLeft = a.getCardsInDeck().size();
        int numPlayers = a.getPlayers().size();
        int numRoundsLeft = 1 + (cardsLeft / numPlayers);
        double suggestedBid = currentCash / numRoundsLeft;
        int mean = 0;
        int min = 0;
        int max = 0;
        int variance = 0;
        List<Card> currentCards = a.getCardsInAuction();
        List<PlayerRecord> currentPlayers = a.getPlayersInAuction();
        
        //Gets the mean of the cards in the current round
        for (Card card : currentCards){
            if(min == 0){
                min = card.getQuality();
            }
            if(max == 0){
                max = card.getQuality();
            }
            if(min > card.getQuality()){
                min = card.getQuality();
            }
            if(max < card.getQuality()){
                max = card.getQuality();
            }
            mean += card.getQuality();
        }

        //Finds the mean value of the cards, and finds a high limit to bid
        mean = mean / currentCards.size();
        suggestedBid = suggestedBid + (suggestedBid * ((mean - 15) / 15));
        variance = max - min;
        //System.out.println("\n\nMin is:" + min + "  Max is:" + max + "  Variance is:" + variance + "\n\n");
        
        int finalBid = (int)suggestedBid;
        if((finalBid % 2) != 0){
            finalBid++;
        }
        
        int highest = 0;
        // Also counts our money, should not do that shit
        for(PlayerRecord player : currentPlayers){
            if(!player.getName().equals(p.getName())){
                highest = Math.max(highest, player.getCash());
            }
        }
        if(finalBid > highest){
            finalBid = highest;
        }

        if(variance < 6){
            variance = variance / 29;
            finalBid = finalBid * variance;
        }
        // Bids only in even incrementations
        for(int i = 0; i <= finalBid && i < 14; i = i + 2){
            // Bids if the suggested bid is higher than the current bid
            if(currentCash < i && (i - 1) == currentCash && i-1 > a.getCurrentBid()){
                return i-1;
            }else if(i > a.getCurrentBid()){
                return i;
            }
        }
        
        // If no previous clauses are met, pass the turn
        return -1;
    }

    public Card chooseCard(PlayerRecord p, SaleState s) {
        
        List<Card> shitStack = new ArrayList<Card>();
        List<Card> avgStack = new ArrayList<Card>();
        List<Card> sickStack = new ArrayList<Card>();
        List<Card> royaleStack = new ArrayList<Card>();
        List<Integer> currentCheques = s.getChequesAvailable(); 
        int min = 0;
        int max = 0;
        boolean has15 = false;

        for(Card card : p.getCards()){
            if(card.getQuality() == 30 || card.getQuality() == 29){
                royaleStack.add(card);
            }else if(card.getQuality() > 0 && card.getQuality() <= 11){
                shitStack.add(card);
            }else if(card.getQuality() > 11 && card.getQuality() <= 21){
                avgStack.add(card);
            }else if(card.getQuality() > 21 && card.getQuality() <= 30){
                sickStack.add(card);
            }
        }

        // System.out.println("\nCurrent Cheques for this round :\n");
        for(int i = 0; i < currentCheques.size(); i++){
            //System.out.print("[" + currentCheques.get(i) + "]  ");
            if(i == 0){
                min = currentCheques.get(i);
                max = currentCheques.get(i);
            }
            if(min > currentCheques.get(i)){
                min = currentCheques.get(i);
            }
            if(max < currentCheques.get(i)){
                max = currentCheques.get(i);
            }
            if(currentCheques.get(i) == 15){
                has15 = true;
            }
        }
    
        int range = max - min;    
        //System.out.println("\nMax is : " + max + "  Min is : " + min + "  Range is : " + range);

        Collections.sort(shitStack);
        Collections.sort(avgStack);
        Collections.sort(sickStack);
        Collections.sort(royaleStack);
        
        //System.out.println("\nSorted shitStack\n" + shitStack);
        //System.out.println("Sorted avgStack\n" + avgStack);
        //System.out.println("Sorted sickStack\n" + sickStack);
        //System.out.println("Sorted royaleStack\n" + royaleStack);
        
        int numPlayers = s.getPlayers().size();
        //Just a test at this point, nothing past here is final! Needs group discussion
        //change range defined by playercount

        // If a 0 exists

        if(has15 && !royaleStack.isEmpty()){
            return royaleStack.get(royaleStack.size()-1);
        }
        
        if(range <= numPlayers){
            if(!shitStack.isEmpty()){
                return shitStack.get(0);
            }else if(!avgStack.isEmpty()){
                return avgStack.get(0);
            }else if(!sickStack.isEmpty()){
                return sickStack.get(0);
            }else {
                return p.getCards().get(0);
            }
            
        }else if(range < 9){
            if(!avgStack.isEmpty()){
                return avgStack.get(0);
            }else if(!shitStack.isEmpty()){
                return shitStack.get(0);
            }else if(!sickStack.isEmpty()){
                return sickStack.get(0);
            }else {
                return p.getCards().get(0);
            }
            
        }else{
            if(!sickStack.isEmpty()){
                return sickStack.get(0);
            }else if(!avgStack.isEmpty()){
                return avgStack.get(0);
            }else if(!shitStack.isEmpty()){
                return shitStack.get(0);
            }else {
                return p.getCards().get(0);
            }
        }
        
                      
    }
    
    
}
    
