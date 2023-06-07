package core;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public interface Graph {

    String getNamedGraph();

    void addTriple(String subject, String predicate, String object);
    void addTripleLiteral(String subject, String predicate, String object);

    void deleteTriple(String subject, String predicate, String object);

    List<Map<String, Object>> query(String sparql);

    void write(String file);

}
