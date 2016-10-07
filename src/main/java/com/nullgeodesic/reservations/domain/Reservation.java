package com.nullgeodesic.reservations.domain;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class Reservation extends AbstractObject {

	private static final DateTimeFormatter CLEAN_DATE_PATTERN = DateTimeFormatter.ofPattern("MMMM d, YYYY");
	public final Customer customer;
	public final LocalDate arrival;
	public final LocalDate departure;

	public Reservation(LocalDate arrival, LocalDate departure, Customer customer) {
		this.customer = customer;
		this.arrival = arrival;
		this.departure = departure;
	}

	public static Reservation reservation(LocalDate arrival, int stayLength, Customer customer) {
		final LocalDate departure = arrival.plusDays(stayLength);
		return new Reservation(arrival, departure, customer);
	}
	public static Reservation reservation(Month arrivalMonth, int arrivalDay, int stayLength, Customer customer) {
		final LocalDate now = LocalDate.now();
		final int currentYear = now.getYear();
		final LocalDate tentativeArrival = LocalDate.of(currentYear, arrivalMonth, arrivalDay);
		final LocalDate arrival = (tentativeArrival.isAfter(now))
				? tentativeArrival
				: LocalDate.of(currentYear + 1, arrivalMonth, arrivalDay);
		final LocalDate departure = arrival.plusDays(stayLength);
		return new Reservation(arrival, departure, customer);
	}


	public String format() {
		return String.format("%s\n\tArrives: %s\n\tDeparts: %s",this.customer, this.arrival.format(CLEAN_DATE_PATTERN), this.departure.format(CLEAN_DATE_PATTERN));
	}

}
