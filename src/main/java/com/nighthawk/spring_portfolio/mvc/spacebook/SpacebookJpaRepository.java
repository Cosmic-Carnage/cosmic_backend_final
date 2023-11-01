package com.nighthawk.spring_portfolio.mvc.spacebook;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpacebookJpaRepository extends JpaRepository<Spacebook, Long> {
    Optional<Spacebook> findByfileName(String fileName);
}
