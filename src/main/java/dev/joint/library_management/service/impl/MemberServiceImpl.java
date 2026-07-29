package dev.joint.library_management.service.impl;

import dev.joint.library_management.config.EnhancedObjectMapper;
import dev.joint.library_management.dto.MemberRequestDto;
import dev.joint.library_management.dto.MemberResponseDto;
import dev.joint.library_management.entity.Member;
import dev.joint.library_management.exception.ResourceNotFoundException;
import dev.joint.library_management.repository.MemberRepository;
import dev.joint.library_management.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final EnhancedObjectMapper enhancedObjectMapper;

    @Override
    public Page<MemberResponseDto> getAllMembers(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(member -> enhancedObjectMapper.convertValue(member, MemberResponseDto.class));
    }

    @Override
    public MemberResponseDto getMemberById(Integer id) {
        return enhancedObjectMapper.convertValue(memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found with id: " + id)), MemberResponseDto.class);
    }

    @Override
    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {
        return enhancedObjectMapper.convertValue(memberRepository.save(enhancedObjectMapper.convertValue(memberRequestDto, Member.class)), MemberResponseDto.class);
    }

    @Override
    public MemberResponseDto updateMember(Integer id, MemberRequestDto memberRequestDto) {
        Member existingMember = memberRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        existingMember.setName(memberRequestDto.getName());
        existingMember.setEmail(memberRequestDto.getEmail());
        existingMember.setPhone(memberRequestDto.getPhone());
        Member updatedMember = memberRepository.save(existingMember);
        return enhancedObjectMapper.convertValue(updatedMember, MemberResponseDto.class);
    }

    @Override
    public void deleteMember(Integer id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        memberRepository.deleteById(id);
    }
}


