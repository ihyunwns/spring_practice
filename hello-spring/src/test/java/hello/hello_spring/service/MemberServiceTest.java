package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemoryMemberRepository memberRepository;
    MemberService memberService;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }


    @Test
    void 회원가입() {
        //given, 어떤 상황이 주어졌을 때
        Member member = new Member();
        member.setName("spring");

        //when, 이걸 실행 했을 때
        Long saveId = memberService.join(member);
        //then, 결과가 이렇게 나와야 해
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        /*try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회웝입니다.");
        }*/

        //then
    }

    @Test
    void 멤버전부찾기() {
    }

    @Test
    void findOne() {
    }
}