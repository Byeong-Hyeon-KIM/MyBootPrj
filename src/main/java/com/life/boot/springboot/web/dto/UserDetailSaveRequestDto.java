package com.life.boot.springboot.web.dto;

import com.life.boot.springboot.domain.user.UserDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDetailSaveRequestDto {
    private String nickname;
    private int point;

    @Builder
    public UserDetailSaveRequestDto(String nickname, int point) {
        this.nickname = nickname;
        this.point = point;
    }



    public UserDetail toEntity() {
        return UserDetail.builder()
                .nickname(nickname)
                .point(point)
                .build();
    }
}
