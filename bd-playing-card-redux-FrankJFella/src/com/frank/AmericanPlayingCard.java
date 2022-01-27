package com.frank;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class AmericanPlayingCard extends PlayingCard{
	// static means there only one shared by all
	// final means cannot be changed once it has been assigned a value
	// static final means only one occurrence, shared by all, that can't be changed
	private static final int    DEFAULTCARDVALUE = 0;
	private static final String DEFAULTCOLOR     = "BLACK";
	private static final String DEFAULTSUIT      = "Joker";
	private static final int    MAXVALUE         = 13;
	private static final int    MINVALUE         = 0;

	// Maps are uninitialized - due to there being no easy way to initialize at instantiation
	//                          so a method must be used to initialize the Maps
	//                          we don't initialize static data in a ctor because static may exist
	//                             even if no objects are instantiated - ctor are only called
	//                             objects are instantiated.
	//                          if we put static data initialization in a ctor, the static may not
	//                             be initialized when needed by the ctor - careful coding technique is required
	//                          Since static function may access static data at any time, if no object of the
	//                             class has been instantiated, ctor hasn't run, it will be uninitialized
	//                             if initialization is done in a ctor
	private static Map<String,  String> suitMap  = new HashMap();  // Associate a suit and a color
	private static Map<Integer, String> valueMap = new TreeMap();  // Associate a value and word describing it

	// Since the suitMap and valueMap are static - only static methods may change them
	// static data may exist without any objects of the class being instantiated
	// if the initializeMaps() method was in constructors, it may not run - our Maps would not get initialize
	// we need to tell Java to run initializeMap() when the applications starts which is when static data is created
	// by putting the call to call to initializeMaps() outside any function/method - Java knows to run it at the
	//            start of the app

	// static is required because it is calling the static method initializeMaps()
	static {  // Note: no function/method definitions - anonymous function
		initializeMaps();  // call the function to initialize the suitMap and valueMap
	}
	// subclass ctor must call the super class ctro before it does anything else
	public AmericanPlayingCard() 
	{
		super(DEFAULTCARDVALUE, DEFAULTSUIT, DEFAULTCOLOR);
	} 

	public AmericanPlayingCard(int value, String suit) {
		// use the conditional operator (?) to provide conditional parameters to a method
		// the way the conditional operator works    condition ? value-if-true : value-if-false
		//
		// suitMap.containsKey(suit) ? suit : DEFAULTSUIT
		//
		// if the suitMap contains a key that equals the suit we were passed, use it, otherwise use DEFAULTSUIT
		//
		//       color       != null ?      color        : defaultcolor
		// suitMap.get(suit) != null ? suitMap.get(suit) : DEFAULTCOLOR)
		//
		// if the value returned from the get for suitMap using the suit passed is not null, use the color from teh suitMap
		//                                            otherwise use the DEFAULTCOLOR
		//
		// if no color is returned from the suitMap, use the DEFAULTCOLOR
		//

		// call the super class constructor with the value passed and the suit and color we determined
		super(value,                                                        // Call super ctor with value passed
			  suitMap.containsKey(suit) ? suit : DEFAULTSUIT,               // If valid suit passed, use it otherwise use DEFAULTSUIT
		      suitMap.get(suit) != null ? suitMap.get(suit) : DEFAULTCOLOR);// If valid suit passed, use color for suit otherwise use DEFAULTCOLOR  

		// Since we can't check for a valid value until the super ctoo is called
		// we have verify the value, and change it if it's invalid, after the super ctor
		if (value > MAXVALUE) {  // if the value we received exceeds the max, make it the max
			setValue(MAXVALUE);
		}
		if (value < MINVALUE) {  // if the value we received below the min, make it the min
			setValue(MINVALUE);
		}
	}
// this must be a static method because it is changing static data (suitMap and valueMap)
	static private void initializeMaps() {  // initialize the suitMap and valueMaps
		//            key      ,  value
		suitMap.put("SPADES"   , "BLACK");
		suitMap.put("CLUBS"    , "BLACK");
		suitMap.put("DIAMONDS" , "RED");
		suitMap.put("HEARTS"   , "RED");
		suitMap.put(DEFAULTSUIT, DEFAULTCOLOR);  // the DEFAULTSUIT is associated with the DEFAULTCOLOR

		//        key ,value
		valueMap.put(0,"Joker");
		valueMap.put(1,"Ace");
		valueMap.put(2,"Two");
		valueMap.put(3,"Three");
		valueMap.put(4,"Four");
		valueMap.put(5,"Five");
		valueMap.put(6,"Six");
		valueMap.put(7,"Seven");
		valueMap.put(8,"Eight");
		valueMap.put(9,"Nine");
		valueMap.put(10,"Ten");
		valueMap.put(11,"Jack");
		valueMap.put(12,"Queen");
		valueMap.put(13,"King");
	}
	// It is common for a subclass to call a super class method it overrides - in  this toString()
	// because the sub class doesn't know how to handle the super class data
	// the sub class is only responsible for any data it adds to the super class
	// so all the sub class has to do is decide how to handle it's new data
	// and use the super class method to handle super class data
	//
	@Override
	public String toString() {
		return "AmericanPlayingCard: " 
	          +"Value: "  + valueMap.get(getValue())  // use the valueMap to get the value name
	          +" - Color: " + suitMap.get(getSuit())  // use the suitMap to get the color for the suit
			  +" - Suit: "  + getSuit()               // use the super class method to get the suit
				                                      // super. optional because the sub class does not a method called getSuit()
			  +" - super.toString()=" + super.toString() + "\n";  // call the super class toString()
		                                                          // super. is required because we have a method with the same name
		                                                          // if omitted super. we would be calling ourselves
	}

	// Note: Since this class doesn't add any new data memebers
	//       the super class .eauals() and .hashCode() methods
	//           handle both sets of processing well


	// override the super class abstract showCard method
	public void showCard() {
		System.out.println(this.toString());
	}

	// generate a random PlayingCard (super class for all our Playing cards in this application)
	// because this method is static we can use the class name to invoke or an object of the class
	public static PlayingCard pickACard() {

		Random getRandomValue = new Random();  // Instantiate a Random object to get random number

		// Convert the suitMap to an array so we can pick a suit with a random index
		String[] suits =  suitMap.keySet().toArray(new String[suitMap.size()]);

		// Random.nextInt(upperbound) will generate a random number from 0 to upperbound - 1
		// ie. Random.nextInt(10)     will generate a random number between 0 and 9
		//     Random.nextInt(14)     will generate a random number between 0 and 13
		//     Random.nextInt(13) + 1 will generate a random number between 1 and 13
		//
		// To generate a random between a minimum and maximum value: .nextInt(max-min) + 1
		//
		int newCardValue = getRandomValue.nextInt(13) + 1;

//      Display suit to see if code actual generated a random suit
//		System.out.println("Suit picked: " + aRandomObject.nextInt((((suits.length-1)+1)-1)));

		// Pick a suit form the array created above using a random index
		//                        RandomClassObj.nextInt( (max-min)+1) - min
		String newCardSuit = suits[getRandomValue.nextInt(((suits.length-1)+1))]; //  (since smallest index is 0, no need to +1 and - min
		return new AmericanPlayingCard(newCardValue, newCardSuit );
	}



}
