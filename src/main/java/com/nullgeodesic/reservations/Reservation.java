package com.nullgeodesic.reservations;

import java.time.LocalDate;
import java.time.Month;

public class Reservation {

	public final String customer;
	public final LocalDate arrival;
	public final LocalDate departure;

	public Reservation(String customer, LocalDate arrival, LocalDate departure) {
		this.customer = customer;
		this.arrival = arrival;
		this.departure = departure;
	}

	public static Reservation reservation(String customer, int arrivalYear, int arrivalMonth, int arrivalDay, int days) {
		final LocalDate arrival = LocalDate.of(arrivalYear, arrivalMonth, arrivalDay);
		final LocalDate departure = arrival.plusDays(days);
		return new Reservation(customer, arrival, departure);
	}
	public static Reservation reservation(Month arrivalMonth, int arrivalDay, int days, String customer) {
		final LocalDate now = LocalDate.now();
		final int currentYear = now.getYear();
		final LocalDate tentativeArrival = LocalDate.of(currentYear, arrivalMonth, arrivalDay);
		final LocalDate arrival = (tentativeArrival.isAfter(now))
				? tentativeArrival
				: LocalDate.of(currentYear + 1, arrivalMonth, arrivalDay);
		final LocalDate departure = arrival.plusDays(days);
		return new Reservation(customer, arrival, departure);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.arrival == null) ? 0 : this.arrival.hashCode());
		result = prime * result + ((this.customer == null) ? 0 : this.customer.hashCode());
		result = prime * result + ((this.departure == null) ? 0 : this.departure.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		final Reservation other = (Reservation) obj;
		if(this.arrival == null) {
			if(other.arrival != null) {
				return false;
			}
		} else if(!this.arrival.equals(other.arrival)) {
			return false;
		}
		if(this.customer == null) {
			if(other.customer != null) {
				return false;
			}
		} else if(!this.customer.equals(other.customer)) {
			return false;
		}
		if(this.departure == null) {
			if(other.departure != null) {
				return false;
			}
		} else if(!this.departure.equals(other.departure)) {
			return false;
		}
		return true;
	}

}
