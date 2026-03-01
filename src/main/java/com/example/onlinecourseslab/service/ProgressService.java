package com.example.onlinecourseslab.service;

import com.example.onlinecourseslab.domain.Progress;
import com.example.onlinecourseslab.domain.User;
import com.example.onlinecourseslab.domain.Lesson;
import com.example.onlinecourseslab.domain.Course;

import java.util.List;

public interface ProgressService {

    Progress markCompleted(User student, Lesson lesson);

    List<Progress> getByStudent(User student);

    List<Progress> getByStudentAndCourse(User student, Course course);

    Progress getByStudentAndLesson(User student, Lesson lesson);
}