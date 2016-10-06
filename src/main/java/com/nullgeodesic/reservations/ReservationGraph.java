package com.nullgeodesic.reservations;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;

public class ReservationGraph {

	final DirectedAcyclicGraph<Vertex,Integer> graph = new DirectedAcyclicGraph<>(Integer.class);

	public ReservationGraph(LocalDate start, LocalDate end, List<Reservation> reservations) {

		final Map<LocalDate,List<Vertex>> arrivalVertices = new TreeMap<>();
		final Map<LocalDate,List<Vertex>> departureVertices = new TreeMap<>();

		final Vertex startVertex = new Vertex("start", start, null);
		this.graph.addVertex(startVertex);
		final Vertex endVertex = new Vertex("end", end, null);
		this.graph.addVertex(endVertex);
		
		for(final Reservation reservation : reservations) {
			List<Vertex> currentArrivalVertices = arrivalVertices.get(reservation.arrival);
			List<Vertex> currentDepartureVertices = departureVertices.get(reservation.departure);
			if(currentArrivalVertices == null) {
				currentArrivalVertices = new ArrayList<>();
				arrivalVertices.put(reservation.arrival, currentArrivalVertices);
			}
			if(currentDepartureVertices == null) {
				currentDepartureVertices = new ArrayList<>();
				departureVertices.put(reservation.arrival, currentDepartureVertices);
			}
			final Vertex arrivalVertex = new Vertex(reservation.customer, reservation.arrival, reservation);
			final Vertex departureVertex = new Vertex(reservation.customer, reservation.departure, reservation);
			currentArrivalVertices.add(arrivalVertex);
			currentDepartureVertices.add(departureVertex);

			this.graph.addVertex(arrivalVertex);
			this.graph.addVertex(departureVertex);
			freeEdge(arrivalVertex, departureVertex);
			
			expensiveEdge(startVertex, arrivalVertex);
			expensiveEdge(departureVertex, endVertex);
		}

		for(final Entry<LocalDate, List<Vertex>> departureEntry : departureVertices.entrySet()) {
			final LocalDate depDate = departureEntry.getKey();
			for(final Entry<LocalDate, List<Vertex>> arrivalEntry : arrivalVertices.entrySet()) {
				final LocalDate arrDate = arrivalEntry.getKey();
				if(!depDate.isAfter(arrDate)) {
					for(final Vertex depVertex : departureEntry.getValue()) {
						for(final Vertex arrVertex : arrivalEntry.getValue()) {
							if(depVertex.reservation != arrVertex.reservation) {
								expensiveEdge(depVertex, arrVertex);
							}
						}
					}
				}
			}
		}


		final DijkstraShortestPath<Vertex,Integer> path = new DijkstraShortestPath<>(this.graph, startVertex, endVertex);
		System.out.println(this.graph.vertexSet());
		System.out.println(this.graph.edgeSet());
		System.out.println(path.getPathLength());
		for(final Vertex vertex : path.getPath().getVertexList()) {
			System.out.println(vertex);
		}
	}


	private boolean expensiveEdge(final Vertex startVertex, final Vertex endVertex) {
		int startCost = (int) ChronoUnit.DAYS.between(startVertex.date, endVertex.date); 
		return addEdge(startVertex, endVertex, startCost);
	}

	private boolean freeEdge(final Vertex startVertex, final Vertex endVertex) {
		return addEdge(startVertex, endVertex, 0);
	}
	private boolean addEdge(final Vertex startVertex, final Vertex endVertex, int cost) {
		return this.graph.addEdge(startVertex, endVertex, cost);
	}

}
