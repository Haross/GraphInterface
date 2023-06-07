package core.jena;

import core.Graph;
import core.GraphStore;
import org.apache.jena.query.Dataset;
import org.apache.jena.tdb.TDBFactory;

public class GraphStoreJena implements GraphStore {

    private Dataset dataset;

    public GraphStoreJena(){
        dataset = TDBFactory.createDataset();
    }


    @Override
    public void saveGraph(Graph graph) {

    }

    @Override
    public void deleteGraph(Graph graph) {

    }

    @Override
    public void getGraph(String namedGraph) {

    }

    @Override
    public Boolean containsGraph(String namedGraph) {
        return null;
    }

    @Override
    public void addTriple(String namedGraph, String subject, String predicate, String object) {

    }
}
