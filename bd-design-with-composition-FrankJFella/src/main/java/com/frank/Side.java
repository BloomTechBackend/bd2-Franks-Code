package com.frank;

import java.util.Objects;

// Side represents a side of a die
public class Side {
        private int    sideValue;   // value for the Side
        private String sideColor;   // color for the Side

        // a default ctor in case Java needs one
        // since we haven't determined a value and color for a default side
        //       the default ctor does nothing
        public Side() {}

        // 2-arg ctor for a value and a color
        public Side(int sideValue, String sideColor) {
                this.sideValue = sideValue;
                this.sideColor = sideColor;
        }
//************************************************************
// Getters and Setters
//************************************************************
        public int getSideValue() {
                return sideValue;
        }
        public void setSideValue(int sideValue) {
                this.sideValue = sideValue;
        }
        public String getSideColor() {
                return sideColor;
        }
        public void setSideColor(String sideColor) {
                this.sideColor = sideColor;
        }
//************************************************************
// Object class overrides
//************************************************************
        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Side side = (Side) o;
                return getSideValue() == side.getSideValue() && getSideColor().equals(side.getSideColor());
        }
        @Override
        public int hashCode() {
                return Objects.hash(getSideValue(), getSideColor());
        }
        @Override
        public String toString() {
                return "Side{" +
                        "sideValue=" + sideValue +
                        ", sideColor='" + sideColor + '\'' +
                        '}';
        }
}
