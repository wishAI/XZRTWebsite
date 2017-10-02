package com.wishai.xzrtw.repository.src;

import com.wishai.xzrtw.model.src.TextSrc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TextSrcRepository extends JpaRepository<TextSrc, Integer> {
}
