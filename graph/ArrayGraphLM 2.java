package edu.union.adt.graph;
import java.util.ArrayList;

/**
 * A graph that establishes connections (edges) between objects of
 * (parameterized) type V (vertices).  The edges are directed.  An
 * undirected edge between u and v can be simulated by two edges: (u,
 * v) and (v, u).
 *
 * The API is based on one from
 *     http://introcs.cs.princeton.edu/java/home/
 *
 * Some method names have been changed, and the Graph type is
 * parameterized with a vertex type V instead of assuming String
 * vertices.
 *
 * @author Aaron G. Cass
 * @version 1
 *
 * Editted by Laura Marlin, January 18, 2019
 *
 * I affirm that I have carried out my academic endeavors with full academic honesty.
 */
public class ArrayGraphLM<V> implements Graph<V> {


    private static final int INITIAL =10;

    private boolean [][] edges;
    private Object[] labels;







    //CONSTRUCTOR
    //--------------------------------------------------------------------------------------------
    /**
     * Create an empty graph.
     */
    public ArrayGraphLM()
    {
        edges= new boolean[INITIAL][INITIAL];
        labels= (V[]) new Object[INITIAL];

    }


    //-------- added on ---------------------
    /*
     * Create an empty graph of a given size.
     */
   public ArrayGraphLM(int newCount)
    {
        edges= new boolean[newCount][newCount];
        labels= (V[]) new Object[newCount];

    }








    // GETTERS AND SETTERS
    //--------------------------------------------------------------------------------------------
    /**
     * @return the number of vertices in the graph.
     */
    public int numVertices()
    {
        int count=0;
        for(int i=0; i<labels.length; i++){
            if(labels[i]!=null){
                count++;
            }
        }
    return count;
    }


    /**
     * @param vertex the vertex we want to find the location of
     * @return the index that corresponds to the vertex's location
     */
    private int getVertexSpot(V vertex){
        int i=0;
        int index=0;

        while(i<labels.length && !labels[i].equals(vertex)){
            index++;
            i++;
        }
        return index;
    }




    /**
     * @return the number of edges in the graph.
     */
    public int numEdges()
    {
        int count=0;
        for(int from=0; from<labels.length; from++) {
            for (int to = 0; to < labels.length; to++) {
                V rowVertex= (V) labels[from];
                V columnVertex= (V) labels[to];

                if (hasEdge(rowVertex, columnVertex)) {
                    count++;
                }
            }
        }


        return count;
    }

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
    public int degree(V vertex)
    {

        if(!contains(vertex)){
            throw new RuntimeException("Vertex does not exist");
        }
        else{
            int count=0;
            for(int i=0; i<labels.length; i++){
                if(labels[i]!=null){
                    V possibleConnect= (V) labels[i];
                    if(hasEdge(vertex, possibleConnect)){
                        count++;
                    }
                }
            }

            return count;
        }


    }




    /**
     * @return an iterable collection for the set of vertices of
     * the graph.
     */
    public Iterable<V> getVertices()
    {

        ArrayList<V> verticies= new ArrayList<V>();
        for(int i=0; i<labels.length; i++){
          V newItem= (V) labels[i];
          if(newItem !=null){
            verticies.add(newItem);
           }
      }

        return verticies;
    }





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
    public Iterable<V> adjacentTo(V from)
    {
        ArrayList<V> verticies= new ArrayList<V>();

        for(int i=0; i<labels.length; i++){
            if (labels[i]!=null){
                V possibleConnect= (V) labels[i];
                if(hasEdge(from, possibleConnect)){
                    verticies.add(possibleConnect);
                }
            }

        }
        return verticies;

    }










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
    public void addEdge(V from, V to)
    {
        //check if the verticies exist
        if(!contains(from)){
            addVertex(from);
        }
        if(!contains(to)){
            addVertex(to);
        }

        // if there isnt an edge add one
        if(!hasEdge(from, to)){
            int fromIndex= getVertexSpot(from);
            int toIndex= getVertexSpot(to);
            edges[fromIndex][toIndex]= true;
        }

    }




