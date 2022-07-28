package com.jpamember.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.jpamember.springboot.entity.Member;

// CrudRepository 또는 JpaRepository 상속.
public interface MemberRepository extends JpaRepository<Member, Integer> {

	// 회원 검색용 - name
	Page<Member> findByNameContaining( String name, Pageable pageable );

	// 회원 검색용 - id
	Page<Member> findById( String id, Pageable pageable );

	// 회원 검색용 - phone
	// Containing은 포함이므로 010- 만 검색해도 010 이 포함된 번호를 모두 검색.
	Page<Member> findByPhoneContaining( String phone, Pageable pageable );
	
	// 만약 010- 으로만 된 번호만 검색하고 싶다면???
	Page<Member> findByPhone( String phone, Pageable pageable );
	
	// LIKE - 특정 문자열이 포함되었는지? --> %111% 사용. 
	Page<Member> findByPhoneLike(String phone, Pageable pageable);
	
	// 111 번호로 시작하는 번호를 찾으려면? --> 111% --> 쿼리조건 키워드조합 --> findByPhoneStartsWith
	Page<Member> findByPhoneStartsWith(String phone, Pageable pageable);
	
	// 111 번호로 끝나는 번호를 찾으려면? --> %111 --> 쿼리조건 키워드조합 --> findByPhoneEndsWith
	Page<Member> findByPhoneEndsWith(String phone, Pageable pageable);
	
	// 대소문자 구분하기
	Page<Member> findByIdContains(String id, Pageable pageable);
	
	// and 쿼리 조건 만들기
	Page<Member> findByNameAndId( String name, String id, Pageable pageable );
	
	// or 쿼리 조건 만들기
	Page<Member> findByNameContainsOrIdContains( String name, String id, Pageable pageable );
	
	// JPA 정렬 쿼리 메서드 만들기1 - 회원 등록순(num)으로 정렬 --> (1) 조건 (2) 정렬
	Page<Member> findByNumGreaterThanEqualOrderByNameAsc( Integer num, Pageable pageable );
	
	// JPA 정렬 쿼리 메서드 만들기2
	Page<Member> findByAgeLessThanEqualOrderByNameAsc( int age, Pageable pageable );
	
	// JPA 정렬 쿼리 메서드 만들기3
	Page<Member> findByNameContains( String name, Pageable pageable );
	

	// -------------------------------------------------------------------------------------------
	
	
	// @Query 애너테이션을 이용하여 쿼리를 작성하는 법
	
	// [1] : 회원 이름으로 검색하기 --> 쿼리 메서드 X --> @Query 애너테이션 O
	// @Query( "SELECT m FROM Member m WHERE m.name = ?1 and m.age > 20 ORDER BY m.name ASC" )
	
	// [2] : 이름에 "순신" 들어가고, 나이가 20 이상이고, 정렬은 이름기준 오름차순으로 정렬하시오.
	// @Query( "SELECT m FROM Member m WHERE m.name LIKE %?1% and m.age < 20 ORDER BY m.name DESC" )	
	// public Page<Member> findByName( String name, Pageable pageable );
	
	// [3] : 이름에 "순신" 들어가고, 나이가 10~20 사이의 회원을 이름기준 오름차순으로 정렬하시오.
	// @Query( "SELECT m FROM Member m WHERE (m.name LIKE %?1%) and (m.age BETWEEN 10 AND 20) ORDER BY m.name ASC" )
	// public Page<Member> findByName( String name, Pageable pageable );
	
	// [4] : 위 [3]번 쿼리를 쿼리 메서드로 키워드 조합해서 만들어보시오.
	public Page<Member> findByNameContainingAndAgeBetweenOrderByNameAsc( String name, int start, int end, Pageable pageable );
	
	// -----------------------------------------------------------------------------------------------------------------------
	
	// JPQL 다양한 사용법
	
	// [1] : @Query 애너테이션 + JPQL을 사용한 수정 처리
	@Transactional
	@Modifying
	@Query( "UPDATE Member m SET m.name = :name, m.age = :age WHERE m.num = :num" )
	int updateMemberQuery( @Param("num") int num, @Param("name") String name, @Param("age") int age );
	
	// [2] : @Query 애너테이션 + JPQL을 사용 --> LIKE 콜론(:) 파라미터 바인딩
	@Query( "SELECT m FROM Member m WHERE (m.name LIKE %:name%) AND (m.age BETWEEN :from AND :to) ORDER BY m.name ASC" )
	Page<Member> findMembers( @Param("name") String name, @Param("from") int from, @Param("to") int to, Pageable pageable );
		
	// -----------------------------------------------------------------------------------------------------------------------
	

	// SELECT * FROM member m WHERE (m.name LIKE '%순신%') AND EXISTS ( SELECT 1 FROM member mm WHERE mm.age > 10 );
	// SELECT * FROM member m WHERE (m.name LIKE '%순신%') AND EXISTS ( SELECT 1 FROM member mm WHERE mm.age < 10 );
	// SELECT * FROM member m WHERE (m.name LIKE '%순신%') AND EXISTS ( SELECT 1 FROM member mm WHERE mm.age > 10 ) limit 1;

	// SELECT EXISTS (SELECT 1 FROM member mm WHERE mm.age > 10); <-- 서브쿼리만 수행해보면 한 번만 수행함을 알 수 있다.

	// SELECT name, id, age FROM member m WHERE EXISTS (SELECT 1 FROM member mm WHERE mm.age = 19);
	// SELECT name, id, age FROM member m WHERE EXISTS (SELECT 1 FROM member mm WHERE mm.id = m.name);

	// SELECT * FROM 회원테이블 WHERE EXISTS ( SELECT 1 FROM 주문테이블 WHERE 주문테이블.주문번호(기본키) = 회원테이블.주문번호 );
	// SELECT * FROM member WHERE EXISTS ( SELECT 1 FROM buy WHERE buy.ordernum(기본키) = member.ordernum );
	
	
	// [0]
	Page<Member> findByName( String name, Pageable pageable );
	
	// [1]
	boolean existsByName( String name );
	
	// [2]
	// select count(*) from member where name = '홍순신';  // 1
	@Query(
			"SELECT count(m.num) " +
			"FROM Member m " +
			"WHERE m.name = :name AND m.id = :id"
		  )
	int existsQuery( @Param(value = "name") String name, @Param(value = "id") String id );
	
	// [3] : 일반 순수 SQL 쿼리
	@Query(value = "SELECT * FROM member m WHERE m.name LIKE %:searchKeyword% AND EXISTS ( SELECT 1 FROM member mm WHERE mm.age < 20 )", nativeQuery = true)
	Page<Member> selectAllSQL( @Param(value = "searchKeyword") String searchKeyword, Pageable pageable );
	
}
