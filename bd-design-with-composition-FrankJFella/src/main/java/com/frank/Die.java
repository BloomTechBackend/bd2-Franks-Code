package com.frank;

import java.util.*;

public class Die {
// contains a set of Side objects
// What type set do we want to use: simple array, ArrayList, Set, Map, LinkedList

// List<> chosen so we can access an individual side by number
private List<Side> sides;  // define a reference to the list of sides (read-only - no setters)

// ctor for a Die needs to know: number-of-sides, side-color
public Die(int numberOfSides, String sideColor) {
        sides = new ArrayList<>();  // instantiate the Set object to hold the  sides
        // we need to create a Side based the numberOfSides and color the user wants
        // we assume the side values will start at 1 and go the value in numberSides
        for(int i = 1; i <= numberOfSides; i++) {
             sides.add(new Side(i,sideColor));
        }
}
        public List<Side> getSides() {
                return sides;
        }
        public Side getASide(int sideNumber) {
        return sides.get(sideNumber);
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Die die = (Die) o;
                return sides.equals(die.sides);
        }

        @Override
        public int hashCode() {
                return Objects.hash(sides);
        }

        @Override
        public String toString() {
                return "Die{" +
                        "sides=" + sides +
                        '}';
        }
}
