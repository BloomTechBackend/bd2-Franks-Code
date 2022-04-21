package com.amazon.ata.inmemorycaching.classroom.dao;

import com.amazon.ata.inmemorycaching.classroom.dao.models.GroupMembershipCacheKey;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

// This manage calls to the data store for membership validation using
// Google Guava cache manager

// Insert calls to Guava between the application and the data store
// If data is not found in the cache Guava will go to the data store
//    using the delegateDao (the original dao for accessing the data store)
//
// We need to mimic the behavior(s) of the original Dao (methods)
//
// In this example the delegateDao we are mimicking is:
//       boolean isUserInGroup(final String userId, final String groupId)
//
// We need to have a method called isUserInGroup that receives our cache key object
//            and returns a boolean.

public class GroupMembershipCachingDao {

    // Define a LoadingCache object for the cache a LoadingCache has a key and a value
    // caching-key is what you are looking for in the cache (contains userID and GroupId)
    // caching-value is what is returned from the cache (boolean)
    //                     caching-key          , caching-value
    private LoadingCache<GroupMembershipCacheKey, Boolean> theCache;

    // ctor will instantiate the cache and assign it to the reference
    // delegateDao is the "usual" Dao for accessing the data store that
    //        teh cache will use it if doesn't get a hit
    //  It will be dependency injected when the ctor is called
    @Inject
    public GroupMembershipCachingDao(final GroupMembershipDao delegateDao) {
        this.theCache = CacheBuilder.newBuilder()
                .maximumSize(20000)                   // max number of entries for the cache
                .expireAfterWrite(3, TimeUnit.HOURS)  // Evict entries this long after 1st written
                .build(CacheLoader.from(delegateDao::isUserInGroup));  // Go build the cache with delegate method
                // delegateDao must have a method called isUserInGroup() that receives a "cache-key object"
    }
    // method to search the cache for what we want
    // has the same method signature as the delegate DAO method (the we are replacing)
    public boolean isUserInGroup(final String userId, final String groupId) {
        // search cache using a get that might throw an unchecked exception and the search values
        //    in a "cache-key object" and return the result of search
        return theCache.getUnchecked(new GroupMembershipCacheKey(userId, groupId));
    }
}