    /**
     * Adds a vertex to the graph.  If the vertex already exists in
     * the graph, does nothing.  If the vertex does not exist, it is
     * added to the graph, with no edges connected to it.
     *
     * @param vertex the vertex to add
     */
    public void addVertex(V vertex)
    {
        if(!contains(vertex)){
            if(isAtMax()){
                expandGraph();
            }
            int lastSpot= numVertices();
            labels[lastSpot]= vertex;

        }

    }







    //BOOLEAN METHODS
    //--------------------------------------------------------------------------------------------


    /**
     * @return whether there is any space in the labels
     */
    private boolean isAtMax(){
        return labels.length == numVertices();
    }

    /**
     * Tells whether or not a vertex is in the graph.
     *
     * @param vertex a vertex
     * @return true iff 'vertex' is a vertex in the graph.
     */
    public boolean contains(V vertex)
    {

        for(int i=0; i<labels.length; i++){
            V item =(V) labels[i];
            if(item!=null){
                if (item.equals(vertex)) {
                    return true;
                }
            }
        }

            return false;



    }



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
    public boolean hasEdge(V from, V to)
    {
        if(contains(from) && contains(to)) {
            int fromIndex = getVertexSpot(from);
            int toIndex = getVertexSpot(to);
            return edges[fromIndex][toIndex];
        }
        return false;

    }


    /**
     * @return whether there is anything in the graph
     */
   /* private boolean isEmpty(){
        return numVertices()==0 && numEdges()==0;
    }*/








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
    public String toString()
    {

        String answer="";
        ArrayList<V> listOfVertices= (ArrayList<V>) getVertices();
        int numV= numVertices();

        for(int i=0; i<numV; i++){
            V currentItem= listOfVertices.get(i);
            ArrayList<V> listOfFriends= (ArrayList<V>) adjacentTo(currentItem);
            int numFriends= listOfFriends.size();
            answer= answer+ currentItem +":";

            if(numFriends>0){
                answer= answer+ edgeString(numFriends, listOfFriends);
            }
            else{
                answer= answer+"\n";
            }



        }

        return answer;
    }


    /**
     * This provides the list of edges from a certain vertex in a readable format
     * @param numFriends degree from a specific vertex
     * @param listOfFriends list of vertecies adjacent to the current vertex
     * @return the list of edges from a given vertex in a string format
     */
    private String edgeString(int numFriends, ArrayList<V> listOfFriends){
        String answer="";
        for(int j=0; j<numFriends; j++){
            V currentFriend= listOfFriends.get(j);

            if(j!= numFriends-1){
                answer= answer+" "+currentFriend+",";
            }
            else{
                answer= answer+" "+currentFriend+"\n";
            }
        }

        return answer;


    }


    /**
     * This expands the graph when there is not enough space in the given graph
     */
    private void expandGraph(){
        if(labels!=null) {
            int currentSize = labels.length;

            ArrayGraphLM<V> newGraph = new ArrayGraphLM<V>(currentSize * 2 + 1);

            //ADDING IN THE LABELS
            for(int i=0; i<labels.length; i++){
                if(labels[i] != null){
                    newGraph.addVertex((V) labels[i]);
                }
            }


            //ADDING IN THE EDGES
            for (int row = 0; row < labels.length; row++) {
                for (int column = 0; column < labels.length; column++) {
                    V rowVertex = (V) labels[row];
                    V columnVertex = (V) labels[column];

                    if (hasEdge(rowVertex, columnVertex)) {
                        newGraph.addEdge(rowVertex, columnVertex);
                    }
                }
            }

            this.labels = newGraph.labels;
            this.edges = newGraph.edges;
        }



    }


