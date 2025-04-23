/**
 * Author: Lucas Carpenter
 * Program #: 4 Task Management App
 * C243 - Data Structures
 * Date: 4/16/2025
 * Description: This Java program functions as a simple task management app that uses a priority queue to 
 * organize tasks based on their priority, where lower numbers mean higher priority. When tasks are added, 
 * the upheap method ensures the queue stays sorted, and when tasks are removed, the downheap method 
 * maintains the correct order. The app allows users to add new tasks, view the highest-priority task, 
 * and remove tasks once completed. It also handles errors like trying to peek or remove from an empty queue. 
 * A test class is included to demonstrate how tasks are added and removed in action.
 */

// The scanner class is going to allow the user to interact with the program in the terminal.
import java.util.Scanner;           // Import the scanner class.

// The PriorityQueue implements a priority queue ADT using an array to store the tasks and their priorities.
// The PriorityQueue class is a custom data structure that organizes tasks based on their priority, using an 
// array-backed implementation. Each task is assigned a numerical priority, with lower numbers representing 
// higher priority. When tasks are added, the class uses the upHeap method to maintain the correct order, 
// ensuring that the highest-priority task is always at the front. When tasks are removed, the downHeap method 
// reorganizes the remaining tasks to preserve the queue’s structure. This allows users to efficiently add, view, 
// and remove tasks in priority order, making it ideal for task management applications where urgent items 
// need to be handled first. The PriorityQueue class in this program manages tasks with methods like add(), remove(), 
// peek(), and heap maintenance methods such as upheap() and downheap() to efficiently maintain the heap property for 
// prioritized task management.
class PriorityQueue {
    static final int CAPACITY = 10; // FIXED CAPACITY OF THE QUEUE the capacity cannot change there can only be 10
                                    // tasks in the queue.
    Job[] queue;                    // the queue variable is an array of Job objects defined by the client or user
    int size = 0;                   // the size variable is an integer that will keep track of the number of tasks
                                    // in the queue.
    final int front = 0;            // the front of the queue will always be the task with the highest priority.
                                    // the front of the queue is always at index 0 in the array.

    // Constructor for the PriorityQueue class that initializes the queue array and size.
    // the queue will always be set to a fixed capacity of 10(as specified by the professor).
    // the size will always be set to 0 when the queue is initialized.
    public PriorityQueue() {
        queue = new Job[CAPACITY]; // Initialize the queue array that will serve as a queue ADT with the fixed
                                   // capacity defined by the professor to be 10.
        size = 0;                  // Initialize the size of the queue to 0.
    }
    // this method is used to check if the queue is empty. It returns true if the queue is empty and false otherwise.
    public boolean isEmpty() {
        return size == 0;           // return true if the size of the queue's size is currently 0(empty), otherwise
                                    // return false.
    }
    // this method is used to check if the queue is full. It returns true if the queue is full and false otherwise.
    public int size() {
        return size;                // return the size of the queue.
    }

    // this method is used to clear the queue of all tasks. It sets the size of the queue to 0 and 
    // sets each element in the queue to null.
    public void clear() {
        for (int i = 0; i < size; i++) {
            queue[i] = null;        // set each element in the queue to null to clear the queue.
        }
        size = 0;                   // set the size of the queue to 0 to indicate that the queue is empty.
    }

    // this method is used to add a task to the queue. It takes in a task name and priority as parameters.
    // this method uses upheap to restore the heap property after insertion. upheap could have been inside 
    // the enqueue method but it is separated out to make the code more readable. This method relies on the 
    // Job class to create a new job object with the task and priority.CAPACITY is a constant that is defined 
    // at the top of the class. It is used to check if the queue is full. this method relies on the isEmpty 
    // method to check if the queue is empty before calling the enqueue method.
    void enqueue(String taskName, int priority) {
        if (size == CAPACITY) {  // Check if the queue is full
            System.out.println("Queue is full. Cannot enqueue.");
            return;  // Exit the method if the queue is full
        }
    
        // Create a new job object with the task and priority
        Job task = new Job(taskName, priority);
    
        // Add the new task at the end of the heap
        queue[size] = task;  // Insert the task at the current position (size)
    
        size++;  // Increment size to reflect that a new task has been added
    
        // Call upheap to restore the heap property after insertion
        upheap(size - 1);  // Pass the index of the newly added task
    }
    
    

