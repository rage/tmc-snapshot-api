package fi.helsinki.cs.tmc.snapshot.api.service;

import fi.helsinki.cs.tmc.snapshot.api.model.Course;

import java.io.IOException;
import java.util.Collection;

public interface CourseService {

    Collection<Course> find(String instance, String username) throws IOException;

    Course find(String instance, String username, String course) throws IOException;

}
