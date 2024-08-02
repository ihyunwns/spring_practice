package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Spring이 자동으로 컴포넌트 스캔할 때 빈으로 등록
@RequiredArgsConstructor
public class MemberRepository {

    //스프링이 JPA의 엔티티 매니저를 스프링이 생성한 엔티티 매니저를 주입해주는 어노테이션
/*  @PersistenceContext >> @Autowired 로 바꿀 수 있게 해줌 스프링 데이터 JPA가 지원해줌, 원래는 안됨
    private EntityManager em;

    public MemberRepository(EntityMnager em){
        this.em = em;
    }

    따라서 @RequiredArgsContructor 어노테이션으로 자동 생성자 자동 생성
*
* */

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
                                //SPQL ( SQL 문법이랑 비슷하지만 대상이 테이블이 아니라 엔티티가 된다. )
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("Select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
