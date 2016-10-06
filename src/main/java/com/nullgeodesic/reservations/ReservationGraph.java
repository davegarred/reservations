package com.nullgeodesic.reservations;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
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
			final Vertex arrivalVertex = new Vertex(reservation.customer, reservation.arrival);
			final Vertex departureVertex = new Vertex(reservation.customer, reservation.departure);
			currentArrivalVertices.add(arrivalVertex);
			currentDepartureVertices.add(departureVertex);

			this.graph.addVertex(arrivalVertex);
			this.graph.addVertex(departureVertex);
			this.graph.addEdge(arrivalVertex, departureVertex, 0);
		}

		for(final Entry<LocalDate, List<Vertex>> departureEntry : departureVertices.entrySet()) {
			final LocalDate depDate = departureEntry.getKey();
			for(final Entry<LocalDate, List<Vertex>> arrivalEntry : arrivalVertices.entrySet()) {
				final LocalDate arrDate = arrivalEntry.getKey();
				if(!depDate.isAfter(arrDate)) {
					final int cost = (int) ChronoUnit.DAYS.between(depDate, arrDate);
					for(final Vertex depVertex : departureEntry.getValue()) {
						for(final Vertex arrVertex : arrivalEntry.getValue()) {
							this.graph.addEdge(depVertex, arrVertex, cost);
						}
					}
				}
			}
		}

		final Vertex startVertex = new Vertex("start", start);
		this.graph.addVertex(startVertex);
		for(final Vertex vertex : arrivalVertices.values().iterator().next()) {
			this.graph.addEdge(startVertex, vertex, 0);
		}
		final Vertex endVertex = new Vertex("end", end);
		this.graph.addVertex(endVertex);
		final Iterator<List<Vertex>> it = arrivalVertices.values().iterator();
		List<Vertex> lastVerticeList = null;
		while(it.hasNext()) {
			lastVerticeList = it.next();
		}
		for(final Vertex vertex : lastVerticeList) {
			this.graph.addEdge(vertex, endVertex, 0);
		}

		final DijkstraShortestPath<Vertex,Integer> path = new DijkstraShortestPath<>(this.graph, startVertex, endVertex);
		System.out.println(path.getPathLength());
		for(final Vertex vertex : path.getPath().getVertexList()) {
			System.out.println(vertex);
		}
	}


}
