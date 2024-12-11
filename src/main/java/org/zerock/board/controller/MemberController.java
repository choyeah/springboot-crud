package org.zerock.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.entity.QMember;
import org.zerock.board.repository.MemberRepository;
import org.zerock.board.service.BoardService;

import com.querydsl.core.BooleanBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/member/")
@Log4j2
@RequiredArgsConstructor
public class MemberController {
	
	@Autowired
    private MemberRepository memberRepository;
    
    @GetMapping("/mid/{mid}")
     public void getMemberById(@PathVariable String mid) {
        // 동적인 쿼리를 작성하는 예제: 나이(age)가 주어진 값과 일치하는 경우
        BooleanBuilder predicate = new BooleanBuilder().and(QMember.member.email.contains(mid));
        log.info("memberRepository.findAll(predicate)=============");
        log.info(memberRepository.findAll(predicate));
        // findAll 메서드를 사용하여 동적인 쿼리를 실행
        
      //public Iterable<Member> getMemberById(@PathVariable String mid) {
      //return 	memberRepository.findAll(predicate)	; }
        		
    }

}
