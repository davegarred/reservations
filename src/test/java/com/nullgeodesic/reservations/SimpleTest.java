package com.nullgeodesic.reservations;

import static java.time.Month.NOVEMBER;

import java.time.LocalDate;
import java.util.List;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Test;

public class SimpleTest {


	@Test
	public void test() {
		final List<Reservation> reservations = new ReservationList();


		final ReservationGraph graph = new ReservationGraph(LocalDate.of(2016, NOVEMBER, 1), LocalDate.of(2016, NOVEMBER, 30), reservations);
		DijkstraShortestPath<Vertex, DefaultWeightedEdge> path = null;
		path = graph.findPath();
		while(path.getPath() != null) {
			showPath(path);
			path = graph.removeFoundVertices(path).findPath();
		}
	}

	private static void showPath(DijkstraShortestPath<Vertex, DefaultWeightedEdge> path) {
		System.out.println(path.getPathLength());
		for(final Vertex vertex : path.getPath().getVertexList()) {
			System.out.println(vertex);
		}
	}


}
