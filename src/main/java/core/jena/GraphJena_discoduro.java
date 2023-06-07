package core.jena;

import core.Graph;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

public class GraphJena_discoduro implements Graph {

    Model graph;
    String namedGraph;

    public GraphJena_discoduro(){
        graph = ModelFactory.createDefaultModel();
        namedGraph = "http://example/"+ UUID.randomUUID().toString();
    }

    public GraphJena_discoduro(String namedGraph){
        graph = ModelFactory.createDefaultModel();
        this.namedGraph = namedGraph;
    }

    @Override
    public String getNamedGraph() {
        return namedGraph;
    }

    @Override
    public void addTriple(String subject, String predicate, String object) {
        Resource r = graph.createResource(subject);
        r.addProperty(graph.createProperty(predicate), graph.createResource(object));
    }

    @Override
    public void addTripleLiteral(String subject, String predicate, String literal) {
        Resource r = graph.createResource(subject);
        r.addProperty(graph.createProperty(predicate), literal);
    }

    @Override
    public void deleteTriple(String subject, String predicate, String object) {
        graph.removeAll(new ResourceImpl(subject), new PropertyImpl(predicate), new ResourceImpl(object));
    }

    @Override
    public List<Map<String, Object>> query(String sparql) {
        List<Map<String, Object>> resultsList = new ArrayList<>();

        try (QueryExecution qExec = QueryExecutionFactory.create(QueryFactory.create(sparql), graph)) {
            ResultSetRewindable results = ResultSetFactory.copyResults(qExec.execSelect());
            qExec.close();

            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                Map<String, Object> row = new HashMap<>();

                for (String var : results.getResultVars()) {
                    RDFNode node = soln.get(var);

                    // Convert RDFNode to a more general data type if possible
                    if (node.isLiteral()) {
                        row.put(var, node.asLiteral().getValue());
                    } else if (node.isResource()) {
                        row.put(var, node.asResource().getURI());
                    } else {
                        row.put(var, node.toString());
                    }
                }
                resultsList.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultsList;
    }

    @Override
    public void write(String file) {
        try {
            RDFDataMgr.write(new FileOutputStream(file), graph, Lang.TURTLE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
