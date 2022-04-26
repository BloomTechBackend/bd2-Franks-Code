package com.amazon.ata.metrics.classroom.activity;

import com.amazon.ata.metrics.classroom.dao.ReservationDao;
import com.amazon.ata.metrics.classroom.dao.models.UpdatedReservation;
import com.amazon.ata.metrics.classroom.metrics.MetricsConstants;
import com.amazon.ata.metrics.classroom.metrics.MetricsPublisher;
import com.amazonaws.services.cloudwatch.model.StandardUnit;

import java.time.ZonedDateTime;
import javax.inject.Inject;

/**
 * Handles requests to modify a reservation
 */
public class ModifyReservationActivity {

    private ReservationDao reservationDao;
    private MetricsPublisher metricsPublisher;

    /**
     * Construct ModifyReservationActivity.
     * @param reservationDao Dao used for modify reservations.
     */
    @Inject
    public ModifyReservationActivity(ReservationDao reservationDao, MetricsPublisher metricsPublisher) {
        this.reservationDao = reservationDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Modifies the given reservation.
     * Increment the ModifiedReservationCount metric
     * Update the ReservationRevenue metric with difference between old and new reservation cost
     *
     * @param reservationId Id to modify reservations for
     * @param checkInDate modified check in date
     * @param numberOfNights modified number of nights
     * @return UpdatedReservation that includes the old reservation and the updated reservation details.
     */
    public UpdatedReservation handleRequest(final String reservationId, final ZonedDateTime checkInDate,
                                            final Integer numberOfNights) {

        UpdatedReservation updatedReservation = reservationDao.modifyReservation(reservationId, checkInDate,
            numberOfNights);

        // Increment the ModifiedReservationCount metric
        //                              metric-name                         ,value, unit-for-value
        metricsPublisher.addMetric(MetricsConstants.MODIFIED_COUNT,1, StandardUnit.Count);
        //  Static constant is coded with classname.enum-name

        // Calculate the difference between the old and new reservation cost
        // and update the ReservationRevenue metric with the value
        // The Reservation returned from the modifyReservation has the old and new Reservation
        // Since totalCost is stored as BigDecimal in a Reservation, we have to treat it as such

        double revenueDifference = (updatedReservation.getModifiedReservation().getTotalCost()
                          .subtract(updatedReservation.getOriginalReservation().getTotalCost())
                          .doubleValue());

        metricsPublisher.addMetric(MetricsConstants.BOOKING_REVENUE,revenueDifference, StandardUnit.None);

        return updatedReservation;
    }
}
