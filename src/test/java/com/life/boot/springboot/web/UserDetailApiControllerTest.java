package com.life.boot.springboot.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.life.boot.springboot.domain.posts.Posts;
import com.life.boot.springboot.domain.user.UserDetail;
import com.life.boot.springboot.domain.user.UserDetailRepository;
import com.life.boot.springboot.web.dto.UserDetailSaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserDetailApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        userDetailRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER")
    public void UserDetail_insert() throws Exception {
        //given
        String nickname = "nickname";
        int point = 1000;
        UserDetailSaveRequestDto requestDto = UserDetailSaveRequestDto.builder()
                .nickname(nickname)
                .point(point)
                .build();

        String url = "http://localhost:" + port + "/api/v1/userDetail";
        System.out.println(url);
        //when
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());


        //then
        List<UserDetail> all = userDetailRepository.findAll();
        assertThat(all.get(0).getNickname()).isEqualTo(nickname);
        assertThat(all.get(0).getPoint()).isEqualTo(point);
    }
}
