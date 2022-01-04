package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)    //테스트 실행 시 스프링과 엮어서 실행할래
@SpringBootTest                 //스프링 부트 컨테이너 안에서 테스트를 하고싶을 때 필요함
@Transactional                  //테스트 후 DB롤백
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입(){
        //given : 값이 주어지고
        Member member = new Member();
        member.setName("kim");

        //when : 아래와 같이 실행하면
        Long joinId = memberService.join(member);

        //then : 아래와 같은 결과가 나와야한다
        //assertEquals(expected, actual);
        assertEquals(member, memberRepository.findOne(joinId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);   //여기서 예외가 발생해야한다

        //then
        //member2에서 예외가 발생하지 않으면 아래 문장이 실행된다
        fail("예외가 발생해야 합니다");
    }
}