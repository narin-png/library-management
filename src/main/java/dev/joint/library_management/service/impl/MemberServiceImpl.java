package dev.joint.library_management.service.impl;

import dev.joint.library_management.dto.MemberRequestDto;
import dev.joint.library_management.dto.MemberResponseDto;
import dev.joint.library_management.repository.MemberRepository;
import dev.joint.library_management.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    @Override
    public List<MemberResponseDto> getAllMembers() {
        return List.of();
    }

    @Override
    public MemberResponseDto getMemberById(Integer id) {
        return null;
    }

    @Override
    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {
        return null;
    }

    @Override
    public MemberResponseDto updateMember(Integer id, MemberRequestDto memberRequestDto) {
        return null;
    }

    @Override
    public void deleteMember(Integer id) {

    }
}
