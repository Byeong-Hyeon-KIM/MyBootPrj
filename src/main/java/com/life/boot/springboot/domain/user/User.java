package com.life.boot.springboot.domain.user;

import com.life.boot.springboot.domain.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    /* Enumerated(EnumType.String */
    /* JPA로 DB저장시 Enum값을 어떤형태로 저장할지? (기본값:int) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role){
        this.name    = name;
        this.email   = email;
        this.picture = picture;
        this.role    = role;
    }

    public User update(String name, String picture) {
        this.name    = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
