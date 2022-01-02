package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    //영속성 컨텍스트 : 엔티티를 영구히 저장하는 환경
    private EntityManager em;
    //엔티티는 과자, 엔티티매니저는 아줌마, 엔티티매니저팩토리는 과자공장

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }

}
