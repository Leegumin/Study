package com.example.jpa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    Logger logger = LoggerFactory.getLogger(this.getClass());

    // thymeleaf-test10_utility.html
    @RequestMapping(value = "/thymeleaf-test10-utility", method = RequestMethod.GET)
    public ModelAndView formPageUtil(ModelAndView mav) {
        logger.info("formPageUtil 실행 됐어요~");
        // 뷰페이지 설정
        mav.setViewName("thymeleaf-test10_utility");
        // 데이터 설정
        mav.addObject("msg", "Hello World");
        return mav;
    }

    // thymeleaf-test11_checkBox.html
    @RequestMapping(value = "/thymeleaf-test11-checkbox", method = RequestMethod.GET)
    public ModelAndView formCheckBox(ModelAndView mav) {
        logger.info("formCheckBox 실행 됐어요~");

        // 뷰페이지 설정
        mav.setViewName("thymeleaf-test11_checkBox");
        // 데이터 설정
        mav.addObject("msg", "아래 체크박스에서 사용하시는 메일 계정을 선택해주세요");
        return mav;
    }

    // * RequestParam의 required = false : 파라미터 값이 없어도 된다는 뜻, true일 경우 반드시 필요하다는 말
    @RequestMapping(value = "/thymeleaf-test11-checkbox", method = RequestMethod.POST)
    public ModelAndView postFormCheckBox(ModelAndView mav,
                                         @RequestParam(value = "email", required = false)
                                         List<String> emails) {
        logger.info("postFormCheckBox 실행 됐어요~");

        List<String> emailList = emails;
        // 뷰페이지 설정
        mav.setViewName("thymeleaf-test11_checkBox");
        // 데이터 설정
        mav.addObject("msg", "아래 체크박스에서 사용하시는 메일 계정을 선택해주세요");
        mav.addObject("emailList", emailList);
        return mav;
    }

    // thymeleaf-test12_checkBox2.html
    @RequestMapping(value = "/thymeleaf-test12-checkbox", method = RequestMethod.GET)
    public ModelAndView formCheckBox2(ModelAndView mav) {
        logger.info("formCheckBox2 실행 됐어요~");

        // 뷰페이지 설정
        mav.setViewName("thymeleaf-test12_checkBox2");
        // 데이터 설정
        mav.addObject("msg", "아래 다양한 폼값을 선택해주세요");
        return mav;
    }

    @RequestMapping(value = "/thymeleaf-test12-checkbox", method = RequestMethod.POST)
    public ModelAndView postFormCheckBox2(ModelAndView mav,
                                          @RequestParam(value = "email", required = false)
                                          List<String> emailItems,
                                          @RequestParam(value = "gender", required = false)
                                          String gender,
                                          @RequestParam(value = "age")
                                          String age,
                                          @RequestParam(value = "mconsent", required = false)
                                          String mconsent) {
        logger.info("postFormCheckBox2 실행 됐어요~");

        // 뷰페이지 설정
        mav.setViewName("thymeleaf-test12_checkBox2");
        // 데이터 설정
        mav.addObject("msg", "아래 다양한 폼값을 선택해주세요");

        // Email
        // *1. 사용자가 체크한 값을 리스트로 저장
        List<String> emailList = emailItems;
        mav.addObject("emailList", emailList);
        logger.info("emailList : {}", emailList);

        // *emailList를 Optional의 null 체크를 통해 빈 배열 값으로 바꿔서 작업해줘도 될 것 같음
        List<String> emailList2 = Optional.ofNullable(emailItems).orElse(new ArrayList<>());
        mav.addObject("emailList2", emailList2);
        logger.info("emailList2 : {}", emailList2);

        // *2. 뷰페이지단에서 자동으로 체크박스 항목들을 생성시켜주기 위한 용도
        List<String> checkItems = new ArrayList<>();
        checkItems.add("gmail");
        checkItems.add("kakao");
        checkItems.add("hotmail");
        checkItems.add("naver");
        mav.addObject("checkItems", checkItems);

        // Gender
        mav.addObject("gender", gender);
        logger.info("gender : {}", gender);

        // Age
        mav.addObject("age", age);
        logger.info("age : {}", age);

        // mconsent
        mav.addObject("mconsent", mconsent);
        logger.info("mconsent : {}", mconsent);

        return mav;
    }
}
