package hello.hello_spring.service;

import hello.hello_spring.domain.Member;

import static org.assertj.core.api.Assertions.*;

import hello.hello_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        // given (입력 -> 준비)
        Member member = new Member();
        member.setName("hello");
        // when (실행)
        Long saveId = memberService.join(member);

        // then (검증)
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getId()).isEqualTo(findMember.getId());
    }

    @Test
    public void 중복_회원_에외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        assertThatThrownBy(() -> {
            memberService.join(member2);
        }).isInstanceOf(IllegalStateException.class).hasMessage("이미 존재하는 회원입니다.");

        /* try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/
        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findMember() {
    }
}