package study.querydsl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void basicTest() throws Exception {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // findByid 테스트
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        assertThat(findMember1).isEqualTo(member1);

        // findAll 테스트
        List<Member> all = memberRepository.findAll();
        assertThat(all).containsExactly(member1, member2);

        // findByUsername 테스트
        List<Member> findByUsername = memberRepository.findByUsername("member1");
        assertThat(findByUsername.get(0)).isEqualTo(member1);

    }
}
