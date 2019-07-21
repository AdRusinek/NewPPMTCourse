package com.rusinek.ppmtool.services;

import com.rusinek.ppmtool.domain.Backlog;
import com.rusinek.ppmtool.domain.Project;
import com.rusinek.ppmtool.exceptions.ProjectIdException;
import com.rusinek.ppmtool.repositories.BacklogRepository;
import com.rusinek.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Adrian Rusinek on 17.07.2019
 **/
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }
            if (project.getId() != null) {
                project.setBacklog(backlogRepository.
                        findBacklogByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase()
                    + "' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectIdentifier) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project does not exists");
        }
        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectIdentifier) {
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Cannot delete Project with Project ID '" +
                    projectIdentifier + "'. This project does not exist.");
        }
        projectRepository.delete(project);
    }
}
