package com.rusinek.ppmtool.repositories;

import com.rusinek.ppmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Adrian Rusinek on 17.07.2019
 **/
@Repository
public interface ProjectRepository extends CrudRepository<Project,Long> {

}
