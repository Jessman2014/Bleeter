package bleeter.users;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository  extends MongoRepository<BleetUser, String>, UpdateableUserRepository{

	public BleetUser findByUsername(String username);

}
