package dev.joint.library_management.service;

import dev.joint.library_management.service.impl.MemberServiceImpl;

import dev.joint.library_management.config.EnhancedObjectMapper;
import dev.joint.library_management.dto.MemberResponseDto;
import dev.joint.library_management.entity.Member;
import dev.joint.library_management.repository.MemberRepository;
import dev.joint.library_management.service.impl.MemberServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class MemberServiceTest {
    private final MemberRepository memberRepository =
            Mockito.mock(MemberRepository.class);

    private final EnhancedObjectMapper mapper =
            Mockito.mock(EnhancedObjectMapper.class);

    private final MemberServiceImpl memberService =
            new MemberServiceImpl(memberRepository, mapper);


    @Test
    void getMemberById_shouldReturnMember() {

        Member member = new Member(
                1,
                "Ali",
                "ali@gmail.com",
                "0500000000"
        );

        MemberResponseDto dto = new MemberResponseDto(
                1,
                "Ali",
                "ali@gmail.com",
                "0500000000"
        );


        Mockito.when(memberRepository.findById(1))
                .thenReturn(Optional.of(member));

        Mockito.when(mapper.convertValue(member, MemberResponseDto.class))
                .thenReturn(dto);


        MemberResponseDto result =
                memberService.getMemberById(1);


        assertEquals("Ali", result.getName());


        Mockito.verify(memberRepository)
                .findById(1);
    }
}
