package bleeter.users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository  extends MongoRepository<BleetUser, String>, UpdateableUserRepository{
	@Query(value= "{ 'id' : ?0, 'images.id' : ?1 }" )
	public String findByBleetId(String uid, String bid);
	public BleetUser findByUsername(String username);

}
