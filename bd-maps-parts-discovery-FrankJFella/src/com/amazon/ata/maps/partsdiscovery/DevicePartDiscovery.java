package com.amazon.ata.maps.partsdiscovery;

import java.util.*;

/**
 * Helps expose key words from new editions of part catalogs.
 * Look at the words in catalog files and determine some characteristics
 */
public class DevicePartDiscovery {

    // --- Part A ---
    /**
     * Calculate how often each word appears in a Catalog.
     * @param catalog The catalog to calculate word frequencies for.
     * @return A Map of words that appear in the catalog to the number of times they appear.
     */
    //  return a Map and receiving a PartCatalog object containing the list of words in the catalog
    public Map<String, Integer> calculateWordCounts(PartCatalog catalog) {
        // PARTICIPANTS: Implement calculateWordCounts()

        // instantiate the return object/variable
        Map<String, Integer> returnedMap = new HashMap<>();

        // (optional) Copy the list of words from the PartCatalog object we were given
        // List<String> listOfWords = catalog.getCatalogWords();

        // go through the list words
     // for(String aWord : listOfWords) - if we copy list of words from PartCatalog
        for(String aWord : catalog.getCatalogWords()) {
            if(returnedMap.containsKey(aWord)) { // check to see if the current word is already in the Map we are returning
                int currentCount = returnedMap.get(aWord); // retrieve the current count from the Map
                currentCount++;                            // increment the current count
                returnedMap.put(aWord,currentCount);       // put the incremented count back in the Map
            }
            else {   // if it's not, add the word to the Map with a count of 1
            //    int wordCount = 1;
            //    returnedMap.put(aWord, wordCount);
               returnedMap.put(aWord,1);
            }
        }
        // return the return object/variable
        return returnedMap;
    }

    // --- Part B ---
    /**
     * Removes a word from the provided word count map.
     * @param word the word to be removed
     * @param wordCounts the map to remove the word from
     */
    public void removeWord(String word, Map<String, Integer> wordCounts) {
        // PARTICIPANTS: Implement removeWord()
        wordCounts.remove(word);  // Use the built-in Map remove() method
        return;  // optional since method returns void
    }

    // --- Part C ---
    /**
     * Find the word that appears most frequently based on the word counts from a catalog.
     * @param wordCounts an association between a word and the total number of times it appeared in a catalog
     * @return The word that appears most frequently in the catalog to the number of times they appear.
     */
    public String getMostFrequentWord(Map<String, Integer> wordCounts) {
        // PARTICIPANTS: Implement getMostFrequentWord()
        // The value for each value in the Map provided contains the work count
        // There is no version of a Map that keeps the entries in value sequence

        // instantiate an object for the return value
        String mostFrequentWord = "";  // hold the most frequently occurring word

        int highestWordCountSoFar = 0; // define a variable to hold the highest word count so far

        // iterate through the Map provided remembering the word with the highest word count
        // Map.Entry represents a single entry in a Map
        //    .entrySet() - Return the entries in a Map as a set of entries
        // convert the Map to a set of individual entries,
        //         use a for loop to go through one entry at a time
        //    one-entry-in-map                 : all-entries-in-Map
        for(Map.Entry<String, Integer> anEntry : wordCounts.entrySet()) {
            if (anEntry.getValue() > highestWordCountSoFar) {//  if current count is higher than the highest count so far
                mostFrequentWord      = anEntry.getKey();    // remember the word
                highestWordCountSoFar = anEntry.getValue();  // set the highest count so far to the current word count
      }
        }
        // return the object containing the return value
        return mostFrequentWord;
    }

    // --- Part D ---
    /**
     * Calculates the TF-IDF score for each word in a catalog. The TF-IDF score for a word
     * is equal to the count * idf score. You can assume there will be an idfScore for each word
     * in wordCounts.
     * @param wordCounts - associates a count for each word from a catalog
     * @param idfScores - associates an IDF score for each word in the catalog
     * @return a map associating each word with its TF-IDF score.
     */
    //  return a map of TF-IDF values receiving       word count map         , Map with idf scores for each word
    public Map<String, Double> getTfIdfScores(Map<String, Integer> wordCounts, Map<String, Double> idfScores) {
        // PARTICIPANTS: Implement getTfIdfScores()
        // Instantiate object to e returned
        Map<String, Double> returnedObject = new TreeMap<>();

        // iterate through the word count map
        //    multiply the word count for the word by the idf score for the word

        for(Map.Entry<String, Integer> aWord : wordCounts.entrySet()) {
            //              the current word , current word count  * idfScore for current word
            //                                                          idfScore Map
            //                                                          key  value
            //                                                          cat  10
            //                                                          dog  20
            //                                                       value for entry with key that matches current word
            returnedObject.put(aWord.getKey(), aWord.getValue()    * idfScores.get(aWord.getKey()));
        }
        return returnedObject;   // return the object defined to be returned
    }

    // --- Extension 1 ---
    /**
     * Gets the 10 highest (TF-IDF) scored words for a catalog.
     *
     * @param tfIdfScores - associates a TF-IDF score for each word in a catalog
     * @return a list of the 10 highest scored words for a catalog.
     */
    public List<String> getBestScoredWords(Map<String, Double> tfIdfScores) {
        // PARTICIPANTS: Implement getBestScoredWords()
        return Collections.emptyList();
    }

    // --- Extension 2 ---
    /**
     * Calculates the IDF score for each word in a set of catalogs. The IDF score for a word
     * is equal to the inverse of the total number of times appears in all catalogs.
     * Assume df is the sum of the counts of a single word across all catalogs, then idf = 1.0/df.
     *
     * @param catalogWordCounts - a list of maps that associate a count for each word for each catalog
     * @return a map associating each word with its IDF score.
     */
    public Map<String, Double> calculateIdfScores(List<Map<String,Integer>> catalogWordCounts) {
        // PARTICIPANTS: Implement getIdfScores()
        return Collections.emptyMap();
    }

}
