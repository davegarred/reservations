package com.nullgeodesic.reservations;

import static java.lang.String.format;
import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import com.nullgeodesic.reservations.domain.Reservation;
import com.nullgeodesic.reservations.domain.RoomSummary;

public abstract class AbstractTest {

	protected abstract LocalDate startDate();
	protected abstract LocalDate endDate();
	protected abstract List<Reservation> reservations();

	@Test
	public void test() {
		showAllPaths(reservations(), startDate(), endDate());
	}

	private static void showAllPaths(final List<Reservation> reservations, LocalDate startDate, LocalDate endDate) {
		final ReservationGraph graph = new ReservationGraph(startDate, endDate, reservations);
		int roomCount = 0;
		int totalUnfilledDays = 0;
		for(final RoomSummary roomSummary : graph.findAllPaths()) {
			roomCount++;
			totalUnfilledDays += roomSummary.unfilledDays;
			for(final Reservation reservation : roomSummary.roomReservations) {
				System.out.println(reservation.format());
			}
			System.out.println("-------------------------");
		}
		System.out.println("Total rooms needed: " + roomCount);
		final int totalRoomDays = (int) DAYS.between(startDate, endDate) * roomCount;
		System.out.println(format("Total unfilled room-days: %d of total room-days %d - %.1f%% efficiency", totalUnfilledDays, totalRoomDays, 100.0*totalUnfilledDays/totalRoomDays));
	}

}
