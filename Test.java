/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forsale;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
/**
 *
 * @author MichaelAlbert
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // A null strategy - never bid, always play your top card.
        Strategy s = new Strategy() {

            @Override
            public int bid(PlayerRecord p, AuctionState a) {
                return -1;
            }

            @Override
            public Card chooseCard(PlayerRecord p, SaleState s) {
                return p.getCards().get(0);
            }
            
        };
        
        // A random strategy - make a random bid up to your amount remaining,
        // choose a rand card to sell.
        Strategy r = new Strategy() {
                
            @Override
            public int bid(PlayerRecord p, AuctionState a) {
                return (int) (1 + (Math.random()*p.getCash()));
            }

            @Override
            public Card chooseCard(PlayerRecord p, SaleState s) {
                return p.getCards().get((int) (Math.random()*p.getCards().size()));
            }
            
        };
        
        List<Player> players = new ArrayList<>();
        //for(int i = 0; i < 1; i++) {
        //    players.add(new Player("R" + ((char) ('A' + i)), r));
        //    players.add(new Player("N" + ((char) ('A' + i)), s));
        //}
        players.add(new Player("TheOne", new TheOne()));
        players.add(new Player("TheTwo", new TheTwo()));
        players.add(new Player("TheThree", new TheThree()));
        players.add(new Player("TheFour", new TheFour()));
        players.add(new Player("TheFive", new TheFive()));
        Collections.shuffle(players);
        GameManager g = new GameManager(players);
        g.run();
    }

}
