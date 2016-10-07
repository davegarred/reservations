package com.nullgeodesic.reservations;

import static com.nullgeodesic.reservations.Reservation.reservation;
import static java.time.Month.NOVEMBER;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class ReservationList extends ArrayList<Reservation> {

	public ReservationList() {
		this.add(reservation(NOVEMBER, 1, 4, Customer.PLANCK));
		this.add(reservation(NOVEMBER, 1, 3, Customer.MAXWELL));
		this.add(reservation(NOVEMBER, 2, 1, Customer.FARADAY));
		this.add(reservation(NOVEMBER, 3, 3, Customer.BOHR));
		this.add(reservation(NOVEMBER, 4, 2, Customer.FERMI));
	}
}
