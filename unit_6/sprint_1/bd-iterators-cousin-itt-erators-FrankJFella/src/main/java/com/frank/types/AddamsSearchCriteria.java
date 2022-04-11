package com.frank.types;
/******************************************************************************************************
 * Represent criteria for search for an entry in a collection
 *
 * Since multiple values are required for a search, we made a class to hold values
 *
 * This being a "Helper class" it doesn't need all the methods of a POJO (equals, hashCode, toString)
 *****************************************************************************************************/
public class AddamsSearchCriteria {
        private String  searchValue;
        private boolean isCaseSensitiveSearch;

        public AddamsSearchCriteria(String searchValue, boolean isCaseSensitiveSearch) {
                this.searchValue = searchValue;
                this.isCaseSensitiveSearch = isCaseSensitiveSearch;
        }

        public String getSearchValue() {
                return searchValue;
        }

        public boolean isCaseSensitiveSearch() {
                return isCaseSensitiveSearch;
        }
}
