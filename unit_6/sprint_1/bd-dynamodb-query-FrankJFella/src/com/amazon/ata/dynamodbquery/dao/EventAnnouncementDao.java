package com.amazon.ata.dynamodbquery.dao;

import com.amazon.ata.dynamodbquery.converter.ZonedDateTimeConverter;
import com.amazon.ata.dynamodbquery.dao.models.EventAnnouncement;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.awt.*;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 * Manages access to EventAnnouncement items.
 */
public class EventAnnouncementDao {

    // Define a constant to represent a Date/Time converter
    // This done to make it easier to replace Date/Time converter with a different one
    private static final ZonedDateTimeConverter ZONED_DATE_TIME_CONVERTER = new ZonedDateTimeConverter();

    private DynamoDBMapper mapper;

    /**
     * Creates an EventDao with the given DDB mapper.
     * @param mapper DynamoDBMapper
     */
    @Inject
    public EventAnnouncementDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Gets all event announcements for a specific event.
     *
     * @param eventId The event to get announcements for.
     * @return the list of event announcements.
     */
    public List<EventAnnouncement> getEventAnnouncements(String eventId) {
        // TODO: implement
        // Instantiate an EventAnnouncement object for interaction with the database
        //       and set it's eventId to the eventId request
        EventAnnouncement eventAnnouncement = new EventAnnouncement();
        eventAnnouncement.setEventId(eventId); // set the object is to eventIf we are passed

        // Define a Query Expression to filter our database data based on the "hash key" eventId
        DynamoDBQueryExpression<EventAnnouncement> queryExpression = new DynamoDBQueryExpression<EventAnnouncement>()
                .withHashKeyValues(eventAnnouncement);

        // Go to the database with the query expression to get the data and tell it to return EventAnnouncement class objects
        return mapper.query(EventAnnouncement.class, queryExpression);
    }

    /**
     * Get all event announcements posted between the given dates for the given event.
     *
     * @param eventId The event to get announcements for.
     * @param startTime The start time to get announcements for.
     * @param endTime The end time to get announcements for.
     * @return The list of event announcements.
     */
    public List<EventAnnouncement> getEventAnnouncementsBetweenDates(String eventId, ZonedDateTime startTime,
                                                                     ZonedDateTime endTime) {
        // TODO: implement
        // Define the conditions we want the database manager to use when searching the database
        // Because there are multiple conditions we will store the conditions in a Map
        // The Map key is identifier for the condition, the Map value is the condition value as an AttributeValue
        Map<String, AttributeValue> searchConditions = new HashMap<>();
        //               identifier-for-condition       data-type(value-to-use-in-search)
        searchConditions.put(":eventId"  , new AttributeValue().withS(eventId));
        searchConditions.put(":startDate", new AttributeValue().withS(ZONED_DATE_TIME_CONVERTER.convert(startTime)));
        searchConditions.put(":endDate"  , new AttributeValue().withS(ZONED_DATE_TIME_CONVERTER.convert(endTime)));

        // Define a QueryExpression to give database to do the search using our searchConditions
        DynamoDBQueryExpression<EventAnnouncement> querySearchExpression = new DynamoDBQueryExpression<EventAnnouncement>()
                //                   table-attribute = key-in-Map
                .withKeyConditionExpression("eventId = :eventId and timePublished between :startDate and :endDate")
                .withExpressionAttributeValues(searchConditions);

         return mapper.query(EventAnnouncement.class, querySearchExpression);
    }

    /**
     * Creates a new event announcement.
     *
     * @param eventAnnouncement The event announcement to create.
     * @return The newly created event announcement.
     */
    public EventAnnouncement createEventAnnouncement(EventAnnouncement eventAnnouncement) {
        mapper.save(eventAnnouncement);
        return eventAnnouncement;
    }
}
