package com.example.library_managment_system.member;

import com.example.library_managment_system.book.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {
    private final MemberRepository members;

    public MemberService(MemberRepository members) {
        this.members = members;
    }

    @Transactional(readOnly = true)
    public List<Member> findAll() {
        return members.findAll();
    }

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return members.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id " + id));
    }

    public Member create(MemberRequest request) {
        if (members.existsByEmailIgnoreCase(request.email())) {
            throw new IllegalArgumentException("A member with this email already exists.");
        }
        return members.save(new Member(request.name(),request.email()));
    }

    public Member update(Long id, MemberRequest request) {
        Member member = findById(id);
        boolean emailUsedByAnotherMember = members.existsByEmailIgnoreCase(request.email())
                && !member.getEmail().equalsIgnoreCase(request.email());
        if (emailUsedByAnotherMember) {
            throw new IllegalArgumentException("A member with this email already exists.");
        }
        member.updateDetails(request.name(), request.email());
        return member;
    }

    public void delete(Long id) {
        members.deleteById(id);
    }
}
