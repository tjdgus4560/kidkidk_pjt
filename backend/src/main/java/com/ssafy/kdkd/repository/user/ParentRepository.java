package com.ssafy.kdkd.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.kdkd.domain.entity.user.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long> {
}
