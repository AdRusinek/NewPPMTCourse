package com.rusinek.ppmtool.services;

import com.rusinek.ppmtool.domain.Project;
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

    public Project saveOrUpdateProject(Project project) {
        return projectRepository.save(project);
    }
}
