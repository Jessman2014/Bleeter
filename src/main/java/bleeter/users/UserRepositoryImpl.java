package bleeter.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class UserRepositoryImpl implements UpdateableUserRepository {
	@Autowired
	@Qualifier("defaultMongoTemplate")
	private MongoOperations mongo;
		
	private Update getUpdate(BleetUser x, BleetUser y) {
		Update update = new Update();
		update.set("firstName", y.getUsername());
		update.set("lastName", y.getPassword());
		update.set("email", y.getEmail());
		update.set("avatar", y.getAvatar());
		update.set("favorites", y.getFavorites());
		update.set("bleets", y.getBleets());	
		update.set("authorities", y.getAuthorities());
		//update.set("username", y.getUsername());
		return update;
	}
	
	@Override
	public BleetUser update (BleetUser user) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(user.getUsername()));
		BleetUser old = mongo.findOne(query,  BleetUser.class);		
		mongo.updateFirst(query, getUpdate(old, user), BleetUser.class);
		return mongo.findOne(query, BleetUser.class);
	}
}