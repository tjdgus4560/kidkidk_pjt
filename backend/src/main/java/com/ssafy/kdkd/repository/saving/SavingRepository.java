package com.ssafy.kdkd.repository.saving;

import com.ssafy.kdkd.domain.entity.saving.Saving;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingRepository extends JpaRepository<Saving, Long> {
}
