package study.querydsl.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

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

    // searchByBuilder 테스트
    // 조건을 넣어서 member4를 가져와보자
    @Test
    public void searchByBuilderTest() throws Exception {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeLoe(40);
        condition.setAgeGoe(35);
        condition.setTeamName("teamB");

        // when
        List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(condition);

        // then
        assertThat(result).extracting("username").containsExactly("member4");
    }
    // 동적 쿼리 - where 다중 파라미터 테스트
    @Test
    public void search_test()throws Exception {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeLoe(40);
        condition.setAgeGoe(35);
        condition.setTeamName("teamB");

        // when
        List<MemberTeamDto> result = memberJpaRepository.search(condition);

        // then
        assertThat(result).extracting("username").containsExactly("member4");
    }

}