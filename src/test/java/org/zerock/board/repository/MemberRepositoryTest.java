package org.zerock.board.repository;


import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.zerock.board.entity.Member;


@SpringBootTest
@EnableJpaAuditing
public class MemberRepositoryTest {
	
	@Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertDummies(){

    	IntStream.rangeClosed(1, 100).forEach(i -> {

            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .pwd("1111")
                    .name("USER" + i)
                    .build();

            memberRepository.save(member);
        });
    }
}
