package core;

public interface GraphStore {

    void saveGraph(Graph graph);

    void deleteGraph(Graph graph);

    void getGraph(String namedGraph);

    Boolean containsGraph(String namedGraph);

    void addTriple(String namedGraph, String subject, String predicate, String object);



}