    // be sure to check if the queue is empty before calling this method.
    // This method removes the task with the highest priority from the queue (the
    // first element in the array) this method relies on upheap and downheap method
    // to return the correct task to the user. the task that this method returns is
    // the task with the highest priority in the queue. or the lowest numerical
    // value priority.
    public String dequeue() {
        Job removedJob = queue[0];              // Store the job at the front of the queue (highest priority)
    
        queue[0] = queue[--size];               // Replace the root (highest priority) with the last job in the heap
        queue[size] = null;                     // Set the last element to null to remove the reference
        
        downheap(0);                          // Restore the heap property starting from the root
        
        return removedJob.getTaskName();        // Return the task name of the removed job
    }

    // return the priority of the task with the highest priority without removing it
    // from the queue.
    // User MUST check if queue is empty before calling this method.
    // This method will print the task with the highest priority without removing it
    // from the queue AND will not disclose the actual priority of the task.
    // So long as upheap/downheap are implemented correctly, this method will work
    // as intended otherwise without a correctly implemented priority queue this
    // method will not work as intended.
    public int peekPriority() {
        if (queue[front] == null) {             // Check if the queue is empty.
            // If the queue is empty, print an error message and return -1 to 
            // indicate that there are no tasks in the queue.
            System.out.println("TASK LIST EMPTY\n\nADD A TASK TO THE QUEUE");
            return -1;                          // Exit the method if the queue is empty.

        }
        return queue[front].getPriority();      // return the priority of the task with the highest priority 
                                                // without removing it from the queue. the Job at the front 
                                                // of the priority queue is the task with the highest priority. 
                                                // Always so long as upheap/downheap are implemented correctly.
    }

    // this method peekTask is used to return the task with the highest priority
    // without removing it from the queue. There is error handleing done in this method
    // because in PqTest class there is no redundent method that checks if the queue is empty before calling this method.
    // this method will print the task with the highest priority without removing it from the queue.
    public String peekTask() {
        if (queue[front] == null) {
            return "TASK LIST EMPTY\n\nADD A TASK TO THE QUEUE";

        }
        return queue[front].getTaskName();      // return the task with the highest priority without removing it from the
                                                // queue.
    }

    // auxiliary methods for the enqueue and dequeue methods
    int[] a = new int[100];                     // array representing the heap (indexing starts at 1)
    int N = 0;                                  // number of elements currently in the heap
    int intMax = Integer.MAX_VALUE;             // sentinel value used in upheap. The program 4 specifications require
                                                // a intMax variable.

    // Restores heap order by moving element at index k downward
    void downheap(int k) {
        int j, v;                               // j is the index of the child, v is the value at index k
        v = a[k];                               // store the value at index k
        
        // The comments here reflect the structure of the while loop
        // this while loop is used to move the element at index k down the heap until
        // it is in the correct position. The while loop will continue until 
        // the element at index k is larger than both of its children.
        while (k <= N / 2) {                // while k has at least one child
            j = k + k;                          // j is left child

            if (j < N && a[j] < a[j + 1])       // if right child exists and is greater than left child
                j++;                                // j becomes index of larger child

            if (v >= a[j])                      // if current value is larger than both children
                break;                              // stop

            a[k] = a[j];                        // move larger child up
            k = j;                              // move down to child's position
        }

        a[k] = v;                               // place the original value in its final position
    }

    // Restores heap order by moving element at index k upward
    // this method is used to move the element at index k up the heap until it is in the correct position.
    // The while loop will continue until the element at index k is smaller than its parent.
    // this method relies on the getPriority method to get the priority of the task at index k.
    void upheap(int k) {
        int v;                                                      // v is the value at index k 
                                                                    // (as specified by the next line)
        v = queue[k].getPriority();                                 // Store the priority of the task
        queue[0] = new Job("", Integer.MAX_VALUE);  // Sentinel value, to simplify comparison 
                                                                    // (not necessary, but done for consistency)
        
        while (queue[k / 2].getPriority() <= v) {                   // Compare the priority of 
                                                                    // the current node with its parent
            queue[k] = queue[k / 2];                                // Move the parent down
            k = k / 2;                                              // Move up to the parent
        }
        
        queue[k] = queue[k];                                        // Place the task at the correct position 
                                                                    // (it will already be in the right spot)
    }
    

    /*
     * Variable Explanations:
     * 
     * a: the array representing the heap; holds the integer priorities
     * N: the number of elements currently in the heap
     * intMax: a large constant used as a sentinel value (prevents going out-of-bounds in upheap)
     * k: the index of the element to be moved (moved up or down)
     * v: the value originally at index k, which is being repositioned
     * j: used in downheap; represents the index of the child (either left or right) being compared
     */

}
// this class is used to test the PriorityQueue class. It is a simple test class that allows the user to 
// interact with the program in the terminal.it has a menu that allows the user to add tasks, remove tasks, 
// peek at the most urgent task, peek at the priority of the most urgent task, clear the queue, get the size 
// of the queue, and check if the queue is empty.this class is used to test the PriorityQueue class. It is a 
// simple test class that allows the user to interact with the program in the terminalit is important because 
// it allows the user to see how the program works and to test the functionality of the PriorityQueue class.
public class pqTest {
    static PriorityQueue pq = new PriorityQueue();      // Create a new priority queue object.
    static Scanner in = new Scanner(System.in);         // Create a new scanner object to read user input.

