package com.amazon.ata.metrics.classroom.activity;

import com.amazon.ata.metrics.classroom.dao.ReservationDao;
import com.amazon.ata.metrics.classroom.dao.models.Reservation;
import com.amazon.ata.metrics.classroom.metrics.MetricsConstants;
import com.amazon.ata.metrics.classroom.metrics.MetricsPublisher;
import com.amazonaws.services.cloudwatch.model.StandardUnit;

import javax.inject.Inject;

/**
 * Handles requests to cancel a reservation.
 */
public class CancelReservationActivity {

    private ReservationDao   reservationDao;
    private MetricsPublisher metricsPublisher;

    /**
     * Constructs a CancelReservationActivity
     * @param reservationDao Dao used to update reservations.
     */
    @Inject
    public CancelReservationActivity(ReservationDao reservationDao, MetricsPublisher metricsPublisher) {
        this.reservationDao   = reservationDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Cancels the given reservation.
     * Increment the CanceledReservationCount metric
     * Update the ReservationRevenue metric
     *
     * @param reservationId of the reservation to cancel.
     * @return canceled reservation
     */
    public Reservation handleRequest(final String reservationId) {

        Reservation response = reservationDao.cancelReservation(reservationId);
        // Increment the CanceledReservationCount metric
        //                              metric-name                         ,value, unit-for-value
        metricsPublisher.addMetric(MetricsConstants.CANCEL_COUNT,1, StandardUnit.Count);
        //  Static constant is coded with classname.enum-name

        // Update the ReservationRevenue metric to reflect loss of revenue from the cancellation
        // The total cost of the Reservation is stored in the response from bookReservation()
        //     as a negative value (per Will Kim)
        // getTotalCost() returns a BigDecimal, addMetric needs a double value

        //                              metric-name                ,value, unit-for-value
        metricsPublisher.addMetric(MetricsConstants.BOOKING_REVENUE,response.getTotalCost().doubleValue()
                ,StandardUnit.None);

        return response;
    }
}
