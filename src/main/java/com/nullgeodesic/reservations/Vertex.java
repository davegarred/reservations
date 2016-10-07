package com.nullgeodesic.reservations;

import java.time.LocalDate;

public class Vertex extends AbstractObject {

	public final Customer customer;
	public final LocalDate date;
	public final Reservation reservation;

	public Vertex(Customer customer, LocalDate date, Reservation reservation) {
		this.customer = customer;
		this.date = date;
		this.reservation = reservation;
	}

}
