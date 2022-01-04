package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository){
        //final 필드 초기화 체킹가능
        //@RequiredArgsConstructor로도 대체 가능(final 붙은 필드로만 생성자를 생성함)
        //필드 주입이 있음에도 생성자 주입을 한번 더 작성하는 이유? 테스트 작성 시 유연함을 위해
        this.memberRepository = memberRepository;
    }

    //회원가입
    @Transactional(readOnly = false)
    public Long join(Member member){
        ValidateDuplicateMember(member);    //중복회원검증
        return memberRepository.save(member);
    }

    private void ValidateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원 한명 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
