package com.example.jpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * packageName : com.example.jpa.controller
 * fileName : HelloController3
 * author : Mingu
 * date : 2022-08-05
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-08-05         Mingu          최초 생성
 */
@Controller
public class HelloController3 {

    @RequestMapping(value = "/thymeleaf-test10-utility", method = RequestMethod.GET)
    public ModelAndView formPageUtil(ModelAndView mav) {

        // 뷰페이지 설정
        mav.setViewName("thymeleaf-test10_utility");
        // 데이터 설정
        mav.addObject("msg", "Hello World");
        return mav;
    }
}
