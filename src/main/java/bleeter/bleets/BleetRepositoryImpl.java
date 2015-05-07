package bleeter.bleets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


public class BleetRepositoryImpl implements UpdateableBleetRepository {
	@Autowired
	@Qualifier("defaultMongoTemplate")
	private MongoOperations mongo;
	
	private Update getUpdate(Bleet x, Bleet y) {
		Update update = new Update();
		update.set("bleet", y.getBleet());
		update.set("timestamp", y.getTimestamp());
		update.set("blocked", y.isBlocked());
		update.set("sentiment", y.getSentiment());
		update.set("confidence", y.getConfidence());
		update.set("privateComment", y.isPrivateComment());	
		update.set("uid", y.getUid());
		update.set("username", y.getUsername());
		return update;
	}
	
	@Override
	public Bleet update (Bleet bleet) {
		Query query = new Query();
		query.addCriteria(Criteria.where("bleet").is(bleet.getBleet()));
		Bleet old = mongo.findOne(query,  Bleet.class);		
		mongo.updateFirst(query, getUpdate(old, bleet), Bleet.class);
		return mongo.findOne(query, Bleet.class);
	}
}
