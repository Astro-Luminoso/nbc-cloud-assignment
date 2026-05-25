package dev.nbcsparta.assignment.nbccloudassignment.member.repository;

import dev.nbcsparta.assignment.nbccloudassignment.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
