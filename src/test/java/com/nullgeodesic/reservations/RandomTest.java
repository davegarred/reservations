package com.nullgeodesic.reservations;

import static java.time.Month.NOVEMBER;

import java.time.LocalDate;
import java.util.List;

public class RandomTest extends AbstractTest {

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
		return new RandomReservationList(START_DATE, END_DATE);
	}

}
