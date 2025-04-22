/**
 * Author: Lucas Carpenter
 * Program #: 4
 * Date: 4/16/2025
 * Description: This program implements a priority queue to manage tasks based on their priority.
 */

// The scanner class is going to allow the user to interact with the program in the terminal.
import java.util.Scanner; // Import the scanner class.

// The PriorityQueue implements a priority queue ADT using an array to store the tasks and their priorities.
class PriorityQueue {
    static final int CAPACITY = 10; // FIXED CAPACITY OF THE QUEUE the capacity cannot change there can only be 10
                                    // tasks in the queue.
    Job[] queue; // the queue variable is an array of Job objects defined by the client or user
    int size = 0; // the size variable is an integer that will keep track of the number of tasks
                  // in the queue.
    int front = 0; // the front variable is an integer that will keep track of the front of the
                   // queue.
    int rear = 0; // the rear variable is an integer that will keep track of the rear of the
                  // queue.

    public PriorityQueue() {
        queue = new Job[CAPACITY]; // Initialize the queue array that will serve as a queue ADT with the fixed
                                   // capacity defined by the professor to be 10.
        size = 0; // Initialize the size of the queue to 0.
    }

    public boolean isEmpty() {
        return size == 0; // return true if the size of the queue's size is currently 0(empty), otherwise
                          // return false.
    }

    public int size() {
        return size; // return the size of the queue.
    }

    public void clear() {
        size = 0; // set the size of the queue to 0 to clear the queue (this forces the queue to
                  // overwrite itself**?).
    }

    /*
     * THIS NEXT SECTION MAY NEED TO BE CHANGED IT DEPENDS ON CIRCULAR QUEUE
     * IMPLEMENTATION IM NOT SURE RIGHT NOW
     */
    public void enqueue(String taskName, int priority) {
        if (size == CAPACITY) {
            System.out.println("Queue is full. Cannot enqueue.");
            return;
        }

        Job newJob = new Job(taskName, priority);

        int i = size - 1;
        // Move jobs with higher priority value (lower actual priority) to the right
        while (i >= 0 && queue[i].getPriority() > priority) {
            queue[i + 1] = queue[i];
            i--;
        }
        queue[i + 1] = newJob;
        size++;
    }

    public String dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty. Cannot dequeue.");
            return null;
        }
        Job removedJob = queue[0];
        for (int i = 1; i < size; i++) {
            queue[i - 1] = queue[i];
        }
        queue[--size] = null;
        return removedJob.getTaskName();
    }

    public String peek() {
        return ""; // return the task with the highest priority without removing it from the queue.
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
        if (queue[front] == null) {
            System.out.println("TASK LIST EMPTY\n\nADD A TASK TO THE QUEUE");
            return -1;

        }
        return queue[front].getPriority(); // return the priority of the task with the highest priority without removing
                                           // it from the queue.
    }

    public String peekTask() {
        if (queue[front] == null) {
            return "TASK LIST EMPTY\n\nADD A TASK TO THE QUEUE";

        }
        return queue[front].getTaskName(); // return the task with the highest priority without removing it from the
                                           // queue.
    }

    // auxiliary methods for the enqueue and dequeue methods
    int[] a = new int[100]; // array representing the heap (indexing starts at 1)
    int N = 0; // number of elements currently in the heap
    int intMax = Integer.MAX_VALUE; // sentinel value used in upheap

    // Restores heap order by moving element at index k downward
    void downheap(int k) {
        int j, v;
        v = a[k]; // store the value at index k

        while (k <= N / 2) { // while k has at least one child
            j = k + k; // j is left child

            // if right child exists and is greater than left child
            if (j < N && a[j] < a[j + 1])
                j++; // j becomes index of larger child

            if (v >= a[j])
                break; // if current value is in correct position, stop

            a[k] = a[j]; // move larger child up
            k = j; // move down to child's position
        }

        a[k] = v; // place the original value in its final position
    }

    // Restores heap order by moving element at index k upward
    void upheap(int k) {
        int v;
        v = a[k]; // store the value at index k
        a[0] = intMax; // sentinel at index 0 to prevent out-of-bounds

        // while parent is smaller than the current value
        while (a[k / 2] <= v) {
            a[k] = a[k / 2]; // move parent down
            k = k / 2; // move up to parent's position
        }

        a[k] = v; // place original value in final correct position
    }

    /*
     * Variable Explanations:
     * 
     * a: the array representing the heap; holds the integer priorities
     * N: the number of elements currently in the heap
     * intMax: a large constant used as a sentinel value (prevents going
     * out-of-bounds in upheap)
     * k: the index of the element to be "heapified" (moved up or down)
     * v: the value originally at index k, which is being repositioned
     * j: used in downheap; represents the index of the child (either left or right)
     * being compared
     */

}

