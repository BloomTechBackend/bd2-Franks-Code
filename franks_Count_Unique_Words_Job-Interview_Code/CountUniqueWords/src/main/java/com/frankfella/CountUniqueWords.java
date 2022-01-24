

/*********************************************************************************************************************************
 * Notes to Reviewer:
 *
 * The coding techniques and comments used are a style I would use if presenting or reviewing this problem with a student
 * If this were for a real production situation, I may have coded it slightly differently.
 *
 * The requirements include "Craft and use of Java ... and switch statements".
 * I did not see an opportunity to properly use a switch statement and didn't want to force the use of one.
 * I am hoping you will accept that I AM able to code a proper Java switch statement.
 *
 *********************************************************************************************************************************/

package com.frankfella;

import java.util.*;

public class CountUniqueWords {

    private static final int numWordsWanted = 50;  // Number of words to display;
                                                   // Set this to Integer.MAX_VALUE to get all words to display
                                                   // Since Integer.MAX_VALUE is slightly over 2 billion and the largest published
                                                   //    work is just over 1 billion words, it should work in all situations
    public static void main(String[] args) {
        System.out.println("\n-------- Start of Lambda Coding Assessment Problem Solution ----------\n");

        // Text provided for problem
        String text = "Preamble Whereas recognition of the inherent dignity and of the equal and inalienable rights of all members of the human family is the foundation of freedom, justice and peace in the world,  Whereas disregard and contempt for human rights have resulted in barbarous acts which have outraged the conscience of mankind, and the advent of a world in which human beings shall enjoy freedom of speech and belief and freedom from fear and want has been proclaimed as the highest aspiration of the common people,  Whereas it is essential, if man is not to be compelled to have recourse, as a last resort, to rebellion against tyranny and oppression, that human rights should be protected by the rule of law,  Whereas it is essential to promote the development of friendly relations between nations,  Whereas the peoples of the United Nations have in the Charter reaffirmed their faith in fundamental human rights, in the dignity and worth of the human person and in the equal rights of men and women and have determined to promote social progress and better standards of life in larger freedom,  Whereas Member States have pledged themselves to achieve, in co-operation with the United Nations, the promotion of universal respect for and observance of human rights and fundamental freedoms,  Whereas a common understanding of these rights and freedoms is of the greatest importance for the full realization of this pledge,  Now, Therefore THE GENERAL ASSEMBLY proclaims THIS UNIVERSAL DECLARATION OF HUMAN RIGHTS as a common standard of achievement for all peoples and all nations, to the end that every individual and every organ of society, keeping this Declaration constantly in mind, shall strive by teaching and education to promote respect for these rights and freedoms and by progressive measures, national and international, to secure their universal and effective recognition and observance, both among the peoples of Member States themselves and among the peoples of territories under their jurisdiction.   Article 1.   All human beings are born free and equal in dignity and rights. They are endowed with reason and conscience and should act towards one another in a spirit of brotherhood.  Article 2.   Everyone is entitled to all the rights and freedoms set forth in this Declaration, without distinction of any kind, such as race, colour, sex, language, religion, political or other opinion, national or social origin, property, birth or other status. Furthermore, no distinction shall be made on the basis of the political, jurisdictional or international status of the country or territory to which a person belongs, whether it be independent, trust, non-self-governing or under any other limitation of sovereignty.  Article 3.   Everyone has the right to life, liberty and security of person.  Article 4.   No one shall be held in slavery or servitude; slavery and the slave trade shall be prohibited in all their forms.  Article 5.   No one shall be subjected to torture or to cruel, inhuman or degrading treatment or punishment.  Article 6.   Everyone has the right to recognition everywhere as a person before the law.  Article 7.   All are equal before the law and are entitled without any discrimination to equal protection of the law. All are entitled to equal protection against any discrimination in violation of this Declaration and against any incitement to such discrimination.  Article 8.   Everyone has the right to an effective remedy by the competent national tribunals for acts violating the fundamental rights granted him by the constitution or by law.  Article 9.   No one shall be subjected to arbitrary arrest, detention or exile.  Article 10.   Everyone is entitled in full equality to a fair and public hearing by an independent and impartial tribunal, in the determination of his rights and obligations and of any criminal charge against him.  Article 11.   (1) Everyone charged with a penal offence has the right to be presumed innocent until proved guilty according to law in a public trial at which he has had all the guarantees necessary for his defence. (2) No one shall be held guilty of any penal offence on account of any act or omission which did not constitute a penal offence, under national or international law, at the time when it was committed. Nor shall a heavier penalty be imposed than the one that was applicable at the time the penal offence was committed.  Article 12.   No one shall be subjected to arbitrary interference with his privacy, family, home or correspondence, nor to attacks upon his honour and reputation. Everyone has the right to the protection of the law against such interference or attacks.  Article 13.   (1) Everyone has the right to freedom of movement and residence within the borders of each state. (2) Everyone has the right to leave any country, including his own, and to return to his country.  Article 14.   (1) Everyone has the right to seek and to enjoy in other countries asylum from persecution. (2) This right may not be invoked in the case of prosecutions genuinely arising from non-political crimes or from acts contrary to the purposes and principles of the United Nations.  Article 15.   (1) Everyone has the right to a nationality. (2) No one shall be arbitrarily deprived of his nationality nor denied the right to change his nationality.  Article 16.   (1) Men and women of full age, without any limitation due to race, nationality or religion, have the right to marry and to found a family. They are entitled to equal rights as to marriage, during marriage and at its dissolution. (2) Marriage shall be entered into only with the free and full consent of the intending spouses. (3) The family is the natural and fundamental group unit of society and is entitled to protection by society and the State.  Article 17.   (1) Everyone has the right to own property alone as well as in association with others. (2) No one shall be arbitrarily deprived of his property.  Article 18.   Everyone has the right to freedom of thought, conscience and religion; this right includes freedom to change his religion or belief, and freedom, either alone or in community with others and in public or private, to manifest his religion or belief in teaching, practice, worship and observance.  Article 19.   Everyone has the right to freedom of opinion and expression; this right includes freedom to hold opinions without interference and to seek, receive and impart information and ideas through any media and regardless of frontiers.  Article 20.   (1) Everyone has the right to freedom of peaceful assembly and association. (2) No one may be compelled to belong to an association.  Article 21.   (1) Everyone has the right to take part in the government of his country, directly or through freely chosen representatives. (2) Everyone has the right of equal access to public service in his country. (3) The will of the people shall be the basis of the authority of government; this will shall be expressed in periodic and genuine elections which shall be by universal and equal suffrage and shall be held by secret vote or by equivalent free voting procedures.  Article 22.   Everyone, as a member of society, has the right to social security and is entitled to realization, through national effort and international co-operation and in accordance with the organization and resources of each State, of the economic, social and cultural rights indispensable for his dignity and the free development of his personality.  Article 23.   (1) Everyone has the right to work, to free choice of employment, to just and favourable conditions of work and to protection against unemployment. (2) Everyone, without any discrimination, has the right to equal pay for equal work. (3) Everyone who works has the right to just and favourable remuneration ensuring for himself and his family an existence worthy of human dignity, and supplemented, if necessary, by other means of social protection. (4) Everyone has the right to form and to join trade unions for the protection of his interests.  Article 24.   Everyone has the right to rest and leisure, including reasonable limitation of working hours and periodic holidays with pay.  Article 25.   (1) Everyone has the right to a standard of living adequate for the health and well-being of himself and of his family, including food, clothing, housing and medical care and necessary social services, and the right to security in the event of unemployment, sickness, disability, widowhood, old age or other lack of livelihood in circumstances beyond his control. (2) Motherhood and childhood are entitled to special care and assistance. All children, whether born in or out of wedlock, shall enjoy the same social protection.  Article 26.   (1) Everyone has the right to education. Education shall be free, at least in the elementary and fundamental stages. Elementary education shall be compulsory. Technical and professional education shall be made generally available and higher education shall be equally accessible to all on the basis of merit. (2) Education shall be directed to the full development of the human personality and to the strengthening of respect for human rights and fundamental freedoms. It shall promote understanding, tolerance and friendship among all nations, racial or religious groups, and shall further the activities of the United Nations for the maintenance of peace. (3) Parents have a prior right to choose the kind of education that shall be given to their children.  Article 27.   (1) Everyone has the right freely to participate in the cultural life of the community, to enjoy the arts and to share in scientific advancement and its benefits. (2) Everyone has the right to the protection of the moral and material interests resulting from any scientific, literary or artistic production of which he is the author.  Article 28.   Everyone is entitled to a social and international order in which the rights and freedoms set forth in this Declaration can be fully realized.  Article 29.   (1) Everyone has duties to the community in which alone the free and full development of his personality is possible. (2) In the exercise of his rights and freedoms, everyone shall be subject only to such limitations as are determined by law solely for the purpose of securing due recognition and respect for the rights and freedoms of others and of meeting the just requirements of morality, public order and the general welfare in a democratic society. (3) These rights and freedoms may in no case be exercised contrary to the purposes and principles of the United Nations.  Article 30.   Nothing in this Declaration may be interpreted as implying for any State, group or person any right to engage in any activity or to perform any act aimed at the destruction of any of the rights and freedoms set forth herein.";

        CountAndDisplayUniqueWords(text);     // Test using text provided with ore than 50 words
        CountAndDisplayUniqueWords("");       // Test using empty string
        CountAndDisplayUniqueWords("How much code could a coder code if a coder could code code?");  // Test using short text

        System.out.println("\n-------- End of Lambda Coding Assessment Problem Solution ---------");
    }

