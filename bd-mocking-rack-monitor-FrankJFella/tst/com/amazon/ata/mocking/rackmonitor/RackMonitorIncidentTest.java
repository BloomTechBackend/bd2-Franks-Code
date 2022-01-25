package com.amazon.ata.mocking.rackmonitor;

import com.amazon.ata.mocking.rackmonitor.clients.warranty.WarrantyClient;
import com.amazon.ata.mocking.rackmonitor.clients.wingnut.WingnutClient;
import com.amazon.ata.mocking.rackmonitor.exceptions.RackMonitorException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;  // @initMocks is deprecated (hence the strikeout)

// We are  using the Mokito framework for mocking
// We use mocking to isolate our test from external data and processes
//     Test are not dependent on outside components
// If there are external classes used: @Mock to tell mockito to simulate them
//    (look at import statements)
public class RackMonitorIncidentTest {
    // No need to @Mock RackMonitor class since it is the class we are testing
    //      so we know it exists

    // @InjectMocks  - not needed for this particular example
    RackMonitor rackMonitor;

    @Mock
    WingnutClient wingnutClient;   // external class objects - @Mock

    @Mock
    WarrantyClient warrantyClient; // external class objects - @Mock

    @Mock // @Mock objects rather instantiate them
    Rack rack;

    // We can't really Mock Server because se need to be able to modify it
    // Instantiate a generic Server and modify its attributes (healthy, unhealthy, et al)
    //       as needed
    Server testServer = new Server("TEST_SERVER");
    Map<Server, Integer> rackServerUnits;

    // Instantiate the Map<Server, Double> returned by the mock rack.getHealth()
    Map<Server, Double> result;  // Hold the result from rack.getHealth()

    @BeforeEach   // Do this before each test is run - setup for each test
    void setUp() {
        // warrantyClient = new WarrantyClient(); // no need to instantiate because it is @Mock'd
        // wingnutClient = new WingnutClient();   // no need to instantiate because it is @Mock'd

        // We need to mokito to create the @Mock objects we have identified
        initMocks(this);    // @initMocks is deprecated (hence the strikeout)
        rackServerUnits = new HashMap<>();
        rackServerUnits.put(testServer, 1);
        // rack1 = new Rack("RACK01", rack1ServerUnits);  // no need to instantiate because it is @Mock'd

    `    // Pooja gave me permission to talk rack in the statement below tomorrow
        rackMonitor = new RackMonitor(new HashSet<>(Arrays.asList(rack)),
            wingnutClient, warrantyClient, 0.9D, 0.8D);
    }

    @Test // a single test in this JUnit suite - tests one method
    // using throws Exception means you don't worry any checked exceptions that might be thrown
    //       since all exceptions are subclasses of Exception throws Exception covers all of them
    public void getIncidents_withOneUnhealthyServer_createsOneReplaceIncident() throws Exception {
        // GIVEN
        // The rack is set up with a single unhealthy server
        // We've reported the unhealthy server to Wingnut
        rackMonitor.monitorRacks();

        // WHEN
        Set<HealthIncident> actualIncidents = rackMonitor.getIncidents();

        // THEN
        HealthIncident expected =
            new HealthIncident(unhealthyServer, rack1, 1, RequestAction.REPLACE);
        assertTrue(actualIncidents.contains(expected),
            "Monitoring an unhealthy server should record a REPLACE incident!");
    }

    @Test
    public void getIncidents_withOneShakyServer_createsOneInspectIncident() throws Exception {
        // GIVEN
        // The rack is set up with a single shaky server
        rack1ServerUnits = new HashMap<>();
        rack1ServerUnits.put(shakyServer, 1);
        rack1 = new Rack("RACK01", rack1ServerUnits);
        rackMonitor = new RackMonitor(new HashSet<>(Arrays.asList(rack1)),
            wingnutClient, warrantyClient, 0.9D, 0.8D);
        // We've reported the shaky server to Wingnut
        rackMonitor.monitorRacks();

        // WHEN
        Set<HealthIncident> actualIncidents = rackMonitor.getIncidents();

        // THEN
        HealthIncident expected =
            new HealthIncident(shakyServer, rack1, 1, RequestAction.INSPECT);
        assertTrue(actualIncidents.contains(expected),
            "Monitoring a shaky server should record an INSPECT incident!");
    }

    @Test
    public void getIncidents_withOneHealthyServer_createsNoIncidents() throws Exception {
        // GIVEN
        // monitorRacks() will find only healthy servers

        // WHEN
        Set<HealthIncident> actualIncidents = rackMonitor.getIncidents();

        // THEN
        assertEquals(0, actualIncidents.size(),
            "Monitoring a healthy server should record no incidents!");
    }

    @Test
    public void monitorRacks_withOneUnhealthyServer_replacesServer() throws Exception {
        // GIVEN
        // The rack is set up with a single unhealthy server

        // WHEN
        rackMonitor.monitorRacks();

        // THEN
        // There were no exceptions
        // No way to tell we called the warrantyClient for the server's Warranty
        // No way to tell we called Wingnut to replace the server
    }

    @Test
    public void monitorRacks_withUnwarrantiedServer_throwsServerException() throws Exception {
        // GIVEN
        Server noWarrantyServer = new Server("TEST0052");
        rack1ServerUnits = new HashMap<>();
        rack1ServerUnits.put(noWarrantyServer, 1);
        rack1 = new Rack("RACK01", rack1ServerUnits);
        rackMonitor = new RackMonitor(new HashSet<>(Arrays.asList(rack1)),
            wingnutClient, warrantyClient, 0.9D, 0.8D);

        // WHEN and THEN
        assertThrows(RackMonitorException.class,
            () -> rackMonitor.monitorRacks(),
            "Monitoring a server with no warranty should throw exception!");
    }
}
