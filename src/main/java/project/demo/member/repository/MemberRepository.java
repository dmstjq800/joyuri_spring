package project.demo.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    Optional<Member> findByEmailToken(String token);
}