    /**
     * Count and display words in the text String given
     * 
     * @param textString
     */
    public static void CountAndDisplayUniqueWords(String textString) {

        System.out.println("_".repeat(70));                           // Display visual result separator

        if (textString.length() == 0) {                               // if empty String given
            System.out.println("----- no words in textString -----"); // display a message to that effect
            return;                                                   // and exit
        }
        System.out.println("The textString: " + textString);              // display text string given

        HashMap<String, Integer> wordCountMap = new HashMap<String, Integer>();  // Hold unique words and occurrence count

        String[] words = textString.split("(\\s+)|([\\p{P}\\p{S}])"); // Divide the text string given at spaces and punctuation marks

        System.out.println("contains " + words.length + " words"); // Display total number of words in the text string given

        for (String aWord : words) {                              // Loop through words in text string given
            String currentWord = aWord.toUpperCase().trim();      //      Convert word to uppercase, remove any leading or trailing spaces

            if (currentWord.isEmpty()) {                          //      If current word is an empty string...
                continue;                                         //      skip it
            }
            if (wordCountMap.containsKey(currentWord)) {          //      if current word is already in the Map...
                int currentCount = wordCountMap.get(currentWord); //         retrieve the current count
                currentCount++;                                   //         add 1 to the current count
                wordCountMap.put(currentWord, currentCount);      //         store the current count back in the Map
            } else {                                              //      if current word is not in the Map...
                wordCountMap.put(currentWord, 1);                 //         add the current word to the Map with a count of 1
            }
        }
        Map<String, Integer> sortedMapByValue = sortByValue(wordCountMap);  // Sort the word count map by count of words

        Set<String> theKeys = sortByValue(wordCountMap).keySet();           // Retrieve the unique words (keys) from the sorted map

        Map<String, Integer> topWords = new TreeMap<String, Integer>();     // Hold top number of words in alphabetical order

        Iterator<String> currentKey = theKeys.iterator();                   // Iterator to step through the set of unique words from the sorted Map

        System.out.println("\nThe top"                                              // display visual separator and message
                + (topWords.size() >= numWordsWanted ? (" " + numWordsWanted) : "") // do not include number of words if less than what wa wanteed
                + " occurring words sorted by occurrence count: \n" );              // rest of message

        for (int i=0; i < numWordsWanted && currentKey.hasNext(); i++){     // Loop for number of words wanted or no more words
            String currentWord = currentKey.next();                         //      Retrieve the next unique word
            // Display message containing word an occurrence count
            System.out.println("Common word: <" + currentWord + "> appears " + sortedMapByValue.get(currentWord) + " times");
            topWords.put(currentWord, sortedMapByValue.get(currentWord));   // Add word and occurrence count to topWords
          }

          System.out.println("-".repeat(70) + "\nThe top"                                      // display visual separator and message
                           + (topWords.size() >= numWordsWanted ? (" " + numWordsWanted) : "") // do not include number of words if less than what wa wanteed
                           + " occurring words sorted alphabetically: \n" );                   // rest of message

        for (Map.Entry<String, Integer> anEntry : topWords.entrySet()) {  // Loop through the Map containing words in alphabetical order
            System.out.println("Common Word <" + anEntry.getKey() + "> occurs " + anEntry.getValue() + " times");  // Display the word and count
        }
    }

    /**********************************************************************************************************************************************
     *  Helper methods
     **********************************************************************************************************************************************/

    /**
     * Sort hashmap by descending values
     *
     * @param aHashMap
     * @return result - the given HashMap sorted by descending values
     */
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> aHashMap) {

        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(aHashMap.entrySet());

        // Sort the list by descending values using Collections class sort method
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                return (entry2.getValue()).compareTo(entry1.getValue());
            }
        });

        // Add the sorted elements in the list to a LinkedHashMap (Map entries stored in entry sequence)
        HashMap<String, Integer> result = new LinkedHashMap<String, Integer>();  // Hold return object
        for (Map.Entry<String, Integer> anEntry : list) {                        // Loop through sorted list
            result.put(anEntry.getKey(), anEntry.getValue());                    // Add current entry from list to result Map
        }
        // return the LinkedHashMap with entries in sorted sequence
        return result;
    }
}




