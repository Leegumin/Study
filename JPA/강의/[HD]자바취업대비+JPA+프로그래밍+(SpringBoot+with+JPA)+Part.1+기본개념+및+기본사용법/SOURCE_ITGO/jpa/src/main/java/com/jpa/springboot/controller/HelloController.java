package com.jpa.springboot.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jpa.springboot.dto.MyBag;



@Controller
public class HelloController {
	
	@RequestMapping("/helloworld")
	public String hellWorld() {
		
		return "helloworld";  // helloworld.html
	}
	
	// 단순 요청
	@RequestMapping("/thymeleaf-test")
	public String thymeleafTest() {
		
		return "thymeleaf-test";
	}
	
	// 단순 요청
	@RequestMapping("/thymeleaf-test2")
	public String thymeleafTest2() {
		
		return "thymeleaf-test2";  // 뷰페이지를 리턴할 때 --> data1
	}

	// 단순 요청 + 뷰페이지로 데이터(Model) 전달하기
	@RequestMapping("/thymeleaf-test3")
	public String thymeleafTest3( Model model ) {
		
		// 뷰페이지단으로 데이터(model)를 같이 전달
		model.addAttribute("data1", "홍길동");
		model.addAttribute("data2", "조선시대");
		
		return "thymeleaf-test3";
	}
	
	// 단순 요청 + 뷰페이지로 데이터(ModelAndView) 전달하기
	@RequestMapping("/thymeleaf-test4")
	public ModelAndView thymeleafTest4( ModelAndView mav ) {
		
		// 뷰페이지단으로 ModelAndView를 이용해서 데이터를 전달
		// (1) 데이터를 지정
		// (2) 뷰페이지를 지정
		// (3) 리턴
		mav.addObject("name", "이순신");
		mav.addObject("age", 27);
		mav.setViewName("thymeleaf-test4");
		
		return mav;
	}

	// 단순 요청 + 뷰페이지로 데이터(Model) 전달 + @PathVariable 이용한 파라미터 전달1
	@RequestMapping("/thymeleaf-test5/{num}")
	public String thymeleafTest5( @PathVariable int num, Model model ) {
		
		// 결과 변수
		int result = 0;
		
		// 입력된 변수 num 크기 만큼 반복하면서 합산
		// num 값은 int 외에는 받을 수 없도록 되어있기 때문에 문자열은 오류 발생.
		for( int i=1; i <= num; i++ ) {
			result += i;
		}
		
		// 뷰페이지로 결과 전달
		model.addAttribute("num", num);
		model.addAttribute("result", result);
		
		return "thymeleaf-test5";
	}
	
	// 단순 요청 + 뷰페이지로 데이터(Model) 전달 + @PathVariable 이용한 파라미터 전달2 (구구단)
	@RequestMapping("/thymeleaf-test6/{num}")
	public String thymeleafTest6( @PathVariable int num, Model model ) {
		
		// 결과 변수
		String rst = "";
		
		// 입력된 변수 num에 해당하는 구구단 반복
		for( int i=1; i <= 9; i++ ) {
			rst += num + "x" + i + "=" + num * i + "<br>";			
		}
		// System.out.println( rst );
		
		// 뷰페이지로 결과 전달
		model.addAttribute("num", num);
		model.addAttribute("rst", rst);
		
		return "thymeleaf-test6";
	}
	
	// 단순 요청 + 뷰페이지로 데이터(ModelAndView) 전달 + @PathVariable 이용한 파라미터 전달
	// 여러가지 방법을 연습하는 이유는 현업에서도 실제 섞어서 사용하기 때문.
	@RequestMapping("/thymeleaf-test7/{num}")
	public ModelAndView thymeleafTest7( @PathVariable int num, ModelAndView mav ) {
		
		// 결과 변수
		int result = 0;
		
		// 입력된 변수 num 크기 만큼 반복하면서 합산
		// num 값은 int 외에는 받을 수 없도록 되어있기 때문에 문자열은 오류 발생.
		for( int i=1; i <= num; i++ ) {
			result += i;
		}
		
		// 뷰페이지로 결과 전달
		mav.addObject("num", num);
		mav.addObject("result", result);
		mav.setViewName("thymeleaf-test5");  // 뷰페이지는 그대로 5번거를 사용
		
		return mav;
	}
	
