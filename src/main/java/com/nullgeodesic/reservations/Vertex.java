package com.nullgeodesic.reservations;

import java.time.LocalDate;

public class Vertex {

	public final String customer;
	public final LocalDate date;

	public Vertex(String customer, LocalDate date) {
		this.customer = customer;
		this.date = date;
	}

	@Override
	public String toString() {
		return this.customer + " - " + this.date + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.customer == null) ? 0 : this.customer.hashCode());
		result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());
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
		final Vertex other = (Vertex) obj;
		if(this.customer == null) {
			if(other.customer != null) {
				return false;
			}
		} else if(!this.customer.equals(other.customer)) {
			return false;
		}
		if(this.date == null) {
			if(other.date != null) {
				return false;
			}
		} else if(!this.date.equals(other.date)) {
			return false;
		}
		return true;
	}

}
