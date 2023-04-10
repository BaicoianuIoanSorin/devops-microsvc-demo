package dk.via.doc1.microservice.accessdata;

import java.util.List;

import dk.via.doc1.microservice.model.Entry;
import org.springframework.data.repository.CrudRepository;
public interface EntryRepository extends CrudRepository<Entry,Long>{
}