    /**
     * Checks whether the graphs are equivalent taking into account:
     * - emptiness
     * - the number of verticies and edges
     * - the list of verticies
     * - the list of edges
     * @param other another object to compare to
     * @return whether the two objects are equivalent
     */

    @Override
    public boolean equals(Object other){

        if(other instanceof ArrayGraphLM) {
            ArrayGraphLM otherGraph = (ArrayGraphLM) other;
            if (isEmpty() && otherGraph.isEmpty()) {
                return true;
            }
            else {
                int thisVertexCount = numVertices();
                int otherVertexCount = otherGraph.numVertices();

                int thisEdgeCount = numEdges();
                int otherEdgeCount = otherGraph.numEdges();

                //count check
                if (thisEdgeCount == otherEdgeCount && thisVertexCount == otherVertexCount) {

                    // checking if they have all the same verticies
                    for(int i=0; i< numVertices(); i++){
                        if (!otherGraph.contains(labels[i])){
                            return false;
                        }
                    }

                    // checking if their edges match up
                    if(!edgeListFormation().equals(otherGraph.edgeListFormation())){
                        return false;
                    }

                    return true;
                }
            }
        }
        return false;

    }


    /**
     * This forms a list of edges that a graph contains
     * @return a list of edges
     */
    private ArrayList<String> edgeListFormation(){
        ArrayList<String> edgeList= new ArrayList<String>();
        for (int row = 0; row < labels.length; row++) {
            for (int column = 0; column < labels.length; column++) {
                V rowVertex = (V) labels[row];
                V columnVertex = (V) labels[column];

                if (hasEdge(rowVertex, columnVertex)) {
                    edgeList.add(rowVertex+" "+columnVertex);
                }
            }
        }
        return edgeList;
    }

//-----------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------




    // BOOLEAN METHODS
    //-----------------------------------------------------------------------------------------------------
    /**
     * Tells whether the graph is empty.
     *
     * @return true iff the graph is empty. A graph is empty if it has
     * no vertices and no edges.
     */
    public boolean isEmpty(){
        return false;
    }




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
    public boolean hasPath(V from, V to){
        return false;
    }









    // REMOVING METHODS
    //-----------------------------------------------------------------------------------------------------
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
    public void removeVertex(V toRemove){

    }






    /**
     * Removes an edge from the graph.
     *
     * <p>Postcondition: If from and to were in the graph and (from,
     * to) was an edge in the graph, then numEdges = numEdges' - 1
     */
    public void removeEdge(V from, V to){

    }









    //GETTERS AND SETTERS
    //-----------------------------------------------------------------------------------------------------
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
    public int pathLength(V from, V to){
        return -1;
    }







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
    public Iterable<V> getPath(V from, V to){
        Iterable<V> fake= new ArrayList<V>();
        return fake;

    }



    public static <V> void depthFirstRecurse(ArrayGraphLM <V> g, V currentVertex, boolean[] marked){

        ArrayList<V> connections= (ArrayList<V>) g.adjacentTo(currentVertex);//neighbors(currentIndex);
        int i;

       /* int currentIndex= getVertexSpot(currentVertex);
        marked[currentIndex]= true;
        System.out.println(currentVertex);//g.getLabel(currentVertex));*/

       /* for(V nextNeighbor: connections)
        {
            V nextN= (V) nextNeighbor;
            int neighIndex= getVertexSpot(nextN);
            if(!marked[neighIndex])
                depthFirstRecurse(g, nextN, marked);

        }*/
    }


    public static <V> void depthFirstPrint(ArrayGraphLM<V> g, V startVertex)
    {
        boolean[] marked= new boolean[g.numVertices()];//size()];
        depthFirstRecurse(g, startVertex, marked);
    }




    public static void main (String[] args){
        ArrayGraphLM<String> g= new ArrayGraphLM<String>();
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        System.out.println(g);
        g.depthFirstPrint(g, "A");
  
    }



}
