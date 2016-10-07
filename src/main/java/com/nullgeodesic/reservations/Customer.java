package com.nullgeodesic.reservations;

import java.util.Arrays;
import java.util.List;

public class Customer extends AbstractObject {

	public final String name;

	public Customer(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public static final Customer PENZIAS = new Customer("Arno Penzias");
	public static final Customer WILSON = new Customer("Robert Wilson");
	public static final Customer FERMI = new Customer("Enrico Fermi");
	public static final Customer BOHR = new Customer("Niels Bohr");
	public static final Customer FARADAY = new Customer("Michael Farady");
	public static final Customer MAXWELL = new Customer("James Maxwell");
	public static final Customer PLANCK = new Customer("Max Planck");
	public static final Customer HEISENBERG = new Customer("Werner Heisenberg");
	public static final Customer DIRAC = new Customer("Paul Dirac");
	public static final Customer PAULI = new Customer("Wolfgang Pauli");
	public static final Customer MARCONI = new Customer("Guglielmo Marconi");
	public static final Customer BORN = new Customer("Max Born");
	public static final Customer BOLTZMANN = new Customer("Ludwig Boltzmann");
	public static final Customer SOMMERFELD = new Customer("Arnold Sommerfeld");
	public static final Customer JOULE = new Customer("J. Joule");
	public static final Customer MICHELSON = new Customer("Albert Michelson");
	public static final Customer LORENTZ = new Customer("Hendrik Lorentz");
	public static final Customer WATSON = new Customer("James Watson");
	public static final Customer CRICK = new Customer("Francis Crick");

	public static final Customer START = new Customer("GRAPH_START_VERTEX");
	public static final Customer END = new Customer("GRAPH_END_VERTEX");

	public static final List<Customer> ALL_CUSTOMERS = Arrays.asList(new Customer[] {
			PENZIAS,
			WILSON,
			FERMI,
			BOHR,
			FARADAY,
			MAXWELL,
			PLANCK,
			HEISENBERG,
			DIRAC,
			PAULI,
			MARCONI,
			BORN,
			BOLTZMANN,
			SOMMERFELD,
			JOULE,
			MICHELSON,
			LORENTZ,
			WATSON,
			CRICK
	});
}
