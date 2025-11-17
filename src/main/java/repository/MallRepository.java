package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Mall;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MallRepository extends InFileRepository<Mall> {

    /**
     * Constructs the MallRepository.
     * It calls the parent constructor from InFileRepository, specifying the
     * JSON file to be used ("mall.json") and providing a TypeReference.
     * The TypeReference is essential for Jackson to correctly deserialize the
     * JSON array into a List of Mall objects.
     */
    public MallRepository() {
        super("mall.json", new TypeReference<List<Mall>>() {});
    }
}