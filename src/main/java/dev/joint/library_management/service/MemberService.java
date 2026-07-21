package dev.joint.library_management.service;

import dev.joint.library_management.dto.AuthorRequestDto;
import dev.joint.library_management.dto.AuthorResponseDto;
import dev.joint.library_management.dto.MemberRequestDto;
import dev.joint.library_management.dto.MemberResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {
    //List<MemberResponseDto> getAllMembers();
    Page<MemberResponseDto> getAllMembers(Pageable pageable);
    MemberResponseDto getMemberById(Integer id);
    MemberResponseDto createMember(MemberRequestDto memberRequestDto);
    MemberResponseDto updateMember(Integer id,MemberRequestDto memberRequestDto);
    void deleteMember(Integer id);

}
