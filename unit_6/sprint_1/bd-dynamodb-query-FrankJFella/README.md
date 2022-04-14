# Re-Invites

## Introduction

We're continuing to build our app that lets members create events (e.g. parties, book
protests, clubs, dining out). This activity will focus on members being able to view
announcements from event creators for events and event creators being able to retrieve 
the invites they have sent out.

We've added an additional entity since our previous lesson to provide more functionality
to our app: EventAnnouncements.  EventAnnouncements are announcements that event creators 
can post for their events to provide updates and additional information about their event.  

We'll be using four key entities in our app:
* Members: The people who can invite one another to events
    * Partition key: id
* Events: The events that people are being invited to
    * Partition key: id
* Invites: A request for a particular Member to attend a particular Event
    * Partition key: eventId
    * Sort key: memberId
* EventAnnouncements: Announcements posted for events
    * Partition key: eventId
    * Sort key: timePublished

The new functionality we are adding focuses on:
* EventAnnouncements
    * GetEventAnnouncements - retrieves all announcements for a particular event
    * GetEventAnnouncementsBetweenDates - retrieves announcements posted between specific dates for a particular event
* Events
    * A paginated API - GetInvitesForEventActivity

The code base follows the Activity-DAO-DynamoDBMapper pattern that we've come
to know and love. The various Activity classes each implement one operation
that our service supports. That Activity may depend on several of
`MemberDao`'s, `EventDao`'s, `InviteDao`'s, and `EventAnnoucementDao`'s methods to accomplish their
use cases. Each DAO is responsible for one model type, and only interacts
with that model's DynamoDB table.

Disclaimer: One difference that you'll notice here is that our Activity
classes don't yet accept/return Response/Result objects. They're
accepting/returning individual values. We'll do this until it's time to
create our service infrastructure and create the necessary Coral models.
That retrofit is beyond the scope of this activity, but will be fairly
easily accomplished in the Activity classes themselves when the time comes.

