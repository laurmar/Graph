package edu.union.adt.graph;
public class GraphFactory{

     public static<V> Graph<V> createGraph(){  
         return new ArrayGraphLM<V>();
     }
}
