package com.amazon.ata.inmemorycaching.classroom.dao.models;

import java.util.Objects;

// Used in the implementation of caching

// Due to our search requires for matching userdId and groupId in the data store.
//     a class is used to hold the search values

// The cache key is unique id for an in a cache
// A typical cache key is POJO with ctor, getters, equals() hashCode()
//           Remember: eauals() amd hashCode() should use immitable variables
//
// This class is immutable so we can use in a multi-threading environment (thread-safe)

public final class GroupMembershipCacheKey {  // final means this class cannot be extended

    private final String userId;   // an individual
    private final String groupId;  // a group they belongs to

    // adding to final a parameter means methods cannot change values in references passed to it
    public GroupMembershipCacheKey(final String userId, final String groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public String getGroupId() {
        return groupId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, groupId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        GroupMembershipCacheKey request = (GroupMembershipCacheKey) obj;

        return userId.equals(request.userId) && groupId.equals(request.groupId);
    }
}