	// 단순 요청 + 뷰페이지로 데이터(ModelAndView) 전달 + @PathVariable 이용한 파라미터 전달2 (구구단)
	@RequestMapping("/thymeleaf-test8/{num}")
	public ModelAndView thymeleafTest8( @PathVariable int num, ModelAndView mav ) {
		
		// 결과 변수
		String rst = "";
		
		// 입력된 변수 num에 해당하는 구구단 반복
		for( int i=1; i <= 9; i++ ) {
			rst += num + "x" + i + "=" + num * i + "<br>";			
		}
		// System.out.println( rst );
		
		// 뷰페이지로 결과 전달
		mav.addObject("num", num);
		mav.addObject("rst", rst);
		mav.setViewName("thymeleaf-test6");  // 뷰페이지는 그대로 6번거를 사용
		
		return mav;
	}
	
	// Form 값 전달하고 받기
	// 좀 더 제대로 된 데이터를 주고 받기 위해서는 Form을 사용해서 주고 받는 것이 편리.
	// 기본적으로 Form으로 값을 주고 받을 때는 --> GET 방식과 POST 방식이 있고 이 두 방식을 사용한다.
	@RequestMapping(value = "/thymeleaf-test9", method = RequestMethod.GET)
	public ModelAndView formPage( ModelAndView mav ) {
		
		mav.addObject("msg", "아래 폼 값을 입력해주시고 전송(Send) 버튼을 클릭하세요~");
		mav.setViewName("thymeleaf-test9-form");
		
		return mav;
	}
	
	@RequestMapping(value = "/thymeleaf-test9", method = RequestMethod.POST)
	public ModelAndView formSend(
			@RequestParam("data1") String data1,  // Form으로 부터 넘어오는 값을 받을 때 사용 
			ModelAndView mav) {
		
		mav.addObject("msg", "회원님이 입력하신 값은 <span style='color: red'>" + data1 + "</span>입니다.");
		mav.addObject("data1", data1);
		mav.setViewName("thymeleaf-test9-form");
		
		return mav;
	}

	// 여러 개의 Form 값 전달하고 받기
	@RequestMapping(value = "/thymeleaf-test10", method = RequestMethod.GET)
	public ModelAndView multiFormPage( ModelAndView mav ) {
		
		mav.addObject("msg", "아래 여러 개의 폼 값을 입력하고 전송 버튼을 클릭하세요~");
		mav.setViewName("thymeleaf-test10-form");
		
		return mav;
	}

	@RequestMapping(value = "/thymeleaf-test10", method = RequestMethod.POST)
	public ModelAndView multiFormSend(
			@RequestParam("id") String id, 
			@RequestParam("name") String name, 
			@RequestParam("email") String email, 
			@RequestParam("age") Integer age, 
			@RequestParam("gender") String gender, 
			ModelAndView mav) {
		
		mav.addObject("id", id);
		mav.addObject("name", name);
		mav.addObject("email", email);
		mav.addObject("age", age);
		mav.addObject("gender", gender);
		mav.setViewName("thymeleaf-test10-form");
		
		return mav;
	}
	
	// 여러 개의 Form 값 전달하고 받기 - DTO 객체를 활용 (DTO == 책가방)
	// @ModelAttribute("formData") 사용 시 formData는 생략하고 @ModelAttribute 애너테이션만 사용도 가능.
	// @ModelAttribute 애너테이션을 사용하지 않고 new로 인스턴스를 생성하여 하는 것도 가능.
	@RequestMapping(value = "/thymeleaf-test11", method = RequestMethod.GET)
	public ModelAndView multiFormPageDto( 
			@ModelAttribute MyBag mybag, 
			ModelAndView mav) {
		
		mav.addObject("msg", "아래 여러 개의 폼 값을 입력하고 전송 버튼을 클릭하세요~");
		mav.addObject("formData", mybag);
		mav.setViewName("thymeleaf-test11-form");
		
		return mav;
	}
	
