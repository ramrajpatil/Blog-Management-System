package com.blog.app.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 30)
	private String name;

	@Column(length = 50, nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(length = 500)
	private String about;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Post> posts = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",
	joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();

	public User(String name, String email, String password, String about) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> grantedAuths = this.roles.stream()
				.map(r -> new SimpleGrantedAuthority(r.getName()))
				.collect(Collectors.toList());
		
		return grantedAuths;
	}

	// Method to retrieve the username associated with the user.
	@Override
	public String getUsername() {
		return this.email;
	}

	// Method to retrieve the password associated with the user
	// Already implemented using getters.

	// Method to determine if the user's account is not expired
	@Override
	public boolean isAccountNonExpired() {
		return true; // Assuming no account expiration logic for this example
	}

	// Method to determine if the user's account is not locked
	@Override
	public boolean isAccountNonLocked() {
		return true; // Assuming no account locking logic for this example
	}

	// Method to determine if the user's credentials are not expired
	@Override
	public boolean isCredentialsNonExpired() {
		return true; // Assuming no credential expiration logic for this example
	}

	// Method to determine if the user is enabled
	@Override
	public boolean isEnabled() {
		return true; // Assuming all users are enabled for this example
	}

}
