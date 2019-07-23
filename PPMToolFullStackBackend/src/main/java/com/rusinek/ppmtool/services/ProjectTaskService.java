package com.rusinek.ppmtool.services;

import com.rusinek.ppmtool.domain.Backlog;
import com.rusinek.ppmtool.domain.Project;
import com.rusinek.ppmtool.domain.ProjectTask;
import com.rusinek.ppmtool.exceptions.ProjectNotFoundException;
import com.rusinek.ppmtool.repositories.BacklogRepository;
import com.rusinek.ppmtool.repositories.ProjectRepository;
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

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        try {
            Backlog backlog = backlogRepository.findBacklogByProjectIdentifier(projectIdentifier);

            projectTask.setBacklog(backlog);

            Integer backlogSequence = backlog.getPTsequence();
            backlogSequence++;

            backlog.setPTsequence(backlogSequence);

            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            if (projectTask.getPriority() == null) {
                projectTask.setPriority(3);
            }

            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TO_DO");
            }

            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project not found");
        }

    }

    public Iterable<ProjectTask> findBacklogById(String backlog_id) {

        Project project = projectRepository.findByProjectIdentifier(backlog_id);

        if (project == null) {
            throw new ProjectNotFoundException("Project with ID: '" + backlog_id + "' does not exist.");
        }

        return projectTaskRepository.findProjectTasksByProjectIdentifierOrderByPriority(backlog_id);
    }

    public ProjectTask findPTByProjectSequence(String backlogId, String projectSequence) {

        Backlog backlog = backlogRepository.findBacklogByProjectIdentifier(backlogId);

        if (backlog == null) {
            throw new ProjectNotFoundException("Project with ID: '" + backlogId + "' does not exist.");
        }

        ProjectTask projectTask = projectTaskRepository.findProjectTaskByProjectSequence(projectSequence);

        if (projectTask == null) {
            throw new ProjectNotFoundException("Project Task '" + projectSequence + "' not found.");
        }

        if (!projectTask.getProjectIdentifier().equals(backlogId)) {
            throw new ProjectNotFoundException("Project Task '" + projectSequence +
                    "'does not exist in project: '" + backlogId);
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlogId,
                                               String ptId) {
        ProjectTask projectTask = findPTByProjectSequence(backlogId, ptId);

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlogId, String ptId) {
        ProjectTask projectTask = findPTByProjectSequence(backlogId, ptId);
        projectTaskRepository.delete(projectTask);
    }
}
