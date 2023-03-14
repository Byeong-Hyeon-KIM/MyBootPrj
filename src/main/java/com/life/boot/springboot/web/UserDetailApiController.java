package com.life.boot.springboot.web;

import com.life.boot.springboot.service.userDetail.UserDetailService;
import com.life.boot.springboot.web.dto.UserDetailSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserDetailApiController {

    private final UserDetailService userDetailService;

    /* 마이페이지 저장 */
    @PostMapping("/api/v1/userDetail")
    public Long save(@RequestBody UserDetailSaveRequestDto requestDto) {
        return userDetailService.save(requestDto);
    }
}
