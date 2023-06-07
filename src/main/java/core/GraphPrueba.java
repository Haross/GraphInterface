package core;

import core.jena.GraphJena;
import core.rdf4j.GraphRDF4J;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import java.util.List;
import java.util.Map;

public class GraphPrueba {


    public static void main(String[] args) {

        String path = "/Users/javierflores/Library/CloudStorage/OneDrive-Personal/PhD/code/graphPruebas/src/main/resources";
        String namespace = "http://www.example.com/";

        String subject = namespace + "EDBT";
        String predicate = RDF.type.getURI().toString();
        String object = namespace + "Conference";

        String subject1 = namespace + "EDBT";
        String predicate1 = RDFS.label.toString();
        String literal1 = "International Conference on Extending Database Technology";

        String subject2 = namespace + "ISWC";
        String predicate2 = RDF.type.getURI().toString();
        String object2 = namespace + "Conference";

        String subject3 = namespace + "SWJ";
        String predicate3 = RDF.type.getURI().toString();
        String object3 = namespace + "Journal";


        Graph g1 = new GraphJena();
        g1.addTriple(subject, predicate, object);
        g1.addTripleLiteral(subject1, predicate1, literal1);
        g1.addTriple(subject2, predicate2, object2);
        g1.addTriple(subject3, predicate3, object3);

        g1.deleteTriple(subject3, predicate3, object3);

        List<Map<String, Object>> result = g1.query("SELECT ?s ?p ?o WHERE { ?s ?p ?o. } ");

        System.out.println("?s\t?p\t?o");
        for (Map<String, Object> row :  result){
            System.out.println(row.get("s")+"\t"+row.get("p")+"\t"+row.get("o"));
        }
        g1.write(path + "/graphJena.ttl");

        System.out.println("---- RDF4J -----");
        Graph g2 = new GraphRDF4J();
        g2.addTriple(subject, predicate, object);
        g2.addTripleLiteral(subject1, predicate1, literal1);
        g2.addTriple(subject2, predicate2, object2);
        g2.addTriple(subject3, predicate3, object3);

        g2.deleteTriple(subject3, predicate3, object3);

        List<Map<String, Object>> result2 = g2.query("SELECT ?s ?p ?o WHERE { ?s ?p ?o. } ");

        System.out.println("?s\t?p\t?o");
        for (Map<String, Object> row :  result2){
            System.out.println(row.get("s")+"\t"+row.get("p")+"\t"+row.get("o"));
        }
        g2.write(path + "/graphRDF4J.ttl");



    }
}
