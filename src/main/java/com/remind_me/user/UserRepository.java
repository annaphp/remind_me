package com.remind_me.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
	
	public User findByUserName(String username);
	
	public User findByEmail(String email);

}
