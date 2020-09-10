package com.imsomax.todolist.controller;

import com.imsomax.todolist.model.Projects;
import com.imsomax.todolist.model.Tasks;
import com.imsomax.todolist.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class TodoController {
    @Autowired
    FirebaseService firebaseService;

    @PostMapping("/createProject")
    public String createProject(@RequestBody Projects projects) throws InterruptedException, ExecutionException {
        return firebaseService.saveProjectDetails(projects);
    }

    @PostMapping("/createTask")
    public String createTask(@RequestBody Tasks task) throws InterruptedException, ExecutionException {
        return firebaseService.saveTaskDetails(task);
    }

    @GetMapping("/viewTaskByProject")
    public List<Tasks> viewTaskByProject(@RequestHeader String projectId) throws InterruptedException, ExecutionException {
        return firebaseService.viewTaskByProject(projectId);
    }

    @GetMapping("/viewAllTasks")
    public List<Tasks> viewAllTasks() throws InterruptedException, ExecutionException {
        return firebaseService.viewAllTasks();
    }
}
