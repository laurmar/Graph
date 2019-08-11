package edu.union.adt.graph;
public interface Graph<V> {


        // GETTERS AND SETTERS
        //--------------------------------------------------------------------------------------------
        /**
         * @return the number of vertices in the graph.
         */
         int numVertices();



        /**
         * @return the number of edges in the graph.
         */
        int numEdges();

        //GETTERS AND SETTERS COMPLICATED
        //----------------------------------------------------------------------------------------------

        /**
         * Gets the number of vertices connected by edges from a given
         * vertex.  If the given vertex is not in the graph, throws a
         * RuntimeException.
         *
         * @param vertex the vertex whose degree we want.
         * @return the degree of vertex 'vertex'
         */
         int degree(V vertex);



        /**
         * @return an iterable collection for the set of vertices of
         * the graph.
         */
         Iterable<V> getVertices();



        /**
         * Gets the vertices adjacent to a given vertex.  A vertex y is
         * "adjacent to" vertex x if there is an edge (x, y) in the graph.
         * Because edges are directed, if (x, y) is an edge but (y, x) is
         * not an edge, we would say that y is adjacent to x but that x is
         * NOT adjacent to y.
         *
         * @param from the source vertex
         * @return an iterable collection for the set of vertices that are
         * the destinations of edges for which 'from' is the source
         * vertex.  If 'from' is not a vertex in the graph, returns an
         * empty iterator.
         */
        public Iterable<V> adjacentTo(V from);




        //ADDITIONS
        //--------------------------------------------------------------------------------------------
        /**
         * Adds a directed edge between two vertices.
         * If there is already an edge
         * between the given vertices, does nothing.
         *
         * If either (or both)
         * of the given vertices does not exist, it is added to the
         * graph before the edge is created between them.
         *
         * @param from the source vertex for the added edge
         * @param to the destination vertex for the added edge
         */
        void addEdge(V from, V to);




        /**
         * Adds a vertex to the graph.  If the vertex already exists in
         * the graph, does nothing.  If the vertex does not exist, it is
         * added to the graph, with no edges connected to it.
         *
         * @param vertex the vertex to add
         */
        void addVertex(V vertex);








        //BOOLEAN METHODS
        //--------------------------------------------------------------------------------------------


        /**
         * Tells whether or not a vertex is in the graph.
         *
         * @param vertex a vertex
         * @return true iff 'vertex' is a vertex in the graph.
         */
         boolean contains(V vertex);



        /**
         * Tells whether an edge exists in the graph.
         *
         * @param from the source vertex
         * @param to the destination vertex
         *
         * @return true iff there is an edge from the source vertex to the
         * destination vertex in the graph.  If either of the given
         * vertices are not vertices in the graph, then there is no edge
         * between them.
         */
        boolean hasEdge(V from, V to);




        //PRINTING
        //--------------------------------------------------------------------------------------------
        /**
         * Gives a string representation of the graph.  The representation
         * is a series of lines, one for each vertex in the graph.  On
         * each line, the vertex is shown followed by ": " and then
         * followed by a list of the vertices adjacent to that vertex.  In
         * this list of vertices, the vertices are separated by ", ".  For
         * example, for a graph with String vertices "A", "B", and "C", we
         * might have the following string representation:
         *
         * <PRE>
         * A: A, B
         * B:
         * C: A, B
         * </PRE>
         *
         * This representation would indicate that the following edges are
         * in the graph: (A, A), (A, B), (C, A), (C, B) and that B has no
         * adjacent vertices.
         *
         * Note: there are no extraneous spaces in the output.  So, if we
         * replace each space with '*', the above representation would be:
         *
         * <PRE>
         * A:*A,*B
         * B:
         * C:*A,*B
         * </PRE>
         *
         * @return the string representation of the graph
         */
         String toString();



        /**
         * Checks whether the graphs are equivalent taking into account:
         * - emptiness
         * - the number of verticies and edges
         * - the list of verticies
         * - the list of edges
         * @param other another object to compare to
         * @return whether the two objects are equivalent
         */

        boolean equals(Object other);






    /**
     * Tells whether the graph is empty.
     *
     * @return true iff the graph is empty. A graph is empty if it has
     * no vertices and no edges.
     */
    public boolean isEmpty();

    /**
     * Removes and vertex from the graph.  Also removes any edges
     * connecting from the edge or to the edge.
     *
     * <p>Postconditions:
     *
     * <p>If toRemove was in the graph:
     * <ul>
     * <li>numVertices = numVertices' - 1
     * <li>toRemove is no longer a vertex in the graph
     * <li>for all vertices v: toRemove is not in adjacentTo(v)
     * </ul>
     *
     * @param toRemove the vertex to remove.
     */
    public void removeVertex(V toRemove);

    /**
     * Removes an edge from the graph.
     *
     * <p>Postcondition: If from and to were in the graph and (from,
     * to) was an edge in the graph, then numEdges = numEdges' - 1
     */
    public void removeEdge(V from, V to);

    /**
     * Tells whether there is a path connecting two given vertices.  A
     * path exists from vertex A to vertex B iff A and B are in the
     * graph and there exists a sequence x_1, x_2, ..., x_n where:
     *
     * <ul>
     * <li>x_1 = A
     * <li>x_n = B
     * <li>for all i from 1 to n-1, (x_i, x_{i+1}) is an edge in the graph.
     * </ul>
     *
     * It therefore follows that, if vertex A is in the graph, there
     * is a path from A to A.
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return true iff there is a path from 'from' to 'to' in the graph.
     */
    public boolean hasPath(V from, V to);

    /**
     * Gets the length of the shortest path connecting two given
     * vertices.  The length of a path is the number of edges in the
     * path.
     *
     * <ol>
     * <li>If from = to, shortest path has length 0
     * <li>Otherwise, shortest path length is length of the shortest
     * possible path connecting from to to.
     * </ol>
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return the length of the shortest path from 'from' to 'to' in
     * the graph.  If there is no path, returns Integer.MAX_VALUE
     */
    public int pathLength(V from, V to);

    /**
     * Returns the vertices along the shortest path connecting two
     * given vertices.  The vertices should be given in the order x_1,
     * x_2, x_3, ..., x_n, where:
     *
     * <ol>
     * <li>x_1 = from
     * <li>x_n = to
     * <li>for all i from 1 to n-1: (x_i, x_{i+1}) is an edge in the graph.
     * </ol>
     *
     * @param from the source vertex
     * @param to the destination vertex
     * @return an Iterable collection of vertices along the shortest
     * path from 'from' to 'to'.  The Iterable should include the
     * source and destination vertices.
     */
    public Iterable<V> getPath(V from, V to);


}