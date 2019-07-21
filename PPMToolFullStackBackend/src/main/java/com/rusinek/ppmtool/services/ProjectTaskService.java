package com.rusinek.ppmtool.services;

import com.rusinek.ppmtool.domain.Backlog;
import com.rusinek.ppmtool.domain.ProjectTask;
import com.rusinek.ppmtool.repositories.BacklogRepository;
import com.rusinek.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Adrian Rusinek on 21.07.2019
 **/
@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        Backlog backlog = backlogRepository.findBacklogByProjectIdentifier(projectIdentifier);

        projectTask.setBacklog(backlog);

        Integer backlogSequence = backlog.getPTsequence();
        backlogSequence++;

        backlog.setPTsequence(backlogSequence);

        projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        if(projectTask.getPriority() == null) {
            projectTask.setPriority(3);
        }

        if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }
}