You'll primarily be updating DAO and Activity code, but will touch a few tests
as well, where appropriate (we'll guide you!).

For each "Phase" of the activity, there's a corresponding RDE workflow that
will test that you've satisfied the requirements for that phase. Also make
sure that the relevant unit tests for the classes you're working on are
passing (typically one Activity and 1-3 DAOs).

Helpful hint: If an RDE workflow is failing for one of the phases, and you want to
see more about the test failure, use the file URL that is printed out on the commandline
to see more detail!

## Phase 0: Preliminaries

1. Create the tables we'll be using for this activity by running these aws CLI commands:
   ```none
   aws cloudformation create-stack --region us-west-2 --stack-name dynamodbquery-eventstable --template-body file://cloudformation/events_table.yaml --capabilities CAPABILITY_IAM
   aws cloudformation create-stack --region us-west-2 --stack-name dynamodbquery-invitestable --template-body file://cloudformation/invites_table.yaml --capabilities CAPABILITY_IAM
   aws cloudformation create-stack --region us-west-2 --stack-name dynamodbquery-memberstable --template-body file://cloudformation/members_table.yaml --capabilities CAPABILITY_IAM
   aws cloudformation create-stack --region us-west-2 --stack-name dynamodbquery-eventannouncementstable01 --template-body file://cloudformation/event_announcements_table.yaml --capabilities CAPABILITY_IAM
   ```

1. Log into your AWS account and verify that the tables exist and have
   sample data.
1. Discuss the different attributes with your team to make sure you all understand
   what they represent.
1. As a final verification, run the `Phase0Test` tests and make sure they passes.

GOAL: Events, Invites, Members, and EventAnnouncements tables are all created in your AWS Account, and
the attributes make sense

Phase 0 is complete when:
- You understand the 4 data types and their relationships
- Events, Invites, Members, EventAnnouncements tables exist with some sample data
- `Phase0Test` tests pass


## Phase 1: Get all announcements for an event

Members want to view all the announcements posted for a particular event, so we'll be implementing
logic to retrieve all announcements for an event. This will be implemented in the 
`GetEventAnnouncementsActivity` class, which is already stubbed out for you.

The activity class will call the `getEventAnnouncements()` method in the `EventAnnouncementDao`.
This method has been declared for you, but you'll be implementing it!

1. Implement `getEventAnnouncements()` in `EventAnnouncementDao` to return all event announcements
   for the given `eventId`.   
1. Add a unit test to `EventAnnouncementDaoTest` for the case where the event ID provided has 
   announcements, i.e. the happy case where DynamoDB returns a list of results.
   Some notes on writing unit tests for `query` calls:
   * You will need to mock the `query` call to `DynamoDBMapper` to return a list of
      accepted invites. The `query` method returns a `PaginatedQueryList`. These are pretty tough 
      to create - go ahead and open the class and take a look at the constructor! Instead, let's
      mock it! Have your `thenReturn` method return a mocked `PaginatedQueryList`.
   * We can inspect the `DynamoDBQueryExpression` passed into `query` using a mocking tool called 
      `ArgumentCaptor`. You do not need to use `ArgumentCaptor`s for this phase, but we encourage you 
      to take a look at Extension 1 of this activity, which walks through adding an `ArgumentCaptor` 
      to this test.
1. Implement `GetEventAnnouncementsActivity`'s `handleRequest()` method. Ensure the tests in
   `GetEventAnnouncementsActivityTest` are passing.
1. Verify end-to-end using the integration test by running `Phase1Test`

GOAL: We can request a list of event announcements for a specific event.

Phase 1 is complete when:
- The above unit tests are passing
- `Phase1Test` tests pass


## Phase 2: Get Event Announcements Between Dates

Members want to be able to browse through event announcements published within certain timeframes 
to more easily find information from earlier announcements, so we'll be implementing logic to get 
event announcements that were posted between specified dates. This will be implemented in the 
`GetEventnnouncementsBetweenDatesActivity` class, which is already stubbed out for you.

The activity class will call the `getEventAnnouncementsBetweenDates()` method in the 
`EventAnnouncementDao`. This method has been declared for you, but you'll be implementing it!

1. Implement `getEventAnnouncementsBetweenDates()` in `EventAnnoucementDao` to return only events 
   whose `timePublished` are between the given `startDate` and `endDate`. You will need to use a 
   key condition expression to narrow the scope of your query.
   * NOTE: DynamoDB stores dates as Strings in our tables, so you will need to convert the `startDate` 
     and `endDate` into the proper string format when passing them into your query expression. Use 
     the provided `ZonedDateTimeConverter` class (under `com.amazon.ata.dynamodbquery.converter`) 
     to convert the `ZonedDateTime`s into `Strings`.
1. Add a unit test to `EventAnnouncementDaoTest` to test `getEventAnnouncementsBetweenDates()`. 
1. Add a unit test to `GetEventAnnoucementsBetweenDatesActivityTest` to `verify()` that
   `EventAnnouncementDao` receives a call to `getEventAnnouncementsBetweenDates()` when its method
   is invoked.
1. Implement `GetEventAnnouncementsBetweenDatesActivity`'s `handleRequest()` method.
1. Verify end-to-end using the integration test by running `Phase2Test`.
   
GOAL: We can request a list of announcements posted between particular dates for a particular event.

Phase 2 is complete when:
- The above unit tests are passing
- `Phase2Test` tests pass

## Phase 3: Pagination

Event organizers also want to be able to view all the invitations they've sent out.
Because there may be a large number of invitations sent out, and we want to support 
event organizers being able to view the invitations on smaller screens (like a mobile 
phone) or slower devices, we only want to load 10 invitations on a page at a time.

In the `InviteDao`, we'll call this operation, `getInvitesForEvent()`.
This method has been declared for you, but you'll implement it. Notice that it takes in 
both an `eventId` and `exclusiveStartMemberId`. `exclusiveStartMemberId` should be the 
the last memberId returned from the previous page, and you would use this along with the 
eventId when setting `withExclusiveStartKey` in your `DynamoDBQueryExpression`. When 
retrieving the first page of the query, `exclusiveStartMemberId` would be null, since 
there would be no previous page!

The Activity is also stubbed out in `GetInvitesForEventActivity`.

1. Implement `InviteDao.getInvitesForEvent()` to return the next 10 invites for
   that event ID, based on the passed `exclusiveStartMemberId`. (If `exclusiveStartMemberId`
   is null, you should be getting the first 10 invites in the table.)
1. Add a unit test to `InviteDaoTest` to `verify()` that `DynamoDBMapper` receives
   a query call to with the expected parameters when `InviteDao.getInvitesForEvent()` is called.
   Like with Phase 1, use `ArgumentCaptor`s to help test your query calls. 
1. Add a unit test to `GetInvitesForEventActivityTest` to `verify()` that
   `InviteDao` receives a call to `getInvitesForEvent()` when its method
   is invoked.
1. Implement `GetInvitesForEventActivity`'s `handleRequest()` method.
1. Verify end-to-end using the integration test by running `Phase3Test`.

GOAL: We can request a list of paginated invites for a specific event.

Phase 3 is complete when:
- The above unit tests are passing
- `Phase3Test` tests pass

## EXTENSION 1 - Use ArgumentCaptors in your unit tests
Let's update your unit tests that you wrote to use a new mocking tool called `ArgumentCaptor`s. ([Javadoc](https://site.mockito.org/javadoc/current/org/mockito/ArgumentCaptor.html))

1. Update your unit test you wrote for phase 1 in `EventAnnoucementDaoTest` for the case where the event ID provided
   has invites. An example of a unit test that uses an `ArgumentCaptor` similar to the one we are about to write 
   can be found in your project. Read through `getBookFromCatalog_oneVersion_returnVersion1` in `CatalogDaoTest` 
   before we get started.   
1. In addition to the assertions you've already added, let's also verify that `query` was called and
   use our captor to inspect the `DynamoDBQueryExpression` that was passed to it. An `ArgumentCaptor` can
   be used to capture arguments for mocked methods. Let's declare one in the `GIVEN` section: 
   ```
   ArgumentCaptor<DynamoDBQueryExpression<EventAnnoucement>> captor = ArgumentCaptor.forClass(DynamoDBQueryExpression.class);
   ```
1. Now in your `THEN` section, let's verify that the `query` method was called and capture the state of 
   the provided `DynamoDBQueryExpression`. Here, we use the `capture` method in place of a `Matcher`.   
   ```
   verify(mapper).query(eq(EventAnnoucement.class), captor.capture());
   ```
1. Calling `captor.getValue()` will now return the `DynamoDBQueryExpression` that was passed to query. We can
   retrieve values from it, like the `EventAnnouncement` passed to its `withHashKeyValues` method. 
   ```
   DynamoDBQueryExpression<EventAnnouncement> capturedQueryExpression = captor.getValue();
   EventAnnouncement eventAnnouncement = capturedQueryExpression.getHashKeyValues();
   ```
1. For now, let's just verify that the `EventAnnouncement` provided to our `DynamoDBQueryExpression` has
   the correct `eventId` value. 
   
Run your updated unit test and ensure that it still passes. Then update the rest of your unit tests to use
`ArgumentCaptors` for testing your `query` calls!

## EXTENSION 2 - Replacing Scan with a Query
Scanning is an expensive and slow operation in DynamoDB. Depending on your table design, this is your 
only option. However, DynamoDB allows us to add a Global Secondary Index (GSI), which tells
DynamoDB to maintain a copy of our table, but with a different key. In this phase, we have created a GSI with
a new partition and sort key, `memberId` and `timeReceived`. Take a look at the Invite model to see the new annotations
on these fields, `@DynamoDBIndexHashKey` and `@DynamoDBIndexRangeKey`. 

Let's write a query that uses this new copy of our table. Update `getInvitesSentToMember` in `InviteDao` to use a 
query, rather than a scan. Using a GSI requires that we use a Key Condition Expression to specify our partition key. 
There are a couple other things we will need to add to our `DynamoDBQueryExpression`. We need to specify the name of
out index using `withIndexName`. Finally, querying a GSI does NOT support consistent reads. You have to explicitly
acknowledge this by setting this to `false` using `withConsistentRead`.

Make sure to still return the results as an `ArrayList`, so that we call `listIterator()` on it in 
`GetInvitesForMemberActivity`.

Add a new unit test in `InviteDao` using `ArgumentCaptor`s to verify the behavior. Try verifying that
the GSI was used in the `DynamoDBQueryExpression`. 

The `getInvitesSentToMember` method is already in use by `GetInvitesForMemberActivity`'s `handleRequest()` method.
You can verify end-to-end using the integration test by running `Phase4Test`.



