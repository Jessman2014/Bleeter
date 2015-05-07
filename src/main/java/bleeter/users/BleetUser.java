package bleeter.users;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public class BleetUser {
	@Id
	private String id;
	private String firstName;
	private String lastName;
	@Indexed
	private String username;
	private String email;
	private String avatar;
	private List<String> favorites;
	private List<String> authorities;
	
	@JsonIgnore
	private String password;
	
	@PersistenceConstructor
	public BleetUser(String id, String firstName, String lastName,
			String username, String email, String avatar,
			List<String> favorites,
			List<String> authorities, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.avatar = avatar;
		this.favorites = favorites;
		this.authorities = authorities;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	private BleetUser(Builder b) {
		this.id = b.id;
		this.firstName = b.firstName;
		this.lastName = b.lastName;
		this.username = b.username;
		this.email = b.email;
		this.avatar = b.avatar;
		this.favorites = b.favorites;
		this.authorities = b.authorities;
		this.password = b.password;
	}

	public static class Builder {
		private String id;
		private String firstName;
		private String lastName;
		private String username;
		private String email;
		private String avatar;
		private List<String> favorites;
		private List<String> authorities;
		private String password;
		
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		
		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		
		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		
		public Builder username(String username) {
			this.username = username;
			return this;
		}
		
		public Builder email(String email) {
			this.email = email;
			return this;
		}
		
		public Builder avatar(String avatar) {
			this.avatar = avatar;
			return this;
		}
		
		public Builder favorites(List<String> favorites) {
			this.favorites = favorites;
			return this;
		}
		
		public Builder authorities(List<String> authorities){
			this.authorities = authorities;
			return this;
		}
		
		public Builder password(String password) {
			this.password = password;
			return this;
		}
		
		public BleetUser build() {
			return new BleetUser(this);
		}
	}
}
