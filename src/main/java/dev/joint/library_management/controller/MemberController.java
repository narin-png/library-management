package dev.joint.library_management.controller;

import dev.joint.library_management.dto.MemberRequestDto;
import dev.joint.library_management.dto.MemberResponseDto;
import dev.joint.library_management.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "Member", description = "Member management APIs")
public class MemberController {
    private final MemberService memberService;
    @GetMapping
    @Operation(summary = "Get all members")
    public ResponseEntity<Page<MemberResponseDto>> getAllMembers(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ResponseEntity.ok(memberService.getAllMembers(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get member by id")
    public ResponseEntity<MemberResponseDto> getMemberById(@PathVariable Integer id) {
        MemberResponseDto memberDto = memberService.getMemberById(id);
        return ResponseEntity.ok(memberDto);
    }

    @PostMapping
    @Operation(summary = "Create member")
    public ResponseEntity<MemberResponseDto> createMember(@Valid @RequestBody MemberRequestDto memberDto) {
        MemberResponseDto createdMember = memberService.createMember(memberDto);
        return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update member")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable Integer id, @Valid @RequestBody MemberRequestDto memberDto) {
        MemberResponseDto updatedMember = memberService.updateMember(id, memberDto);
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete member")
    public ResponseEntity<Void> deleteMember(@PathVariable Integer id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
