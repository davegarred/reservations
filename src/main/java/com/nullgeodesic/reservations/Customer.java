package com.nullgeodesic.reservations;

public class Customer extends AbstractObject {

	public static final Customer FERMI = new Customer("Enrico Fermi");
	public static final Customer BOHR = new Customer("Niels Bohr");
	public static final Customer FARADAY = new Customer("Michael Farady");
	public static final Customer MAXWELL = new Customer("James Maxwell");
	public static final Customer PLANCK = new Customer("Max Planck");


	public static final Customer START = new Customer("GRAPH_START_VERTEX");
	public static final Customer END = new Customer("GRAPH_END_VERTEX");

	public final String name;

	public Customer(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
