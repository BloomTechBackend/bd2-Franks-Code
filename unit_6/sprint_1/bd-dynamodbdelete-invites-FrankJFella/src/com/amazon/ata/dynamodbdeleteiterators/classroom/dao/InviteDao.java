package com.amazon.ata.dynamodbdeleteiterators.classroom.dao;

import com.amazon.ata.dynamodbdeleteiterators.classroom.dao.models.Invite;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

/**
 * Manages access to Invite items.
 */
public class InviteDao {
    private DynamoDBMapper mapper;

    /**
     * Constructs a DAO with the given mapper.
     * @param mapper The DynamoDBMapper to use
     */
    @Inject
    public InviteDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Fetches an invite by event ID and member ID.
     * @param eventId The event ID of the invite
     * @param memberId The member ID of the invite
     * @return the invite, if found; null otherwise
     */
    public Invite getInvite(String eventId, String memberId) {
        return mapper.load(Invite.class, eventId, memberId);
    }

    /**
     * Fetches all invites sent to a given member.
     * @param memberId The ID of the member to fetch invites for (sent to)
     * @return List of Invite objects sent to the given member
     */
    public List<Invite> getInvitesSentToMember(String memberId) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
            .withFilterExpression("memberId = :memberId")
            .withExpressionAttributeValues(ImmutableMap.of(":memberId", new AttributeValue(memberId)));
        return new ArrayList<>(mapper.scan(Invite.class, scanExpression));
    }

    /**
     * Creates a new invite.
     * @param invite The invite to create
     * @return The newly created invite
     */
    public Invite createInvite(Invite invite) {
        mapper.save(invite);
        return invite;
    }

    /**
     * Cancels the invite corresponding to the event + member IDs.
     * @param eventId event ID for the invite to cancel
     * @param memberId member ID for the invite to cancel
     * @return The updated Invite if found; null otherwise.
     */
    public Invite cancelInvite(String eventId, String memberId) {
        Invite invite = mapper.load(Invite.class, eventId, memberId);
        if (Objects.isNull(invite)) {
            return null;
        }

        if (!invite.isCanceled()) {
            invite.setCanceled(true);
            mapper.save(invite);
        }
        return invite;
    }

    /**
     * Deletes the invite indicated by eventId, memberId.
     * For extra safety, deletes conditional on the invite not having been
     * accepted (isAttending is false).
     * @param eventId The event the invite is for
     * @param memberId The member the invite is sent to
     * @return true if the invite was deleted; false if it was not deleted because the
     *         invite isAttending is set to true.
     */
    public boolean deleteInvite(String eventId, String memberId) {
        Invite inviteToDelete = new Invite();   //  instantiate an Invite object to send to DynamoDB
        inviteToDelete.setEventId(eventId);     //  set the event id for invitations to delete
        inviteToDelete.setMemberId(memberId);   //  set the members whose invitations should be deleted

        // Define a condition to tell DynamoDB to delete invitations for the Event/Member if teh are not attending
        //    not attending means isAttending is false
        // DynamoDB uses a DynamoDBDeleteExpression to specify a condition for delete
        //     it also uses an ExpectedAttributeValue object to hold the condition

        DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression();  // Use this to do conditional delete

        // define a condition to compare a value in the database to be not equal true
        //    isAttending is specified in the withExpectedEntry()
        //    condition is specified in the ExpectedAttributeValue()
        //    connect them with the withExpectedEntry()
        ExpectedAttributeValue deleteCondition = new ExpectedAttributeValue()
                .withComparisonOperator(ComparisonOperator.NE)       // Used in the conditions
                .withValue(new AttributeValue().withBOOL(true));     // value to be used to test database value with operator
        // Connect the delete condition with an attribute in the database
        deleteExpression.withExpectedEntry("isAttending", deleteCondition);

        try {  // handle the ConditionalCheckFailedException if the delete fails
            // Tell DynamoDB to perform the conditional delete using  the Object with the key values and DynamoDBDeleteExpression
            mapper.delete(inviteToDelete, deleteExpression);
        }
        catch (ConditionalCheckFailedException excpetionObject) {
            return false;  // indictate the delete failed
        }
        return true;   // indicate the delete was successful
    }
}
