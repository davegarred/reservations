package com.nullgeodesic.reservations;

import static java.lang.String.format;
import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.List;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Test;

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
		DijkstraShortestPath<Vertex, DefaultWeightedEdge> path = null;
		path = graph.findPath();
		int roomCount = 0;
		int totalUnfilledDays = 0;
		while(path.getPath() != null) {
			roomCount++;
			totalUnfilledDays += path.getPathLength();
			showPath(path);
			path = graph.removeFoundVertices(path).findPath();
		}
		System.out.println("Total rooms needed: " + roomCount);
		final int totalRoomDays = (int) DAYS.between(startDate, endDate) * roomCount;
		System.out.println(format("Total unfilled room-days: %d of total room-days %d - %.1f%% efficiency", totalUnfilledDays, totalRoomDays, 100.0*totalUnfilledDays/totalRoomDays));
	}

	private static void showPath(DijkstraShortestPath<Vertex, DefaultWeightedEdge> path) {
		Customer lastVertexCustomer = null;
		for(final Vertex vertex : path.getPath().getVertexList()) {
			if(vertex.customer == lastVertexCustomer) {
				lastVertexCustomer = null;
				System.out.println(vertex.reservation.format());
			} else {
				lastVertexCustomer = vertex.customer;
			}
		}
		System.out.println("Unfilled days:" + (int)path.getPathLength());
		System.out.println("---------------------------------------");
	}


}
