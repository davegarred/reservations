package com.nullgeodesic.reservations;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class Reservation extends AbstractObject {

	private static final DateTimeFormatter CLEAN_DATE_PATTERN = DateTimeFormatter.ofPattern("MMMM d, YYYY");
	public final Customer customer;
	public final LocalDate arrival;
	public final LocalDate departure;

	public Reservation(Customer customer, LocalDate arrival, LocalDate departure) {
		this.customer = customer;
		this.arrival = arrival;
		this.departure = departure;
	}

	public static Reservation reservation(Customer customer, int arrivalYear, int arrivalMonth, int arrivalDay, int days) {
		final LocalDate arrival = LocalDate.of(arrivalYear, arrivalMonth, arrivalDay);
		final LocalDate departure = arrival.plusDays(days);
		return new Reservation(customer, arrival, departure);
	}
	public static Reservation reservation(Month arrivalMonth, int arrivalDay, int days, Customer customer) {
		final LocalDate now = LocalDate.now();
		final int currentYear = now.getYear();
		final LocalDate tentativeArrival = LocalDate.of(currentYear, arrivalMonth, arrivalDay);
		final LocalDate arrival = (tentativeArrival.isAfter(now))
				? tentativeArrival
				: LocalDate.of(currentYear + 1, arrivalMonth, arrivalDay);
		final LocalDate departure = arrival.plusDays(days);
		return new Reservation(customer, arrival, departure);
	}


	public String format() {
		return String.format("%s\n\tArrives: %s\n\tDeparts: %s",this.customer, this.arrival.format(CLEAN_DATE_PATTERN), this.departure.format(CLEAN_DATE_PATTERN));
	}

}
