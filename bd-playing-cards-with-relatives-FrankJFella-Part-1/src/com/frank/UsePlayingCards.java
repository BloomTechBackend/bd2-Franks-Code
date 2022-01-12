package com.frank;

import java.util.ArrayList;

public class UsePlayingCards {
// This is our application program which will instantiate object and use thier methods to manipulate them
// We know this is the application program because it has the main() method
	public static void main(String[] args) {


		// Instantiate an AmericanPlayingCard using the 2-arg ctor with a value 1 and "HEARTS"
		//    1. the 2-arg (int, String) ctor for AmericanPlaying card is run  - subclass ctor is run
		//    2. the AmericanPlayingCard ctor runs the 3-arg PlayingCard ctor  - superclass ctor
		//    3. Once the super class ctor completes the remaining code in the subclass ctro is run

		AmericanPlayingCard aUSACard = new AmericanPlayingCard(1, "HEARTS");
		System.out.println("aUSACard is :");
		aUSACard.showCard();  // runs the AmericanPlayingCard showCard()

		AmericanPlayingCard aUSACard2 = new AmericanPlayingCard(14, "SPADES");
		System.out.println("aUSACard2 is :");
		aUSACard2.showCard();

		AmericanPlayingCard aUSACard3 = new AmericanPlayingCard(-1, "SPADES");
		System.out.println("aUSACard3 is :");
		aUSACard3.showCard();

		AmericanPlayingCard aUSACard4 = new AmericanPlayingCard(13, "Dumbledore");
		System.out.println("aUSACard4 is :");
		aUSACard4.showCard();

		// Instantiate an ItalianPlayCard with the value 5, suit CUPS and color blue
		ItalianPlayingCard anItalianCard = new ItalianPlayingCard(5,"CUPS", "Blue");
		// Java will look for a method in the class of the object used to invoke it (the object before the .)
		//           if the class of the object does not have teh method, we look to it's super class for the method
		anItalianCard.showCard();   // Have the ItalianPlayingCard show it's values using the ItalianPlayingCard showCard() methods


	}  // End of main()
	// This must be static because what calls it is static
	static void displayCard(PlayingCard aCard) {  // a function receives a super class object
		aCard.showCard();  // use the super class object to run a method - Polymorphism will run the correct method
	}


}  // End of class that holds main()
