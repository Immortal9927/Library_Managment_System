package com.example.library_managment_system.member;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService service;
    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping
    public List<Member> getMembers() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Member createMember(@Valid @RequestBody MemberRequest member) {
        return service.create(member);
    }

    @PutMapping("/{id}")
    public Member updateMember(@PathVariable Long id, @Valid @RequestBody MemberRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@PathVariable Long id) {
        service.delete(id);
    }
}