    public static void main(String[] args) {
        int choice;                               // create a variable(int) to store the user's choice.
        do {                                      // Repeat until the user chooses to exit the program(when user inputs '0').       
            choice = printMenu();                 // Print the menu and get the user's choice.
            runChoice(choice);                    // Run the user's choice in the method runChoice.
        } while (choice != 0);                          
    }

    // This method runs the user's choice based on the menu options. It serves as a
    // more readable alternative to a switch statement.
    // It checks the user's choice and calls the appropriate method to perform the
    // action.

    static void runChoice(int choice) {
        if (choice == 0)                        // If the user chooses to exit the program, 
                                                // print a goodbye message and return.
        
            // because this option only prints a message to the terminal no stand alone method is needed. 
            // This reduces redundancy. This is not the only option that does not call a method to 
            // perform an action.
            System.out.println("Goodbye!");   // tell user goodbye. The menu will no longer be printed when the user enters 0
        if (choice == 1)                        // If the user chooses to enqueue a task, call the enqueueTask method.
            enqueueTask();                      // Enqueue a task.
        if (choice == 2)                        // If the user chooses to dequeue a task, call the dequeueTask method.
            dequeueTask();                      // Dequeue a task.
        if (choice == 3)                        // If the user chooses to peek at the most urgent task, 
                                                // call the peekTask method. Peek at the most urgent task.
            System.out.println("most urgent task: " + pq.peekTask());                                       
        if (choice == 4)                        // If the user chooses to peek at the priority of the most urgent task, 
                                                // call the peekPriority method. Peek at the priority of the most urgent task.
            System.out.println("priority of most urgent task: " + pq.peekPriority());
        if (choice == 5)                        // If the user chooses to clear the queue, call the clearQueue method.
            clearQueue();                       // Clear the queue.
        if (choice == 6)                        // If the user chooses to get the size of the queue, 
                                                // call the printSize method.
            printSize();                        // Get queue size.
        if (choice == 7)                        // If the user chooses to check if the queue is empty, 
                                                // call the printIsEmpty method.
            printIsEmpty();                     // Check if queue is empty.
    }

    // this method prints the menu and returns the user's choice. Its purpose is to help me make a more readble program.
    // instead of using a switch or a series of if statements to run the user's choice.
    // This method is called in the main method to print the menu and get the user's choice.
    static int printMenu() {                                      // Here are the options that the menu for 
                                                                  // the user to interact with in the terminal.
        System.out.println("\nMenu:");                          // Print the menu.
        System.out.println("0) EXIT the program.");             // Exit the program.    
        System.out.println("1) Enqueue a task.");               // Enqueue a task.
        System.out.println("2) Dequeue a task.");               // Dequeue a task.
        System.out.println("3) Peek at the most urgent task."); // Peek at the most urgent task.
                                                                  // Peek at the priority of the most urgent task.
        System.out.println("4) Peek at the priority of the most urgent task."); 
        System.out.println("5) Clear the queue.");              // Clear the queue.
        System.out.println("6) Get queue size.");               // Get queue size.
        System.out.println("7) Check if queue is empty.");        // Check if queue is empty.
        System.out.print("Your choice: ");                        // Prompt the user for their choice.
        return in.nextInt();                                      // Get the user's choice from the menu.
    }

    // this method is called when the user chooses to enqueue a task.
    // It prompts the user for the task and its priority, then adds the task to the priority queue.
    // If the user enters a negative priority, the method will print an error message and return to the menu.
    // This method is called in the runChoice method when the user chooses to enqueue a task.
    // this user choice relies on the enqueue method to add the task to the queue. and the Scanner class to read user input.
    static void enqueueTask() {
        System.out.print("Enter task: ");                 // Prompt the user for the task.     
        String task = in.next();                            // Get the task from the user.
        System.out.print("Enter priority: ");             // Prompt the user for the priority.
        in.nextLine();                                      // Consume the newline character this 
                                                            // is necessary to avoid skipping the next input.
        int p = in.nextInt();                               // Get the priority from the user.    
        if (p < 0) {                                        // Check if the priority is negative.
            System.out.println("Priority must be a positive integer.\n\nReturning to menu.");
            return;                                         // If the priority is negative, print an error 
                                                            // message and return to the menu.
        }
        pq.enqueue(task, p);                                // Add the task to the priority queue with the 
                                                            // information provided by the user.
    }

