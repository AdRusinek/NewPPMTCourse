package com.rusinek.ppmtool.repositories;

import com.rusinek.ppmtool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog,Long> {

    Backlog findBacklogByProjectIdentifier(String projectIdentifier);

    List<Backlog> findByIdBetween(Integer val1, Integer val2);

}
