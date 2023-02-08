package study.querydsl.repository;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static study.querydsl.entity.QMember.*;

@Repository // 순수 JPA로 리파지터리를 먼저 만들어보고
@RequiredArgsConstructor
public class MemberJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;


    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findById(Long id) {
        Member findMember = em.find(Member.class, id);
        return Optional.ofNullable(findMember);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m",
                        Member.class)
                        .getResultList();
    }

    // findAll을 querydsl로 리팩터링!
    public List<Member> findAll_querydsl() {
        return queryFactory
                .selectFrom(member)
                .fetch();
    }

    public List<Member> findByUsername(String username) {
        return em.createQuery("select m from Member m where m.username = :username",
                Member.class)
                .setParameter("username", username)
                .getResultList();
    }

    // findByUsername을 querydsl로 리팩터링!
    public List<Member> findByUsername_querydsl(String username) {
        return queryFactory.selectFrom(member)
                .where(member.username.eq(username))
                .fetch();
    }
}
