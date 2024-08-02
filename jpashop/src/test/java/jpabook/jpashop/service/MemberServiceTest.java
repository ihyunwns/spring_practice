package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/*
@RunWith(SpringRunner.class): 스프링과 테스트 통합
@SpringBootTest: 스프링 부트 띄우고 테스트(이게 없으면 @Autowired 다 실패)
*/
@RunWith(SpringRunner.class)
@SpringBootTest

@Transactional //데이터 rollback
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Autowired EntityManager em;

    @Test
    //@Rollback(value = false) // 테스트코드 중 @Transactional이 있으면 기본적으로 롤백함
                               // Rollback 하고 싶지 않을 때는 이렇게 해준다.
    public void 회원가입() throws Exception {
        //given, 이런게 주어졌을 때
        Member member = new Member();
        member.setName("KIM");

        //when, 이러한 상황에
        Long saveId = memberService.join(member);

        //then, 이런 값이 나와야 해

        //em.flush(); // Rollback은 하되 insert되는 것은 보고 싶을 때 ! Transactional은 insert 하기 전에 롤백해버림
                      //그래서 insert 문이 보이지 않음
                      // 영속성 콘텍스트에 있는 데이터를 DB에 반영해줌
        Assert.assertEquals(member, memberRepository.findOne(saveId)); //Repository에서 찾은 회원과 만든 회원이 같아야 해


    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("KIM");

        Member member2 = new Member();
        member2.setName("KIM");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        Assert.fail("예외가 발생해야 한다.");
    }


}