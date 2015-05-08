package bleeter.bleets;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BleetRepository extends MongoRepository<Bleet, String>, UpdateableBleetRepository{
	public Page<Bleet> findByUid(String uid, Pageable page);
	public Page<Bleet> findByUsernameLike(String username, Pageable page);
	public Page<Bleet> findByTimestampAfter(Date after, Pageable page);
	public Page<Bleet> findByTimestampBefore(Date before, Pageable page);
	public Page<Bleet> findByTimestampBeforeAndUsernameLike(Date before, String username, Pageable page);
	public Page<Bleet> findByTimestampAfterAndUsernameLike(Date after, String username, Pageable page);
}
