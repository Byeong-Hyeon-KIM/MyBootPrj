package com.life.boot.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /* 이미 생성된 사용자인지? 처음 가입자인지 판단을 위함 */
    Optional<User> findByEmail(String email);

}
