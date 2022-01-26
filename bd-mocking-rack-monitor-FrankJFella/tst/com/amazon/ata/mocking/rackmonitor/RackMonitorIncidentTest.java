package com.amazon.ata.mocking.rackmonitor;

import com.amazon.ata.mocking.rackmonitor.clients.warranty.Warranty;
import com.amazon.ata.mocking.rackmonitor.clients.warranty.WarrantyClient;
import com.amazon.ata.mocking.rackmonitor.clients.warranty.WarrantyNotFoundException;
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
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


// We are  using the Mokito framework for mocking
// We use mocking to isolate our test from external data and processes
//     Test are not dependent on outside components
// If there are external classes used: @Mock to tell mockito to simulate them
//    (look at import statements)
public class RackMonitorIncidentTest {
    // No need to @Mock RackMonitor class since it is the class we are testing
    //      so we know it exists

    //@InjectMocks  //  not needed for this particular example
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
    Map<Server, Double> result;  // reference to hold the result from rack.getHealth()

    @BeforeEach   // Do this before each test is run - setup for each test
    void setUp() {
        // warrantyClient = new WarrantyClient(); // no need to instantiate because it is @Mock'd
        // wingnutClient = new WingnutClient();   // no need to instantiate because it is @Mock'd

        // We need to mokito to create the @Mock objects we have identified
        initMocks(this);    // @initMocks is deprecated (hence the strikeout)
        rackServerUnits = new HashMap<>();
        rackServerUnits.put(testServer, 1);

        result = new HashMap<>();  // instantiate the result Map and assign to reference
        //rack = new Rack("RACK01",rackServerUnits);  // no need to instantiate because it is @Mock'd

        // Since we replaced the actual objects with @Mock with different names we need to chnage the names in the code
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
        // We've reported the unhealthy server to Wingnut
        result.put(testServer, 0.75D);
        when(rack.getHealth()).thenReturn(result);
        when(rack.getUnitForServer(testServer)).thenReturn(1);
        when(warrantyClient.getWarrantyForServer(testServer)).thenReturn(Warranty.nullWarranty());
        rackMonitor.monitorRacks();

        // WHEN
        Set<HealthIncident> actualIncidents = rackMonitor.getIncidents();

        // THEN
        HealthIncident expected =
                new HealthIncident(testServer, rack, 1, RequestAction.REPLACE);
        assertTrue(actualIncidents.contains(expected),
                "Monitoring an unhealthy server should record a REPLACE incident!");
    }

    @Test
    public void getIncidents_withOneShakyServer_createsOneInspectIncident() throws Exception {
        // GIVEN
        // The rack is set up with a single shaky server

// We no longer need these objects because we defined in setup() in @BeforeEach
//       rackServerUnits = new HashMap<>();
//       rackServerUnits.put(testServer, 1);
//       rack = new Rack("RACK01",rackServerUnits);
//        rackMonitor = new RackMonitor(new HashSet<>(Arrays.asList(rack)),
//            wingnutClient, warrantyClient, 0.9D, 0.8D);

        // Mocking - replace the actual method calls with Mockito calls
        //           to simulate the behavior of the behavior

        // We've reported the shaky server to Wingnut
        // Follow the execution path to determien if any methods need to be mocked
        //     Do any methods in the execution path use objects we @Mock'd out
        // when Mockito encounters a rack.getHealth() call, it should return our result object
        //      above which contains a server and a health value
        result.put(testServer,.85);  // set up our test result with a shaky server (health .8 - .9)
        when(rack.getHealth()).thenReturn(result);

        // return the id for our test server when a server unit is is requested
        when(rack.getUnitForServer(testServer)).thenReturn(1);

        // return null warranty for the server if getWarrantyForServer() is called for shaky server
        when(warrantyClient.getWarrantyForServer(testServer)).thenReturn(Warranty.nullWarranty());

        rackMonitor.monitorRacks();
        // WHEN
        Set<HealthIncident> actualIncidents = rackMonitor.getIncidents();

        // THEN
        HealthIncident expected =
            new HealthIncident(testServer,rack, 1, RequestAction.INSPECT);
        assertTrue(actualIncidents.contains(expected),
            "Monitoring a shaky server should record an INSPECT incident!");
    }

    @Test
    public void getIncidents_withOneHealthyServer_createsNoIncidents() throws Exception {
        // GIVEN

        result.put(testServer,.99);  // set up our test result with a healthy server (health .9+)
        when(rack.getHealth()).thenReturn(result);

        // return the id for our test server when a server unit is is requested
        when(rack.getUnitForServer(testServer)).thenReturn(1);

        // return null warranty for the server if getWarrantyForServer() is called for shaky server
        when(warrantyClient.getWarrantyForServer(testServer)).thenReturn(Warranty.nullWarranty());

        // monitorRacks() will find only healthy servers
        rackMonitor.monitorRacks();

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
        result.put(testServer,.75);  // set up our test result with a unhealthy server (health less and .8)
        when(rack.getHealth()).thenReturn(result);

        // return the id for our test server when a server unit is is requested
        when(rack.getUnitForServer(testServer)).thenReturn(1);

        // return null warranty for the server if getWarrantyForServer() is called for shaky server
        when(warrantyClient.getWarrantyForServer(testServer)).thenReturn(Warranty.nullWarranty());

        // WHEN
        rackMonitor.monitorRacks();

        // THEN
        // There were no exceptions
        // No way to tell we called the warrantyClient for the server's Warranty
        // YES THERE IS WITH MOCKITO - verify allows determine of if or how many times a method was called

        // Verify the getWarrantyForServer was called exactly once
        verify(warrantyClient).getWarrantyForServer((testServer));

        // No way to tell we called Wingnut to replace the server
        // YES THERE IS WITH MOCKITO - verify allows determine of if or how many times a method was called
        //                                       rack, server, warranty-forserver
        verify(wingnutClient).requestReplacement(rack, 1     , Warranty.nullWarranty());
    }

    @Test
    public void monitorRacks_withUnwarrantiedServer_throwsServerException() throws Exception {
        // GIVEN
        result.put(testServer, 0.75D);
        when(rack.getHealth()).thenReturn(result);
        when(rack.getUnitForServer(testServer)).thenReturn(1);
        when(warrantyClient.getWarrantyForServer(testServer)).thenThrow(WarrantyNotFoundException.class);

        // WHEN and THEN
        assertThrows(RackMonitorException.class,
                () -> rackMonitor.monitorRacks(),
                "Monitoring a server with no warranty should throw exception!");
    }
}
