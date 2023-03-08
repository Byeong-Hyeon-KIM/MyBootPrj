package com.life.boot.springboot.web;

import com.life.boot.springboot.config.auth.LoginUser;
import com.life.boot.springboot.config.auth.dto.SessionUser;
import com.life.boot.springboot.service.posts.PostsService;
import com.life.boot.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    /* 원래는 httpSession.getAttribute("user")로 세션정보를 매번 가져와야 했지만 */
    /* LoginUser 어노테이션을 생성한다면 이거만 사용하면 세션 정보를 가져올 수 있음  */
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        if ( user != null ) {
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);

        return "posts-update";
    }
}
