package com.nullgeodesic.reservations;

import static com.nullgeodesic.reservations.Customer.END;
import static com.nullgeodesic.reservations.Customer.START;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class ReservationGraph {

	final SimpleDirectedWeightedGraph<Vertex,DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
	final Vertex startVertex;
	final Vertex endVertex;

	public ReservationGraph(LocalDate start, LocalDate end, List<Reservation> reservations) {

		final Map<LocalDate,List<Vertex>> arrivalVertices = new TreeMap<>();
		final Set<Vertex> departureVertices = new HashSet<>();

		this.startVertex = new Vertex(START, start, null);
		this.graph.addVertex(this.startVertex);
		this.endVertex = new Vertex(END, end, null);
		this.graph.addVertex(this.endVertex);

		for(final Reservation reservation : reservations) {
			List<Vertex> currentArrivalVertices = arrivalVertices.get(reservation.arrival);
			if(currentArrivalVertices == null) {
				currentArrivalVertices = new ArrayList<>();
				arrivalVertices.put(reservation.arrival, currentArrivalVertices);
			}
			final Vertex arrivalVertex = new Vertex(reservation.customer, reservation.arrival, reservation);
			final Vertex departureVertex = new Vertex(reservation.customer, reservation.departure, reservation);
			currentArrivalVertices.add(arrivalVertex);
			departureVertices.add(departureVertex);

			this.graph.addVertex(arrivalVertex);
			this.graph.addVertex(departureVertex);
			addEdge(arrivalVertex, departureVertex);
			addEdge(this.startVertex, arrivalVertex);
			addEdge(departureVertex, this.endVertex);
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
	}

	public DijkstraShortestPath<Vertex, DefaultWeightedEdge> findPath() {
		return new DijkstraShortestPath<>(this.graph, this.startVertex, this.endVertex);
	}

	public ReservationGraph removeFoundVertices(DijkstraShortestPath<Vertex, DefaultWeightedEdge> path) {
		if(path == null) {
			return this;
		}
		for(final DefaultWeightedEdge edge : path.getPathEdgeList()) {
			this.graph.removeEdge(edge);
		}
		for(final Vertex vertex : path.getPath().getVertexList()) {
			if(vertex.reservation != null) {
				this.graph.removeVertex(vertex);
			}
		}
		return this;
	}

	private void addEdge(Vertex sourceVertex, Vertex targetVertex) {
		final DefaultWeightedEdge edge = this.graph.addEdge(sourceVertex, targetVertex);
		this.graph.setEdgeWeight(edge, edgeWeight(sourceVertex, targetVertex));
	}

	private static double edgeWeight(Vertex sourceVertex, Vertex targetVertex) {
		if(sourceVertex.reservation !=null && sourceVertex.reservation.equals(targetVertex.reservation)) {
			return 0.0;
		}
		final long days = ChronoUnit.DAYS.between(sourceVertex.date, targetVertex.date);
		return days;
	}


}
