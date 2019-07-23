package com.rusinek.ppmtool.repositories;

import com.rusinek.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    List<ProjectTask> findProjectTasksByProjectIdentifierOrderByPriority(String backlog_id);

    ProjectTask findProjectTaskByProjectSequence(String projectSequence);
}
