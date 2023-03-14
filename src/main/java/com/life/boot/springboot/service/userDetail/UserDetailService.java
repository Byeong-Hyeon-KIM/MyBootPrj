package com.life.boot.springboot.service.userDetail;


import com.life.boot.springboot.domain.user.UserDetailRepository;
import com.life.boot.springboot.web.dto.UserDetailSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserDetailService {
    private final UserDetailRepository userDetailRepository;

    @Transactional
    public Long save(UserDetailSaveRequestDto requestDto) {
        return userDetailRepository.save(requestDto.toEntity()).getId();
    }
}
