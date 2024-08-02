package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    //테스트
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    // 생성자 주입은 컴포넌트를 빈에 등록할 때 자동으로 등록이 됨 > 불변, 필수 의존관계에서 사용
    // 그래서 setter 주입은 생성자 주입보다 순서가 느리다. > 선택, 변경 가능성이 있는 의존관계에서 사용

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
        this.memberRepository = memberRepository;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);

    }
}
