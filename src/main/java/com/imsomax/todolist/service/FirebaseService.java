package com.imsomax.todolist.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.imsomax.todolist.model.Projects;
import com.imsomax.todolist.model.Tasks;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {
    public String saveProjectDetails(Projects project) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> collectionsDocumentReference = dbFirestore.collection("projects").add(project);//.document(project.getId()).set(project);
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("projects")
                .document(collectionsDocumentReference.get().getId()).update("id",collectionsDocumentReference.get().getId());
        return collectionsDocumentReference.get().getId();
    }

    public String saveTaskDetails(Tasks task) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> collectionsDocumentReference = dbFirestore.collection("projects").document(task.getProjectId())
                .collection("tasks").add(task);
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("projects").document(task.getProjectId())
                .collection("tasks").document(collectionsDocumentReference.get().getId()).update("id",collectionsDocumentReference.get().getId());
        System.out.println("update time stamp : " + collectionsDocumentReference.get().getId());
        return collectionsDocumentReference.get().getId();
    }

    public List<Tasks> viewTaskByProject(String projectId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = dbFirestore.collection("projects").whereEqualTo("id", projectId).get();

        QuerySnapshot querySnapshot = query.get();
        for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {

            Projects project = documentSnapshot.toObject(Projects.class);
            String id = documentSnapshot.getId();
            String name = project.getName();
            String description = project.getDescription();
        }

        List<Tasks> allTasks = new ArrayList<>();
        ApiFuture<QuerySnapshot> query1 = dbFirestore.collection("projects/"+projectId+"/tasks").get();
        QuerySnapshot querySnapshot1 = query1.get();
        for (QueryDocumentSnapshot documentSnapshot : querySnapshot1) {

            Tasks tasks = documentSnapshot.toObject(Tasks.class);
            String id = documentSnapshot.getId();
            String name = tasks.getName();
            boolean isCompleted = tasks.isCompleted();
            String projectId1 = tasks.getProjectId();
            allTasks.add(tasks);
        }
        return allTasks;
    }

    public List<Tasks> viewAllTasks() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = dbFirestore.collection("projects").get();

        QuerySnapshot querySnapshot = query.get();
        List<Tasks> allTasks = new ArrayList<>();
        for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {

            Projects project = documentSnapshot.toObject(Projects.class);
            String id = documentSnapshot.getId();
            String name = project.getName();
            String description = project.getDescription();
            System.out.println("ID: " + id + "\nTitle: " + name + "\nDescription: " + description + "\n");

            ApiFuture<QuerySnapshot> query1 = dbFirestore.collection("projects/"+id+"/tasks").get();
            QuerySnapshot querySnapshot1 = query1.get();
            for (QueryDocumentSnapshot tasksSnapshot : querySnapshot1) {

                Tasks tasks = tasksSnapshot.toObject(Tasks.class);

                String taskId = tasksSnapshot.getId();
                String taskName = tasks.getName();
                boolean isCompleted = tasks.isCompleted();
                String projectId1 = tasks.getProjectId();
                System.out.println("ID: " + taskId + "\nTitle: " + taskName + "\nisCompleted: " + isCompleted + "\nprojectId1: " + projectId1 );
                allTasks.add(tasks);
            }
        }

        return allTasks;
    }
}
