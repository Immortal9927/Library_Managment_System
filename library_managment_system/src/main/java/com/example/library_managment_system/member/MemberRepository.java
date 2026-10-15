package com.example.library_managment_system.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmailIgnoreCase(String email);
}
