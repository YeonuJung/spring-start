package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();

    // 테스트는 서로 순서없이 실행되기 때문에 의존관계가 생길 수 있음
    // 서로 영향을 받지 않기 위해서 AfterEach를 통해 각각의 테스트가 끝날 때마다
    // 정리작업을 실행해주어야 함
    // 테스트하고자 하는 객체에 정리 관련 메서드 정의해놓기
    @AfterEach
    public void afterEach(){
        memoryMemberRepository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        memoryMemberRepository.save(member);

        Member result = memoryMemberRepository.findById(member.getId()).get();
        System.out.println("result = " + (result == member));
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        memoryMemberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memoryMemberRepository.save(member2);


        Member result = memoryMemberRepository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        memoryMemberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memoryMemberRepository.save(member2);

        List<Member> result = memoryMemberRepository.findAll();

        assertThat(result.size()).isEqualTo(2);

    }
}
