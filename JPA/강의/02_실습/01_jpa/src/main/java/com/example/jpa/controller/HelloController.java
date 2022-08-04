package com.example.jpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    // resources의 thymeleaf의 helloworld.html를 불러와 매핑 시키는 요청
    // @RequestMapping("주소") : 해당주소로 요청이 들어올때 매핑을 실행하겠다.
    @RequestMapping("/helloworld")
    public String helloWorld() {
        // return의 "/"는 resources의 templates를 가리킴
        /*return "/helloWorld.html";*/
        // "/"와 확장자까지는 필요 없음
        return "helloWorld";
    }

    // 뷰페이지 전달
    @RequestMapping("/thymeleaf-test")
    public String thymeleafTest() {
        return "thymeleaf-test";
    }

    // Model을 이요한 데이터 전달
    @RequestMapping("/thymeleaf-test2")
    public String thymeleafTest2(Model model) {

        // 뷰 페이지로 보낼 데이터 정의
        model.addAttribute("data1", "Model 전달하는 데이터 data1");
        model.addAttribute("data2", "Model 전달하는 데이터 data2");

        // view로 리턴할 때 model 같이 리턴
        return "thymeleaf-test2";
    }

    // ModelAndView을 이용한 데이터 전달
    @RequestMapping("/thymeleaf-test3")
    public ModelAndView thymeleafTest3(ModelAndView mav) {

        // 뷰 페이지단으로 보낼 데이터 정의
        // *1. 데이터를 지정
        mav.addObject("data1", "ModelAndView 전달하는 데이터 data1");
        mav.addObject("data2", "ModelAndView 전달하는 데이터 data2");
        // *2. 뷰페이지를 지정
        mav.setViewName("thymeleaf-test3");
        // *3. 뷰페이지를 리턴
        return mav;
    }

    // Model + 파라미터(매개변수) 전달
    @RequestMapping("/thymeleaf-test4/{num}")
    public String thymeleafTest4(Model model,
                                 @PathVariable
                                 long num) {

        // *매개변수
        model.addAttribute("number", num);
        System.out.println("num : " + num);

        // *매개변수를 이용한 합산
        // *(사실 이 부분은 비지니스 로직으로 넘어가야할 부분이나 간단 예제이므로 컨트롤러에서 처리)
        long numSum = 0;
        for (long i = 1; i <= num; i++) {
            numSum += i;
        }
        model.addAttribute("numSum", numSum);
        System.out.println("numSum : " + numSum);

        return "thymeleaf-test4";
    }

    // Model + 파라미터(매개변수) 전달2
    @RequestMapping("/thymeleaf-test5/{num}")
    public String thymeleafTest5(Model model,
                                 @PathVariable
                                 long num) {

        // *매개변수
        model.addAttribute("number", num);
        System.out.println("num : " + num);

        // *매개변수를 이용한 구구단
        // *(사실 이 부분은 비지니스 로직으로 넘어가야할 부분이나 간단 예제이므로 컨트롤러에서 처리)
        String gugudan = "";
        for (int i = 1; i <= 9; i++) {
            gugudan += num + " × " + i + " = " + (i * num) + "<br>";
        }

        model.addAttribute("gugudan", gugudan);
        System.out.println("gugudan : " + gugudan);

        return "thymeleaf-test5";
    }

    // ModelAndView + 파라미터(매개변수) 전달3
    @RequestMapping("/thymeleaf-test6/{num}")
    public ModelAndView thymeleafTest6(ModelAndView mav,
                                 @PathVariable
                                 long num) {
        // *뷰 페이지 설정
        mav.setViewName("thymeleaf-test6");

        // *매개변수 선언
        mav.addObject("number", num);

        // *매개변수를 이용한 구구단
        // *(사실 이 부분은 비지니스 로직으로 넘어가야할 부분이나 간단 예제이므로 컨트롤러에서 처리)
        String gugudan = "";
        for (int i = 1; i <= 9; i++) {
            gugudan += num + " × " + i + " = " + (i * num) + "<br>";
        }

        mav.addObject("gugudan", gugudan);

        return mav;
    }
}