    // This method is called when the user chooses to dequeue a task.
    // It removes the task with the highest priority from the queue and prints it to the console.
    // the highest priority is the lowest numerical value priority.
    // This method is called in the runChoice method when the user chooses to dequeue a task.
    // this user choice relies on the dequeue method to remove the task from the queue. This method 
    // seems rudundent but it has logic that is necessary to avoid errors. checking if the queue is 
    // empty before calling the dequeue method. this method relies on the isEmpty method to check if the queue is empty.
    static void dequeueTask() {
        if (!pq.isEmpty())                                          // Check if the queue is empty before calling dequeue.
            System.out.println("Removed: " + pq.dequeue());         // if the queue is not empty then dequeue the task with 
                                                                    // the highest priority and print it to the console.
        else
            System.out.println("Cannot dequeue from an empty queue.\n\nReturning to menu.");  // If the queue is empty, 
                                                                                                // print an error message and 
                                                                                                // return to the menu.
    }

    // This method is called when the user chooses to clear the queue.
    // It removes all tasks from the queue and prints a message to the console.
    // This method is called in the runChoice method when the user chooses to clear the queue.
    // this user choice relies on the clear method to remove all tasks from the queue.
    // this method relies on the isEmpty method to check if the queue is empty before calling the clear method. 
    // to save on processing time. The clear method is robust enough that it will not throw an error if 
    // the queue is already empty.
    static void clearQueue() {
        pq.clear();                                  // Clear the queue.
        System.out.println("Queue cleared.");        // Print a message to the console indicating 
                                                     // that the queue has been cleared.
    }

    // This method is called when the user chooses to get the size of the queue.
    // It prints the size of the queue to the console.
    // this method relies on the size method to get the size of the queue.
    // the size can be 0-10 depending on how many tasks are in the queue.
    static void printSize() {
        System.out.println("Size: " + pq.size()); // Print the size of the queue to the console.
    }

    // This method is called when the user chooses to check if the queue is empty.
    // this method relies on the isEmpty method to check if the queue is empty.
    // this method does not return anything it simply prints a message to the console. 
    static void printIsEmpty() {

        if (pq.isEmpty()) {                                               // Check if the queue is empty.
            System.out.println("Queue is empty.");                      // Print a message to the console 
                                                                          // if the queue is empty.
        } else {
            System.out.println("Queue contains some number of tasks."); // Print a message to the console if 
                                                                          // the queue is not empty.
        }
    }
}

// Job class description: 
// This class represents a job with a task name and priority.
// Examples of tasks or Jobs may be "Do laundry", "Clean room", "Finish homework", etc.
// The priority is an integer value that determines the order of the tasks in the queue. 
// And is represented by the variable priority in the class.the priority is a non negative 
// integer and the lower the number the higher the priority of the task. there are 2 instance 
// variables in this class: taskName(String) and priority(int). there is 1 constructor in 
// this class that takes in a task name and priority as parameters and initializes the instance variables.
// there are 4 methods in this class: getPriority(), getTaskName(), setPriority(int), and setTaskName(String).
class Job {
    private int priority;                           // the priority of the task is an integer value that determines 
                                                    // the order of the tasks in the queue.
    private String taskName;                        // the task name is a string that represents the task or job.


    // Constructor that takes in a task name and priority as parameters and initializes 
    // the instance variables(taskName and priority).
    public Job(String initialTaskName, int initialPriority) {
        taskName = initialTaskName;                 // Initialize the task name with the 
                                                    // formal parameter(initialTaskName).
        priority = initialPriority;                 // Initialize the priority with the 
                                                    // formal parameter(initialPriority).
    }

    // Getters and Setters for the instance variables(taskName and priority).
    // this method returns the priority of a instance of the job class.
    public int getPriority() {
        return priority;                            // Return the priority of the job.
    }
    // this method returns the task name of a instance of the job class.
    public String getTaskName() {
        return taskName;                            // Return the task name of the job.
    }

    // this method enables the Priority queue in this program the 
    // PQ as an array of instances of the job class.
    public void setPriority(int newPriority) {
        priority = newPriority;                     // Set the priority of the job to the 
                                                    // newPriority(formal parameter).
    }
    // this method sets the task name of a instance of the job class.
    public void setTaskName(String newTaskName) {
        taskName = newTaskName;                     // Set the task name of the job to 
                                                    // the newTaskName(formal parameter).
    }
}
