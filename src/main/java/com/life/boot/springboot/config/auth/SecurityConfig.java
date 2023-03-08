package com.life.boot.springboot.config.auth;

import com.life.boot.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/* EnableWebSecurity : 스프링 시큐리티 설정들을 활성화 시켜줌 */
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    /*
    ①. csrf
    - h2-console 화면을 사용하기 위해 해당 옵션들을 disable함
    ②. authorizeRequests
    - URL별 권한 관리를 설정하는 옵션의 시작점
    - authorizeRequests가 선언되어야만 antMachers 옵션을 사용할 수 있음
    ③. antMatchers
    - 권한 관리 대상을 지정하는 옵션
    - URL, HTTP 메소드별로 관리가 가능하다.
    - 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 줌
    - ~api/v1~ 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 설정
    ④. anyRequest
    - 설정된 값을 이외 나머지 URL들을 나타냄
    - 여기서는 authenticated()를 추가하여 나머지 URL은 모두 인증 사용자에게만 허용
    - 인증사용자 : 로그인한 사용자
    ⑤. logout().logoutSuccessUrl("/")
    - 로그아웃 기능의 여러 설정의 진입점
    - 로그아웃 성공시 /주소로 이동
    ⑥. oauth2Login
    - OAuth2 로그인 기능에 대한 여러 설정의 진입점
    ⑦. userInfoEndpoint
    - OAuth2 로그인 성공 후 사용자 정보를 가져올 떄의 설정들을 담당
    ⑧. userService
    - 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
    - 리소스 서버(구글 등)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있다.
    */
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**",
                            "/js/**", "/h2-console/**", "/profile").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }
}
