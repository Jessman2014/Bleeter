package bleeter.bleets;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BleetRepository extends MongoRepository<Bleet, String>{
}
