package dev.nbcsparta.assignment.nbccloudassignment.member.service;

import dev.nbcsparta.assignment.nbccloudassignment.common.MemberNotFoundException;
import dev.nbcsparta.assignment.nbccloudassignment.member.domain.Member;
import dev.nbcsparta.assignment.nbccloudassignment.member.dto.MemberCreateRequest;
import dev.nbcsparta.assignment.nbccloudassignment.member.dto.MemberResponse;
import dev.nbcsparta.assignment.nbccloudassignment.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse createMember(MemberCreateRequest request) {
        Member member = new Member(
                request.name(),
                request.age(),
                request.mbti()
        );

        Member savedMember = memberRepository.save(member);
        return MemberResponse.from(savedMember);
    }

    @Transactional(readOnly = true)
    public MemberResponse getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));

        return MemberResponse.from(member);
    }
}
