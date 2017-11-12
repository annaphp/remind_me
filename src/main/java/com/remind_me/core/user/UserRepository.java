package com.remind_me.core.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
	
	public User findByUserName(String username);
	
	public User findByEmail(String email);

	public User findOneByVerificationCode(String verificationCode);

}
