# ToDoList(Backend functionality using Cloud Firestore)


I have completed the assignment which was to make a basic ToDo list back-end functionality using Google Firebase (in Java) and Google Cloud Firestore database. There are 2 main entities – Projects and Tasks. Tasks will be organized inside projects. The basic fields for projects are Project name and description. The fields for tasks are task name, IsCompleted (Boolean), due date, and Owner (a person who the task is assigned to). I have built a database in Firestore with these entities and have written the following APIs in Firebase in Java which provides the following functionalities:
 
<br>1) ViewAllTasks – Shows all tasks for all projects.
<br>2) ViewTaskByProject – Takes in input a project name and shows the tasks for each project.
<br>3) CreateProject – Takes in the required input and creates a project.
<br>4) CreateTask – Takes in the required input and creates a task.
 
<br>This is the API which are made in Postman, can access this using this URL:-
<br>https://www.getpostman.com/collections/fafd8a253ac3e861ca00