public class PqTest {
    static PriorityQueue pq = new PriorityQueue(); // Create a new priority queue object.
    static Scanner in = new Scanner(System.in); // Create a new scanner object to read user input.

    public static void main(String[] args) {
        int choice; // create a variable(int) to store the user's choice.
        do {
            choice = printMenu(); // Print the menu and get the user's choice.
            runChoice(choice); // Run the user's choice in the method runChoice.
        } while (choice != 0); // Repeat until the user chooses to exit the program(when user inputs '0').
    }

    // This method runs the user's choice based on the menu options. It serves as a
    // more readable alternative to a switch statement.
    // It checks the user's choice and calls the appropriate method to perform the
    // action.

    static void runChoice(int choice) {
        if (choice == 0)
            exitProgram(); // Exit the program.
        if (choice == 1)
            enqueueTask(); // Enqueue a task.
        if (choice == 2)
            dequeueTask(); // Dequeue a task.
        if (choice == 3)
            System.out.println("most urgent task: " + pq.peekTask()); // Peek at the most urgent task.
        if (choice == 4)
            System.out.println("priority of most urgent task: " + pq.peekPriority()); // Peek at the priority of the
                                                                                      // most urgent task.
        if (choice == 5)
            clearQueue(); // Clear the queue.
        if (choice == 6)
            printSize(); // Get queue size.
        if (choice == 7)
            printIsEmpty(); // Check if queue is empty.
    }

    static int printMenu() {
        System.out.println("\nMenu:");
        System.out.println("0) EXIT the program.");
        System.out.println("1) Enqueue a task.");
        System.out.println("2) Dequeue a task.");
        System.out.println("3) Peek at the most urgent task.");
        System.out.println("4) Peek at the priority of the most urgent task.");
        System.out.println("5) Clear the queue.");
        System.out.println("6) Get queue size.");
        System.out.println("7) Check if queue is empty.");
        System.out.print("Your choice: ");
        return in.nextInt();
    }

    static void exitProgram() {
        System.out.println("Goodbye!");
    }

    static void enqueueTask() {
        System.out.print("Enter task: ");
        String task = in.next();
        System.out.print("Enter priority: ");
        in.nextLine(); // Consume the newline character this is necessary to avoid skipping the next
                       // input.
        int p = in.nextInt();
        pq.enqueue(task, p);
    }

    static void dequeueTask() {
        if (!pq.isEmpty())
            System.out.println("Removed: " + pq.dequeue());
        else
            System.out.println("Queue is empty.");
    }

    static void clearQueue() {
        pq.clear();
        System.out.println("Queue cleared.");
    }

    static void printSize() {
        System.out.println("Size: " + pq.size());
    }

    static void printIsEmpty() {

        if (pq.isEmpty()) {
            System.out.println("Queue is empty.");
        } else {
            System.out.println("Queue contains some number of tasks.");

        }
    }
}

class Job {
    private int priority;
    private String taskName;

    public Job(String initialTaskName, int initialPriority) {
        taskName = initialTaskName;
        priority = initialPriority;
    }

    public int getPriority() {
        return priority;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setPriority(int newPriority) {
        priority = newPriority;
    }

    public void setTaskName(String newTaskName) {
        taskName = newTaskName;
    }
}
