package com.nullgeodesic.reservations.reservation_list;

import static com.nullgeodesic.reservations.domain.Reservation.reservation;
import static java.time.Month.NOVEMBER;

import java.util.ArrayList;

import com.nullgeodesic.reservations.domain.Customer;
import com.nullgeodesic.reservations.domain.Reservation;

@SuppressWarnings("serial")
public class ShortReservationList extends ArrayList<Reservation> {

	public ShortReservationList() {
		this.add(reservation(NOVEMBER, 1, 4, Customer.PLANCK));
		this.add(reservation(NOVEMBER, 1, 3, Customer.MAXWELL));
		this.add(reservation(NOVEMBER, 2, 1, Customer.FARADAY));
		this.add(reservation(NOVEMBER, 3, 3, Customer.BOHR));
		this.add(reservation(NOVEMBER, 6, 8, Customer.FERMI));
		this.add(reservation(NOVEMBER, 10, 2, Customer.HEISENBERG));
		this.add(reservation(NOVEMBER, 10, 3, Customer.DIRAC));
		this.add(reservation(NOVEMBER, 10, 4, Customer.PAULI));
		this.add(reservation(NOVEMBER, 8, 2, Customer.MARCONI));
		this.add(reservation(NOVEMBER, 12, 2, Customer.BORN));
		this.add(reservation(NOVEMBER, 14, 2, Customer.BOLTZMANN));
		this.add(reservation(NOVEMBER, 16, 2, Customer.SOMMERFELD));
		this.add(reservation(NOVEMBER, 10, 2, Customer.JOULE));
		this.add(reservation(NOVEMBER, 12, 2, Customer.MICHELSON));
		this.add(reservation(NOVEMBER, 5, 2, Customer.LORENTZ));
		this.add(reservation(NOVEMBER, 19, 2, Customer.PENZIAS));
		this.add(reservation(NOVEMBER, 13, 2, Customer.WILSON));
		this.add(reservation(NOVEMBER, 8, 2, Customer.WATSON));
		this.add(reservation(NOVEMBER, 7, 2, Customer.CRICK));
	}
}
