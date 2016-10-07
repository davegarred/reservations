package com.nullgeodesic.reservations;

import static com.nullgeodesic.reservations.Customer.ALL_CUSTOMERS;
import static com.nullgeodesic.reservations.Reservation.reservation;
import static java.time.temporal.ChronoUnit.DAYS;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class RandomReservationList extends ArrayList<Reservation> {

	private static final BigDecimal NUMBER_OF_ROOMS = new BigDecimal(5);
	private static final BigDecimal EFFICIENCY = new BigDecimal(".65");

	private final Random random = new Random();

	public RandomReservationList(LocalDate startDate, LocalDate endDate) {
		final int totalDaysInPeriod = (int) DAYS.between(startDate, endDate);
		final int targetRoomNights = EFFICIENCY.multiply(NUMBER_OF_ROOMS).multiply(new BigDecimal(totalDaysInPeriod)).intValue();
		int totalRoomNights = 0;
		while(totalRoomNights < targetRoomNights) {
			final LocalDate arrivalDate = randomDate(startDate, totalDaysInPeriod);
			final int totalNights = upToAWeekStay((int)DAYS.between(arrivalDate, endDate));
			this.add(reservation(arrivalDate, totalNights, randomCustomer()));
			totalRoomNights += totalNights;
		}
	}

	private int upToAWeekStay(int maxLength) {
		final int shortStay = this.random.nextInt(7);
		return shortStay<maxLength ? shortStay+1 : maxLength;
	}

	private LocalDate randomDate(LocalDate startDate, int totalDaysInPeriod) {
		final int daysFromStart = this.random.nextInt(totalDaysInPeriod - 1);
		return startDate.plusDays(daysFromStart);
	}

	private Customer randomCustomer() {
		return ALL_CUSTOMERS.get(this.random.nextInt(ALL_CUSTOMERS.size()));
	}

}
