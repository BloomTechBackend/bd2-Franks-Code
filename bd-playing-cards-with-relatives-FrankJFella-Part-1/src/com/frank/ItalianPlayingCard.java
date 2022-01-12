package com.frank;

// ItalianPlayingCard is a subclass of PlayingCard
// It will have all the data and methods of PlayingCard
//    plus whatever we add to make it an ItalianPlayingCard
public class ItalianPlayingCard extends PlayingCard {
        // define a ctor for an ItalianPlayingCard - accept a value, suit, color
        public ItalianPlayingCard(int theValue, String theSuit, String theColor) {
              super(theValue, theSuit, theColor); // Call super class ctor with arguments it needs
        }
        // Override the showCard in the super class because we want different behavior from the method in this class
        public void showCard() {
                System.out.println("-----  This is the ItalianPlayCard Class showCard() -----");
                System.out.println(this.toString());
        }
}
