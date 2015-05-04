package bleeter.users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import bleeter.bleets.Bleet;

public interface UserRepository  extends MongoRepository<BleetUser, String>, UpdateableUserRepository{
	@Query(value= "{ 'id' : ?0, 'images.id' : ?1 }" )
	public Bleet findByBleetId(String uid, String bid);
	public BleetUser findByUsername(String username);

}
