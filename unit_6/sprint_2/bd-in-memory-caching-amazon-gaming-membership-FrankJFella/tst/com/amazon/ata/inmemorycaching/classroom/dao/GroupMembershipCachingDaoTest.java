package com.amazon.ata.inmemorycaching.classroom.dao;

import com.amazon.ata.inmemorycaching.classroom.dao.models.GroupMembershipCacheKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.*;  // Access all Mockito methods statically
import static org.junit.jupiter.api.Assertions.*;  // Access all assertions in JUnit


public class GroupMembershipCachingDaoTest {
    @Mock         // Mock external database access
    private GroupMembershipDao membershipDao;

    // The unit under test
    @InjectMocks  // Since the CachingDao need the usual Dao we need to Inject
    private GroupMembershipCachingDao cachingMembershipDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void isUserInGroup_userNotInCache_delegateDaoCalled() {
        // GIVEN
        // A cache key with a known user and group
        String userId  = "knownUser";  // value doesn't matter since it's not actual used
        String groupId = "knownGroup"; // value doesn't matter since it's not actual used

        // Define a cache key to be used in teh tests
        GroupMembershipCacheKey theKey = new GroupMembershipCacheKey(userId, groupId);

        // The delegate DAO will return true when asked if the user is in the group
        when(membershipDao.isUserInGroup(theKey)).thenReturn(true);

        // WHEN
        // We ask the caching DAO if the user is in the group
        boolean result = cachingMembershipDao.isUserInGroup(userId, groupId);

        // THEN
        // The user was in the group
        assertTrue(result,"expected result to be true");

        // The delegate DAO was called
        verify(membershipDao).isUserInGroup(theKey);

        // There were no other calls to the delegate DAO
        verifyNoMoreInteractions(membershipDao);
    }

    @Test
    public void isUserInGroup_userInCache_delegateDaoNotCalled() {
        // GIVEN
        // A cache key with a known user and group
        String userId  = "userId";
        String groupId = "groupId";
        GroupMembershipCacheKey key = new GroupMembershipCacheKey(userId, groupId);

        // The delegate DAO will return true when asked if the user is in the group
        when(membershipDao.isUserInGroup(eq(key))).thenReturn(true);

        // The caching DAO has been "primed" by asking if the user is in the group
        cachingMembershipDao.isUserInGroup(userId, groupId);

        // WHEN
        // We ask the caching DAO if the user is in the group
        boolean result = cachingMembershipDao.isUserInGroup(userId, groupId);

        // THEN
        // The user was in the group
        assertTrue(result, "Expected result to be consistent with the cached GroupMembershipDao's response");

        // The delegate DAO was called exactly once
        verify(membershipDao, times(1)).isUserInGroup(eq(key));
        // There were no other calls to the delegate DAO
        verifyNoMoreInteractions(membershipDao);
    }
}
