package com.nullgeodesic.reservations;

import static java.time.Month.NOVEMBER;

import java.time.LocalDate;
import java.util.List;

import com.nullgeodesic.reservations.domain.Reservation;
import com.nullgeodesic.reservations.reservation_list.ShortReservationList;

public class SimpleTest extends AbstractTest {

	private static final LocalDate START_DATE = LocalDate.of(2016, NOVEMBER, 1);
	private static final LocalDate END_DATE = LocalDate.of(2016, NOVEMBER, 30);

	@Override
	protected LocalDate startDate() {
		return START_DATE;
	}

	@Override
	protected LocalDate endDate() {
		return END_DATE;
	}

	@Override
	protected List<Reservation> reservations() {
		return new ShortReservationList();
	}

}
