package com.nullgeodesic.reservations;

import static com.nullgeodesic.reservations.Reservation.reservation;
import static java.time.Month.NOVEMBER;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SimpleTest {


	@Test
	public void test() {
		final List<Reservation> reservations = new ArrayList<>();
		reservations.add(reservation(NOVEMBER, 1, 4, "Alex Jones"));
		reservations.add(reservation(NOVEMBER, 1, 3, "Hillary Clinton"));
		reservations.add(reservation(NOVEMBER, 2, 1, "Donald Trump"));
		reservations.add(reservation(NOVEMBER, 3, 3, "Roger Stone"));
		reservations.add(reservation(NOVEMBER, 4, 2, "Mike Pense"));

		final ReservationGraph graph = new ReservationGraph(LocalDate.of(2016, NOVEMBER, 1), LocalDate.of(2016, NOVEMBER, 30), reservations);

	}


}
