# Hotel Reservation problem

### The problem
From a hotel reservation application, determine if a room is available on any given night.

### Solution  
Maintain a list of reservations that can be used to build a weighted graph and solve for the shortest path. 
 - vertices for start-vertex and end-vertex representing the initial and final dates that reservations are taken
 - all other vertices represent the arrival and departure dates of a customer
 - each reservation has a zero-weight edge between arrival and departure vertices
 - each reservation has an edge from the graph start-vertex to arrival, and another from departure to the graph end-vertex, that is weighted based on the days between the two
 - edges are added between every reservation departure and all reservation arrivals on or after that date, these edges are weighted based on the days between the two dates