	@RequestMapping(value = "/thymeleaf-test11", method = RequestMethod.POST)
	public ModelAndView multiFormSendDto(
			@ModelAttribute MyBag mybag, 
			ModelAndView mav) {
		
		mav.addObject("formData", mybag);
		mav.setViewName("thymeleaf-test11-form");
		
		return mav;
	}
	
	// 12. 타임리프 유틸리티 메서드 사용법 (다양한 Form 값들 전달하고 받기 위해서)
	@RequestMapping(value = "/thymeleaf-test12", method = RequestMethod.GET)
	public ModelAndView formPageUtil( ModelAndView mav ) {
		
		mav.addObject("msg", "Hello World");
		mav.setViewName("thymeleaf-test12-util");
		
		return mav;
	}

	// 13. 체크박스 Form 값 전달하고 받기
	@RequestMapping(value = "/thymeleaf-test13", method = RequestMethod.GET)
	public ModelAndView diverseFormPage( ModelAndView mav ) {
		
		mav.addObject("msg", "아래 체크박스에서 사용하시는 메일 계정을 선택해주세요~");
		mav.setViewName("thymeleaf-test13-form");
		
		return mav;
	}
	
	@RequestMapping(value = "/thymeleaf-test13", method = RequestMethod.POST)
	public ModelAndView diverseFormSend(
			@RequestParam(value = "email", required = false) List<String> emailItem, 
			ModelAndView mav) {
		
		List<String> emailList = emailItem;
		
		mav.addObject("msg", "아래 체크박스에서 사용하시는 메일 계정을 선택해주세요~");
		mav.addObject("emailList", emailList);
		mav.setViewName("thymeleaf-test13-form");
		
		return mav;
	}

	// 14. 여러 개의 Form 값 전달하고 받기
	@RequestMapping(value = "/thymeleaf-test14", method = RequestMethod.GET)
	public ModelAndView diverseMultiFormPage( ModelAndView mav ) {
		
		mav.addObject("msg", "아래 다양한 폼값을 선택해주세요~");
		mav.setViewName("thymeleaf-test14-multiform");
		
		return mav;
	}
	
	@RequestMapping(value = "/thymeleaf-test14", method = RequestMethod.POST)
	public ModelAndView diverseMultiFormSend(
			@RequestParam(value = "email", required = false) List<String> emailItem, 
			@RequestParam(value = "gender", required = false) String gender, 
			@RequestParam(value = "age", required = false) String age, 
			@RequestParam(value = "mconsent", required = false) String mconsent, 
			ModelAndView mav) {
		
		// 1. 사용자가 선택한 체크박스 항목들
		List<String> emailList = emailItem;
		// System.out.println( emailList );  // null (아무 것도 선택하지 않았을 때)
		
		// emailList 값 체크
		if( emailList != null && !emailList.isEmpty() ) {
			System.out.println( emailList );
		}
		else {
			System.out.println( "emailList is empty." );
		}
	
		// 2. 뷰페이지단에서 체크박스 항목들을 자동으로 생성시켜주기 위한 용도
		List<String> checkItems = new ArrayList<String>();
		checkItems.add("gmail");
		checkItems.add("kakao");
		checkItems.add("hotmail");
		checkItems.add("naver");
		
		// 전달
		mav.addObject("msg", "아래 다양한 폼값을 선택해주세요~");
		mav.addObject("emailList", emailList);
		mav.addObject("checkItems", checkItems);
		mav.addObject("gender", gender);
		mav.addObject("age", age);
		mav.addObject("mconsent", mconsent);
		mav.setViewName("thymeleaf-test14-multiform");	
		
		// console
		// System.out.println(checkList.size());
		// System.out.println(checkList.get(0));
		System.out.println( gender );
		System.out.println( age );
		System.out.println( mconsent );
		
		return mav;
	}
}





























