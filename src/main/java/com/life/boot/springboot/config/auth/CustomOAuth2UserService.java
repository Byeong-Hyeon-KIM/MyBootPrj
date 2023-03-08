package com.life.boot.springboot.config.auth;

import com.life.boot.springboot.config.auth.dto.OAuthAttributes;
import com.life.boot.springboot.config.auth.dto.SessionUser;
import com.life.boot.springboot.domain.user.User;
import com.life.boot.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

         /*
        ①. registrationId
        - 현재 진행중인 로그인 서비스 구분 (구글인지 네이버인지 등 )
        ②. userNameAttributeName
        - OAuth2 로그인 진행 시 키가 되는 필드값 (Primary Key와 같은 의미)
        - 구글의 경우 기본적으로 코드를 지원 but 네이버 카카오는 지원x  (구글 기본코드 : sub)
        - 네이버로그인, 구글로그인 등 동시 지원시 사용됨
        */
        String registrationId        = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        
        /* getAttributes() : OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스 */
        OAuthAttributes attributes = OAuthAttributes.
                of(registrationId, userNameAttributeName,
                        oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        /* 세션에 사용자 정보를 저장하기 위한 Dto 클래스 */
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }


}
