package com.example.jpa.controller;

import com.example.jpa.dto.FormDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * packageName : com.example.jpa.controller
 * fileName : HelloController2
 * author : Mingu
 * date : 2022-08-03
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-08-03         Mingu          최초 생성
 */
@Controller
public class HelloController2 {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    // Form 값 가져오기
    @RequestMapping(value = "/thymeleaf-test7-form", method = RequestMethod.GET)
    public ModelAndView getForm(ModelAndView mav) {
        logger.info("getForm 실행 됐어요~");

        // 뷰 페이지 설정
        mav.setViewName("thymeleaf-test7-form");
        // 뷰 페이지로 보낼 데이터
        mav.addObject("msg", "아래 폼 값을 입력해주시고 전송(Send) 버튼을 클릭하세요 :)");

        return mav;
    }

    // Form 값 보내기
    // *@RequestParam : HTTPparams을 받아오는(한 개) 어노테이션,
    @RequestMapping(value = "/thymeleaf-test7-form", method = RequestMethod.POST)
    public ModelAndView postForm(ModelAndView mav,
                                 @RequestParam("data1")
                                 String data1) {
        logger.info("postForm 실행 됐어요~");

        // 뷰페이지 정의
        mav.setViewName("thymeleaf-test7-form");
        // 데이터 정의
        mav.addObject("msg", "회원님이 입력하신 값은 " + "<span style='color:red'>" + data1 + "</span>" + "입니다.");
        mav.addObject("data1", data1);

        return mav;
    }

    // MultiForm 값 가져오기
    @RequestMapping(value = "/thymeleaf-test8-form-multiparam", method = RequestMethod.GET)
    public ModelAndView getMultiForm(ModelAndView mav) {
        logger.info("getMultiForm 실행 됐어요~");

        // 뷰 페이지 설정
        mav.setViewName("thymeleaf-test8-form-multiParam");
        // 뷰 페이지로 보낼 데이터
        mav.addObject("msg", "아래 여러 폼 값을 입력해주시고 전송(Send) 버튼을 클릭하세요 :)");

        return mav;
    }

    // MultiForm 값 보내기
    @RequestMapping(value = "/thymeleaf-test8-form-multiparam", method = RequestMethod.POST)
    public ModelAndView postMultiForm(ModelAndView mav,
                                      @RequestParam("id")
                                      String id,
                                      @RequestParam("name")
                                      String name,
                                      @RequestParam("email")
                                      String email,
                                      @RequestParam("age")
                                      Integer age,
                                      @RequestParam("gender")
                                      String gender) {
        logger.info("postMultiForm 실행 됐어요~");
        // 뷰 페이지 설정
        mav.setViewName("thymeleaf-test8-form-multiParam");
        // 데이터 설정
        mav.addObject("id", id);
        mav.addObject("name", name);
        mav.addObject("email", email);
        mav.addObject("age", age);
        mav.addObject("gender", gender);

        return mav;
    }

    // MultiForm 값 보내기2 - DTO 객체 활용
    @RequestMapping(value = "/thymeleaf-test9-form-dto", method = RequestMethod.GET)
    public ModelAndView getDTOForm(ModelAndView mav,
                                   @ModelAttribute
                                   FormDTO formDTO) {
        logger.info("getDTOForm 실행 됐어요~");
        // 뷰 페이지 설정
        mav.setViewName("thymeleaf-test9-form-DTO");
        // 데이터 설정
        mav.addObject("msg", "아래 여러 폼 값을 입력해주시고 전송(Send) 버튼을 클릭하세요 :)");
        // *formDTO의 값이 없을 때 뷰페이지단의 th:object에서 오류를 일으키기 때문에 빈 값을 정의해서 넘겨줘야함
        mav.addObject("formDTO", formDTO);

        logger.info(formDTO.toString());
        return mav;
    }

    // MultiForm 값 보내기2 - DTO 객체 활용
    // *@ModelAttribute("만들 데이터객체 이름 <- 생략가능") : getter, setter를 통해 DTO에 값을 주입할 수 있는 어노테이션
    @RequestMapping(value = "/thymeleaf-test9-form-dto", method = RequestMethod.POST)
    public ModelAndView postDTOForm(ModelAndView mav,
                                    @ModelAttribute
                                    FormDTO formDTO) {
        logger.info("postDTOForm 실행 됐어요~");
        // 뷰 페이지 설정
        mav.setViewName("thymeleaf-test9-form-DTO");
        // 데이터 설정
        mav.addObject("formDTO", formDTO);

        logger.info(formDTO.toString());
        return mav;
    }
}
