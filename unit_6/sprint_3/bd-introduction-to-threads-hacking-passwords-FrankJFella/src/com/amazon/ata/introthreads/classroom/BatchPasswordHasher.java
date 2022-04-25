package com.amazon.ata.introthreads.classroom;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class to hash a batch of passwords in a separate thread.
 */
// implement the Runnable interface so we can run on mutiple threads
// Note: final attribute is used on memebr data to make it immutable as required for concurrency
//       (concurrency means teh same process running on different threads at the same time)
public class BatchPasswordHasher implements Runnable{

    private final List<String> passwords;                // passwords to be hashed
    private final Map<String, String> passwordToHashes;  // Hold the hashed passwords
    private final String salt;                           // used in hashing the passwords

    // ctor receives the List-of-passwords-to-be-hashed and the salt
    public BatchPasswordHasher(List<String> passwords, String salt) {
        this.passwords = passwords;
        this.salt = salt;
        passwordToHashes = new HashMap<>();
    }

    // run() method is required by the Runnable interface
    // run() os the method called when a thread assigned to and object of this class is started
    public void run() {
        hashPasswords(); // call the method in this class to hash teh passwords
    }


    /**
     *  Hashes all of the passwords, and stores the hashes in the passwordToHashes Map.
     */
    public void hashPasswords() {
        try {
            for (String password : passwords) {
                final String hash = PasswordUtil.hash(password, salt);
                passwordToHashes.put(password, hash);
            }
            System.out.println(String.format("Completed hashing batch of %d passwords.", passwords.size()));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Returns a map where the key is a plain text password and the key is the hashed version of the plaintext password
     * and the class' salt value.
     *
     * @return passwordToHashes - a map of passwords to their hash value.
     */
    public Map<String, String> getPasswordToHashes() {
        return passwordToHashes;
    }
}
