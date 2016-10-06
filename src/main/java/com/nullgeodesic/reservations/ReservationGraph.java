package com.nullgeodesic.reservations;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.jgrapht.EdgeFactory;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class ReservationGraph {

	final SimpleDirectedWeightedGraph<Vertex,DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<>(new EdgeFactory<Vertex,DefaultWeightedEdge>() {
		@Override
		public DefaultWeightedEdge createEdge(Vertex sourceVertex, Vertex targetVertex) {
			return new DefaultWeightedEdge();
		}
	});

	public ReservationGraph(LocalDate start, LocalDate end, List<Reservation> reservations) {

		final Map<LocalDate,List<Vertex>> arrivalVertices = new TreeMap<>();
		final Set<Vertex> departureVertices = new HashSet<>();

		final Vertex startVertex = new Vertex("start", start, null);
		this.graph.addVertex(startVertex);
		final Vertex endVertex = new Vertex("end", end, null);
		this.graph.addVertex(endVertex);
		
		for(final Reservation reservation : reservations) {
			List<Vertex> currentArrivalVertices = arrivalVertices.get(reservation.arrival);
//			List<Vertex> currentDepartureVertices = departureVertices.get(reservation.departure);
			if(currentArrivalVertices == null) {
				currentArrivalVertices = new ArrayList<>();
				arrivalVertices.put(reservation.arrival, currentArrivalVertices);
			}
//			if(currentDepartureVertices == null) {
//				currentDepartureVertices = new ArrayList<>();
//				departureVertices.put(reservation.arrival, currentDepartureVertices);
//			}
			final Vertex arrivalVertex = new Vertex(reservation.customer, reservation.arrival, reservation);
			final Vertex departureVertex = new Vertex(reservation.customer, reservation.departure, reservation);
			currentArrivalVertices.add(arrivalVertex);
			departureVertices.add(departureVertex);
//			currentDepartureVertices.add(departureVertex);

			this.graph.addVertex(arrivalVertex);
			this.graph.addVertex(departureVertex);
			addEdge(arrivalVertex, departureVertex);
			addEdge(startVertex, arrivalVertex);
			addEdge(departureVertex, endVertex);
		}

		for(final Vertex departingVertex : departureVertices) {
			final LocalDate depDate = departingVertex.date;
			for(final Entry<LocalDate, List<Vertex>> arrivalEntry : arrivalVertices.entrySet()) {
				final LocalDate arrDate = arrivalEntry.getKey();
				if(!depDate.isAfter(arrDate)) {
					for(final Vertex arrVertex : arrivalEntry.getValue()) {
						if(departingVertex.reservation != arrVertex.reservation) {
							addEdge(departingVertex, arrVertex);
						}
					}
				}
			}
		}


		final DijkstraShortestPath<Vertex,DefaultWeightedEdge> path = new DijkstraShortestPath<>(this.graph, startVertex, endVertex);
		System.out.println(this.graph.vertexSet());
		System.out.println(this.graph.edgeSet());
		System.out.println(path.getPathLength());
		for(final Vertex vertex : path.getPath().getVertexList()) {
			System.out.println(vertex);
		}
		for(final DefaultWeightedEdge edge : path.getPath().getEdgeList()) {
			System.out.println(this.graph.getEdgeWeight(edge));
		}
	}

	private void addEdge(Vertex sourceVertex, Vertex targetVertex) {
		DefaultWeightedEdge edge = this.graph.addEdge(sourceVertex, targetVertex);
		this.graph.setEdgeWeight(edge, edgeWeight(sourceVertex, targetVertex));
	}
	
	private static double edgeWeight(Vertex sourceVertex, Vertex targetVertex) {
		if(sourceVertex.reservation !=null && sourceVertex.reservation.equals(targetVertex.reservation)) {
			return 0.0;
		}
		long days = ChronoUnit.DAYS.between(sourceVertex.date, targetVertex.date);
		return days;
	}


}
