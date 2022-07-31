package com.example.jpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName : com.example.jpa.controller
 * fileName : HelloController
 * author : Mingu
 * date : 2022-08-01
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-08-01         Mingu          최초 생성
 */
@Controller
public class HelloController {

    // resources의 templates의 helloworld.html를 불러와 매핑 시키는 요청
    // @RequestMapping("주소") : 해당주소로 요청이 들어올때 매핑을 실행하겠다.
    @RequestMapping("/helloworld")
    public String helloWorld() {
        // return의 "/"는 resources의 templates를 가리킴
        /*return "/helloWorld.html";*/
        // "/"와 확장자까지는 필요 없음
        return "helloWorld";
    }
}
