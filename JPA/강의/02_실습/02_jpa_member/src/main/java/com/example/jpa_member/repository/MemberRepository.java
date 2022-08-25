package com.example.jpa_member.repository;

import com.example.jpa_member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName : com.example.jpa_member.repository
 * fileName : MemberRepository
 * author : Mingu
 * date : 2022-08-22
 * description : CRUDRepository 또는 JpaRepository를 상속받는 인터페이스 CRUD를 실행하기 위해 만들어짐
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-08-22         Mingu          최초 생성
 */
// *JpaRepository<형태, ID타입>
public interface MemberRepository extends JpaRepository<Member, Integer> {

}
