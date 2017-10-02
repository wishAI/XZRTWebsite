package com.wishai.xzrtw.repository;


import com.wishai.xzrtw.model.File;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileRepository extends JpaRepository<File, Integer> {
    @Query("select f.id from File f where f.srcKey=?1 and f.type=?2")
    public Integer findIdBySrcKeyAndType(String srcKey, String type);

}
