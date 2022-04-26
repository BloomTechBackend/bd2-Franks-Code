package com.amazon.ata.metrics.classroom.activity;

import com.amazon.ata.metrics.classroom.dao.ReservationDao;
import com.amazon.ata.metrics.classroom.dao.models.Reservation;
import com.amazon.ata.metrics.classroom.metrics.MetricsPublisher;
import com.amazon.ata.metrics.classroom.metrics.MetricsConstants; // access to our metric constants
import com.amazonaws.services.cloudwatch.model.StandardUnit;      // access to CloudWatch standard units

import javax.inject.Inject;

/**
 * Handles requests to book a reservation.
 */
public class BookReservationActivity {

    private ReservationDao   reservationDao;
    private MetricsPublisher metricsPublisher;  // Metric Framework (CloudWatch) interface class

    /**
     * Constructs a BookReservationActivity
     * @param reservationDao Dao used to create reservations.
     */
    @Inject
    public BookReservationActivity(ReservationDao reservationDao, MetricsPublisher metricsPublisher) {
        this.reservationDao = reservationDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Creates a reservation with the provided details.
     * Increment the BookedReservationCount metric
     * Record the ReservationRevenue metric
     *
     * @param reservation Reservation to create.
     * @return
     */
    public Reservation handleRequest(Reservation reservation) {

        // Create a reservation in the data source
        Reservation response = reservationDao.bookReservation(reservation);

        // Increment the Booked ReservationCount metric
        //                              metric-name                         ,value, unit-for-value
        metricsPublisher.addMetric(MetricsConstants.BOOKED_RESERVATION_COUNT,1,StandardUnit.Count);
        //  Static constant is coded with classname.enum-name

        // Update the ReservationRevenue metric with the total cost of the reservation
        // The total cost of the Reservation is stored in the response from bookReservation()
        // getTotalCost() returns a BigDecimal, addMetric needs a double value

        //                              metric-name                         ,value, unit-for-value
        metricsPublisher.addMetric(MetricsConstants.BOOKING_REVENUE,response.getTotalCost().doubleValue()
                                  ,StandardUnit.None);



        return response;
    }
}
