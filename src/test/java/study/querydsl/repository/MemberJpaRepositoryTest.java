package study.querydsl.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void basicTest() throws Exception {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // findByid 테스트
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        assertThat(findMember1).isEqualTo(member1);

        // findAll 테스트
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all).containsExactly(member1, member2);

        // findByUsername 테스트
        List<Member> findByUsername = memberJpaRepository.findByUsername("member1");
        assertThat(findByUsername.get(0)).isEqualTo(member1);

    }

    @Test
    public void basic_with_querydsl() throws Exception {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // findAll_querydsl 테스트
        List<Member> all = memberJpaRepository.findAll_querydsl();
        assertThat(all).containsExactly(member1, member2);

        // findByUsername_querydsl 테스트
        List<Member> findByUsername = memberJpaRepository.findByUsername_querydsl("member1");
        assertThat(findByUsername.get(0)).isEqualTo(member1);
    }

}