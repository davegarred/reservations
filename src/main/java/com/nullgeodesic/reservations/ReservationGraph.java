package com.nullgeodesic.reservations;

import static com.nullgeodesic.reservations.domain.Customer.END;
import static com.nullgeodesic.reservations.domain.Customer.START;
import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.nullgeodesic.reservations.domain.Customer;
import com.nullgeodesic.reservations.domain.Reservation;
import com.nullgeodesic.reservations.domain.RoomSummary;
import com.nullgeodesic.reservations.domain.Vertex;

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

			addReservationToGraph(arrivalVertex, departureVertex);
		}

		addEdgesBetweenReservationsToGraph(arrivalVertices, departureVertices);
	}

	/**
	 * Each reservation will have an arrival and a departure vertex with an zero-weighted edge between them.
	 * Two additional edges are added from the graph start to arrivalVertex and from the departureVertex to
	 * the graph end, these will be weighted with the number of days between the respective dates.
	 * @param arrivalVertex
	 * @param departureVertex
	 */
	private void addReservationToGraph(final Vertex arrivalVertex, final Vertex departureVertex) {
		this.graph.addVertex(arrivalVertex);
		this.graph.addVertex(departureVertex);
		addEdge(arrivalVertex, departureVertex);
		addEdge(this.startVertex, arrivalVertex);
		addEdge(departureVertex, this.endVertex);
	}

	/**
	 * Add an edge between every guest departure and every guest arrival that is on or after the departure date.
	 * The edge will be weighted with the number of days between the departure and arrival dates.
	 */
	private void addEdgesBetweenReservationsToGraph(final Map<LocalDate, List<Vertex>> arrivalVertices,
	        final Set<Vertex> departureVertices) {
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

	/**
	 * Iterates through the graph, finding the shortest path, removing it and continuing until all paths are found.
	 * @return a room-by-room collection of lists of room reservations
	 */
	public Collection<RoomSummary> findAllPaths() {
		DijkstraShortestPath<Vertex, DefaultWeightedEdge> path = this.findPath();
		Customer lastVertexCustomer = null;
		final Collection<RoomSummary> completeReservationList = new ArrayList<>();
		while(path.getPath() != null) {
			final ArrayList<Reservation> roomReservationList = new ArrayList<>();
			completeReservationList.add(new RoomSummary(roomReservationList, (int) path.getPathLength()));
			for(final Vertex vertex : path.getPath().getVertexList()) {
				if(vertex.customer == lastVertexCustomer) {
					lastVertexCustomer = null;
					roomReservationList.add(vertex.reservation);
				} else {
					lastVertexCustomer = vertex.customer;
				}
			}
			path = this.removeFoundVertices(path).findPath();
		}
		return completeReservationList;
	}

	/**
	 * I'm too lazy to write my own shortest path algorithm.
	 */
	public DijkstraShortestPath<Vertex, DefaultWeightedEdge> findPath() {
		return new DijkstraShortestPath<>(this.graph, this.startVertex, this.endVertex);
	}

	/**
	 * After a path is found and "recorded", we can remove the vertices and edges to prepare to recalculate.
	 */
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

	/**
	 * If the source and target vertices share the same reservation then we know this is a single stay
	 * and set the weight to zero, otherwise the weight should be the number of days between the two dates.
	 */
	private static double edgeWeight(Vertex sourceVertex, Vertex targetVertex) {
		if(sourceVertex.reservation != null && sourceVertex.reservation.equals(targetVertex.reservation)) {
			return 0.0;
		}
		final long days = DAYS.between(sourceVertex.date, targetVertex.date);
		return days;
	}

}
