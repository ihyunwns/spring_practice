package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // JPA는 보통 트랜잭션안에서 데이터 처리가 이루워져야 한다
                                // 클래스 레벨에 Transactional을 걸면 퍼블릭 메서드들은 기본적으로 트랜잭션에 걸려들어간다.
                                // 클래스 내에 readOnly=true 로 할것이 더 많기 때문에 클래스 레벨에 걸어주고
                                // 필요한 메서드만 @Transanctional을 걸어준다 (기본값이 false)
@RequiredArgsConstructor // final이 붙은 멤버를 가지고 생성자를 자동으로 만들어줌
                         // @AllArgsContructor는 모든 멤버를 가지고 생성자 자동 생성
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional // 기본값이 readOnly = false 이기 때문에 회원 조회는 true로 하고 join 기능만 false로
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
     //읽기 전용으로 하면 불필요한 것들은 안할 수 있어서 성능이 좋아지게 된다.
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
