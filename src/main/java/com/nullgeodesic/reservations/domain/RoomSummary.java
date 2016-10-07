package com.nullgeodesic.reservations.domain;

import java.util.List;

public class RoomSummary extends AbstractObject {

	public List<Reservation> roomReservations;
	public int unfilledDays;

	public RoomSummary(List<Reservation> roomReservations, int unfilledDays) {
		this.roomReservations = roomReservations;
		this.unfilledDays = unfilledDays;
	}

}
