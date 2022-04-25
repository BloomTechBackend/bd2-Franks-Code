package com.amazon.ata.introthreads.classroom;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;   // access to Google ConcurrentMap class
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class to pre-compute hashes for all common passwords to speed up cracking the hacked database.
 *
 * Passwords are downloaded from https://github.com/danielmiessler/SecLists/tree/master/Passwords/Common-Credentials
 */
public class PasswordHasher {
    // should create the file in your workspace directory
    private static final String PASSWORDS_AND_HASHES_FILE = "./passwordsAndHashesOutput.csv";
    private static final String DISCOVERED_SALT = "salt";  // Normally the salt is a big random String or integer

    /**
     * Generates hashes for all of the given passwords.
     *
     * @param passwords List of passwords to hash
     * @return map of password to hash
     * @throws InterruptedException
     */
    public static Map<String, String> generateAllHashes(List<String> passwords) throws InterruptedException {
        // Prior to adding threading, this Map was populated by one call to BatchPasswordHasher
        // With threading causing multiple BatchPasswordHashed to run concurrently,
        //      we need to combine results from each thread into this Map
        // Added final to the Map as required by concurrency
        //    Map<String, String> passwordToHashes = Maps.newConcurrentMap();
        final Map<String, String> passwordToHashes = Maps.newConcurrentMap(); // Use of Google ConcurrentMap class

        // Split the Lsit passwords into four sublists
        // Each sublist is a List<String> holding a set of passwords to be hashed
        //                                                original-list, #-entries-in-sublist
        List<List<String>> passwordSubLists = Lists.partition(passwords, passwords.size() / 4);

        // Define a List<Thread> to hold each thread we create (so we can wait for each to complete)
        List<Thread> theThreads = new ArrayList<>();

        // Define a List<BatchPasswordHasher> so we can copy each one's results to our result Map
        List<BatchPasswordHasher> theHashers = new ArrayList<>();

        // Define the process to hash the passwords sending it the list of passwords and the salt
        for(int i=0; i < passwordSubLists.size(); i++){
            BatchPasswordHasher batchHasher = new BatchPasswordHasher(passwordSubLists.get(i), DISCOVERED_SALT);
            theHashers.add(batchHasher);     // Remember the BatchPasswordHasher so we can get it's results
            // batchHasher.hashPasswords();  // replaced by define Thread for each batchHasher
            Thread aThread = new Thread(batchHasher);  // Define a thread for the current batchHasher
            theThreads.add(aThread);                   // Remember the Thread we just started
            aThread.start();                           // Start the thread so it start processing
        }

        // Added to Wait for all threads to complete before continuing
        waitForThreadsToComplete(theThreads);

        // passwordToHashes.putAll(batchHasher.getPasswordToHashes());  // replaced by for loop

        // Load the Map of hashed passwords from each batchHasher that was run
        for(BatchPasswordHasher aHasher: theHashers) {  // Go through the List of BatchPasswordHashed and copy result to our result Map
            passwordToHashes.putAll(aHasher.getPasswordToHashes());
        }

        return passwordToHashes;
    }

    /**
     * Makes the thread calling this method wait until passed in threads are done executing before proceeding.
     *
     * @param threads to wait on
     * @throws InterruptedException
     */
    public static void waitForThreadsToComplete(List<Thread> threads) throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();  // Wait for the thread to complete before continuing
        }
    }

    /**
     * Writes pairs of password and its hash to a file.
     */
    static void writePasswordsAndHashes(Map<String, String> passwordToHashes) {
        File file = new File(PASSWORDS_AND_HASHES_FILE);
        try (
            BufferedWriter writer = Files.newBufferedWriter(file.toPath());
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)
        ) {
            for (Map.Entry<String, String> passwordToHash : passwordToHashes.entrySet()) {
                final String password = passwordToHash.getKey();
                final String hash = passwordToHash.getValue();

                csvPrinter.printRecord(password, hash);
            }
            System.out.println("Wrote output of batch hashing to " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
