package bleeter.bleets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class BleetService {
	@Autowired
	private BleetRepository bleetRepository;

	public List<Bleet> addBleet(String uid, String bleet, String privatecomment) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Bleet> deleteBleet(String uid, String bid) {
		bleetRepository.delete(bid);
		return null;
	}

	public Bleet findByBleetId(String uid, String bid) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
