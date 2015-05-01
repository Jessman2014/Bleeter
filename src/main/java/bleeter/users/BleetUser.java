package bleeter.users;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import bleeter.bleets.Bleet;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public class BleetUser {
	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String avatar;
	private List<String> favorites;
	private List<Bleet> bleets;
	private List<String> authorities;
	
	@JsonIgnore
	private String password;


	public BleetUser(String id, String firstName, String lastName,
			String username, String email, String avatar,
			List<String> favorites, List<String> authorities, List<Bleet> bleets, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.avatar = avatar;
		this.favorites = favorites;
		this.authorities = authorities;
		this.bleets = bleets;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<String> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<String> favorites) {
		this.favorites = favorites;
	}
	public List<String> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
	public List<Bleet> getBleets() {
		return bleets;
	}

	public void setBleets(List<Bleet> bleets) {
		this.bleets = bleets;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
