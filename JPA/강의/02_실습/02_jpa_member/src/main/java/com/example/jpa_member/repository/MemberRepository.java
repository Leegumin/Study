package com.example.jpa_member.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa_member.entity.Member;

/**
 * packageName : com.example.jpa_member.repository fileName : MemberRepository
 * author : Mingu date : 2022-08-22 description : CRUDRepository 또는
 * JpaRepository를 상속받는 인터페이스 CRUD를 실행하기 위해 만들어짐
 * =========================================================== DATE AUTHOR NOTE
 * ----------------------------------------------------------- 2022-08-22 Mingu
 * 최초 생성
 */
// *JpaRepository<형태, ID타입>
public interface MemberRepository extends JpaRepository<Member, Integer> {

	// 검색 조회 메서드 정의 방법
	// findBy검색내용Containing = 검색내용 포함 검색
	// findBy검색내용= 검색내용 정확 검색
	
	// 회원 이름으로 검색하는 메서드 정의
	Page<Member> findByNameContaining(String name, Pageable pageable);
	// 회원 아이디로 검색하는 메서드 정의
	Page<Member> findByIdContaining(String id, Pageable pageable);
	// 회원 번호로 검색하는 메서드 정의
	Page<Member> findByPhoneContaining(String phone, Pageable pageable);
	// 회원 번호로 검색하는 메서드 정의(정확 검색)
	Page<Member> findByPhone(String phone, Pageable pageable);
}
