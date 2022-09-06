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
	// *findByNameContains도 동일하게 작동
	Page<Member> findByNameContaining(String name, Pageable pageable);

	// 회원 아이디로 검색하는 메서드 정의
	Page<Member> findByIdContaining(String id, Pageable pageable);

	// 회원 번호로 검색하는 메서드 정의
	Page<Member> findByPhoneContaining(String phone, Pageable pageable);

	// 회원 번호로 검색하는 메서드 정의(정확 검색)
	Page<Member> findByPhone(String phone, Pageable pageable);

	// *Like - 특정 문자열이 포함 되었는지? --> 와일드카드(%)를 사용
	// *%내용% -> Containing, 내용% -> 앞에서 검색, %내용 -> 뒤에서 검색
	Page<Member> findByPhoneLike(String phone, Pageable pageable);

	// *위와 동일한 기능의 메서드 (내용%)
	Page<Member> findByPhoneStartsWith(String phone, Pageable pageable);

	// *(%내용)
	Page<Member> findByPhoneEndsWith(String phone, Pageable pageable);

	// and, or 조건 검색
	// *and
	Page<Member> findByNameAndId(String name, String id, Pageable pageable);
	
	// *or : and와 개념이 좀 다름. 검색 키워드를 name, id 둘 다 받는 것이 아니라 하나만 받아서 하나의 키워드로 양쪽 컬럼을 전부 뒤지는 방식
	// *contains을 조합해서 사용하면 검색 키워드를 포함한(ex - 제목 혹은 내용 검색) 검색을 실시함
	Page<Member> findByNameContainsOrIdContains(String name, String id, Pageable pageable);
	Page<Member> findByNameOrId(String name, String id, Pageable pageable);
}
