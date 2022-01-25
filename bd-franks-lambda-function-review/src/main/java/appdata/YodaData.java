package appdata;

import java.util.Objects;

public class YodaData {
        int          aNum;
        String      planet;
        RequestData pooja;


        public YodaData() {} // default ctor because the conversion to/from json needs it

        public YodaData(int aNum, String planet, RequestData requestData) {
                this.aNum = aNum;
                this.planet = planet;
                this.pooja = requestData;
        }

        public int getaNum() {
                return aNum;
        }

        public RequestData getPooja() {
                return pooja;
        }

        public void setPooja(RequestData pooja) {
                this.pooja = pooja;
        }

        public void setaNum(int aNum) {
                this.aNum = aNum;
        }

        public String getPlanet() {
                return planet;
        }

        public void setPlanet(String planet) {
                this.planet = planet;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                YodaData yodaData = (YodaData) o;
                return getaNum() == yodaData.getaNum() && Objects.equals(getPlanet(), yodaData.getPlanet());
        }

        @Override
        public int hashCode() {
                return Objects.hash(getaNum(), getPlanet());
        }

        @Override
        public String toString() {
                return "YodaData{" +
                        "aNum=" + aNum +
                        ", planet='" + planet + '\'' +
                        '}';
        }
}
