package com.rusinek.ppmtool.web;

import com.rusinek.ppmtool.domain.Project;
import com.rusinek.ppmtool.domain.ProjectTask;
import com.rusinek.ppmtool.services.MapValidationErrorService;
import com.rusinek.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Adrian Rusinek on 17.07.2019
 **/
@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService errorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {

        ResponseEntity<?> errorMap = errorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getProjectByProjectIdentifier(@PathVariable String projectIdentifier) {
        Project project = projectService.findProjectByIdentifier(projectIdentifier);

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects() {
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectIdentifier}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectIdentifier) {
        projectService.deleteProjectByIdentifier(projectIdentifier);

        return new ResponseEntity<String>("Project with Project Identifier '" + projectIdentifier
                + "' was deleted", HttpStatus.OK);
    }


}
