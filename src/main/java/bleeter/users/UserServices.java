package bleeter.users;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bleeter.bleets.Bleet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServices implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	public static final String SENTIMENT_HOST = "sentiment.vivekn.com/api/text/";

	public BleetUser findById(String uid) {
		return userRepository.findOne(uid);
	}

	public BleetUser findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public BleetUser createUser(BleetUser newUser) {
		List<String> authorities = new ArrayList<String>();
		authorities.add("ROLE_USER");
		return userRepository.insert(newUser);
	}

	public Page<BleetUser> findAll(int min, int max) {
		return userRepository.findAll(new PageRequest(min, max));
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		BleetUser user = userRepository.findByUsername(username);
		if (user == null){
			throw new UsernameNotFoundException("User " + username + " not found.");
		}
		User details = new UserWithId(user.getUsername(), user.getPassword(), true, true, true, true, authorities(user.getAuthorities()), user.getId());
		return details;
	}
	
	public List<GrantedAuthority> authorities(List<String> auths) {
		List<GrantedAuthority> result = new ArrayList<>();
		for (String auth : auths) {
			result.add(new SimpleGrantedAuthority(auth));
		}
		return result;
	}
	
	public List<Bleet> addBleet(String uid, Bleet newBleet) {
		BleetUser user = userRepository.findByUsername(uid);
		readSentiment(newBleet);
		List<Bleet> bleets = user.getBleets();
		if (bleets == null){
			bleets = new ArrayList<Bleet>();
		}
		bleets.add(newBleet);
		user.setBleets(bleets);
		userRepository.update(user);
		return bleets;
	}

	public List<Bleet> deleteBleet(String uid, String bid) {
		BleetUser user = userRepository.findByUsername(uid);
		List<Bleet> bleets = user.getBleets();
		if (bleets != null){
			for (Bleet bleet : bleets) {
				if (bleet.getId().equals(bid)) {
					bleets.remove(bleet);
					break;
				}
			}
		}
		userRepository.update(user);
		return bleets;
	}

	public Bleet findByBleetId(String uid, String bid) {
		return userRepository.findByBleetId(uid, bid);
	}

	public List<Bleet> updateBleet(String uid, String bid, String bleet,
			Boolean privatecomment) {
		Bleet b = userRepository.findByBleetId(uid, bid);
		b.setBleet(bleet);
		b.setPrivateComment(privatecomment);
		deleteBleet(uid, bid);
		return addBleet(uid, b);
	}

	public List<Bleet> findAllBleets() {
		List<BleetUser> users = userRepository.findAll();
		List<Bleet> bleets = new ArrayList<Bleet>();
		for (BleetUser user : users) {
			bleets.addAll(user.getBleets());
		}
		return bleets;
	}

	public List<Bleet> findUsersBleets(String uid) {
		return userRepository.findByUsername(uid)
				.getBleets();
	}

	public BleetUser updateUser(BleetUser user) {
		return userRepository.update(user);
	}

	public BleetUser makeAdmin(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	public BleetUser changeBlock(String bid, Boolean block) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void readSentiment (Bleet bleet) {
		try {
		URI uri = new URIBuilder()
		.setScheme("http")
		.setHost(SENTIMENT_HOST)
		.build();
		
		HttpPost httppost = new HttpPost(uri);
		httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httppost.setHeader("Cache-Control", "no-cache");
		String body = "txt=" + bleet.getBleet();
		/*List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("param1", "paramValue1"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");*/
		httppost.setEntity(new ByteArrayEntity(body.getBytes("UTF-8")));
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom()
		        .setSocketTimeout(5000)
		        .setConnectTimeout(5000)
		        .build();
		
		httppost.setConfig(requestConfig);
		System.out.println(httppost);
		Header[] headers = httppost.getAllHeaders();
		for (int i = 0; i < headers.length; i++) {
			System.out.println(headers[i]);
		}
		System.out.println(httppost.getEntity());
		CloseableHttpResponse response = httpclient.execute(httppost);		
		
		HttpEntity result = response.getEntity();
		InputStream stream = result.getContent();			
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode root = mapper.readTree(stream);
		JsonNode res = root.get("result");
		bleet.setSentiment(res.get("sentiment").asText());
		bleet.setConfidence(res.get("confidence").asDouble());
		stream.close();
		httpclient.close();
		} catch (Exception e) {
			System.out.println(e);
		} 
	}

}

