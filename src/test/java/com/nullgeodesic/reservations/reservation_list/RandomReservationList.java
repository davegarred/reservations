package com.nullgeodesic.reservations.reservation_list;

import static com.nullgeodesic.reservations.domain.Customer.ALL_CUSTOMERS;
import static com.nullgeodesic.reservations.domain.Reservation.reservation;
import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import com.nullgeodesic.reservations.domain.Customer;
import com.nullgeodesic.reservations.domain.Reservation;

@SuppressWarnings("serial")
public class RandomReservationList extends ArrayList<Reservation> {

	private static final int MAX_STAY_LENGTH = 2;
	private static final int TARGET_RESERVATIONS_PER_NIGHT = 4;

	private final Random random = new Random();

	public RandomReservationList(LocalDate startDate, LocalDate endDate) {
		final int totalDaysInPeriod = (int) DAYS.between(startDate, endDate);
		final int targetRoomNights = TARGET_RESERVATIONS_PER_NIGHT * totalDaysInPeriod;
		int totalRoomNights = 0;
		while(totalRoomNights < targetRoomNights) {
			final LocalDate arrivalDate = randomDate(startDate, totalDaysInPeriod);
			final int totalNights = randomStayLength((int)DAYS.between(arrivalDate, endDate));
			this.add(reservation(arrivalDate, totalNights, randomCustomer()));
			totalRoomNights += totalNights;
		}
	}

	private int randomStayLength(int maxLength) {
		final int shortStay = this.random.nextInt(MAX_STAY_LENGTH);
		return shortStay<maxLength ? shortStay+1 : maxLength;
	}

	private LocalDate randomDate(LocalDate startDate, int totalDaysInPeriod) {
		final int daysFromStart = this.random.nextInt(totalDaysInPeriod - 1);
		return startDate.plusDays(daysFromStart);
	}

	private Customer randomCustomer() {
		final Customer prototypeCustomer = ALL_CUSTOMERS.get(this.random.nextInt(ALL_CUSTOMERS.size()));
		return new Customer(prototypeCustomer.name + "_" + this.random.nextInt(1000));
	}

}
