package core.rdf4j;

import core.Graph;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

public class GraphRDF4J implements Graph {

    Model model;
    String namedGraph;
    SimpleValueFactory valueF;


    public GraphRDF4J(){
        model = new TreeModel();
        valueF = SimpleValueFactory.getInstance();
        namedGraph = "http://example/"+ UUID.randomUUID().toString();
    }

    public GraphRDF4J(String namedGraph){
        model = new TreeModel();
        valueF = SimpleValueFactory.getInstance();
        this.namedGraph = namedGraph;
    }

    @Override
    public String getNamedGraph() {
        return namedGraph;
    }

    @Override
    public void addTriple(String subject, String predicate, String object) {
        model.add(valueF.createIRI(subject), valueF.createIRI(predicate), valueF.createIRI(object) );
    }

    @Override
    public void addTripleLiteral(String subject, String predicate, String object) {
        model.add(valueF.createIRI(subject), valueF.createIRI(predicate), valueF.createLiteral(object) );
    }

    @Override
    public void deleteTriple(String subject, String predicate, String object) {
        model.remove(valueF.createIRI(subject), valueF.createIRI(predicate), valueF.createIRI(object));

    }

    @Override
    public List<Map<String, Object>> query(String sparql) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        SailRepository repository = new SailRepository(new MemoryStore());
        repository.init();

        try (RepositoryConnection conn = repository.getConnection()) {
            conn.add(model);
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, sparql);
            TupleQueryResult result = tupleQuery.evaluate();


            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                Map<String, Object> map = new HashMap<>();

                for (String name : bindingSet.getBindingNames()) {
                    map.put(name, bindingSet.getValue(name));
                }
                resultList.add(map);
            }
        }

        return resultList;
    }

    @Override
    public void write(String file) {
        try {
            Rio.write(model, new FileOutputStream(file), RDFFormat.TURTLE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
