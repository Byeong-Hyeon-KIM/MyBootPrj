package com.life.boot.springboot.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private int point;


    @Builder
    public UserDetail(String nickname, int point){
        this.nickname    = nickname;
        this.point       = point;
    }

    public UserDetail update(String nickname, int point) {
        this.nickname    = nickname;
        this.point       = point;

        return this;
    }

}
