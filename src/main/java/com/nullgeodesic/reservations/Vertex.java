package com.nullgeodesic.reservations;

import java.time.LocalDate;

public class Vertex {

	public final Customer customer;
	public final LocalDate date;
	public final Reservation reservation;

	public Vertex(Customer customer, LocalDate date, Reservation reservation) {
		this.customer = customer;
		this.date = date;
		this.reservation = reservation;
	}

	@Override
	public String toString() {
		return "Vertex [customer=" + this.customer + ", date=" + this.date + ", reservation=" + this.reservation + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.customer == null) ? 0 : this.customer.hashCode());
		result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());
		result = prime * result + ((this.reservation == null) ? 0 : this.reservation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Vertex other = (Vertex) obj;
		if (this.customer == null) {
			if (other.customer != null) {
				return false;
			}
		} else if (!this.customer.equals(other.customer)) {
			return false;
		}
		if (this.date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!this.date.equals(other.date)) {
			return false;
		}
		if (this.reservation == null) {
			if (other.reservation != null) {
				return false;
			}
		} else if (!this.reservation.equals(other.reservation)) {
			return false;
		}
		return true;
	}
}
