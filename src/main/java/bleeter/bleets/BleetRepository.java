package bleeter.bleets;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BleetRepository extends MongoRepository<Bleet, String>, UpdateableBleetRepository{
	public Page<Bleet> findByUid(String uid, Pageable page);
	
}
