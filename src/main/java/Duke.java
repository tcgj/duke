import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    protected ArrayList<Task> list;

    public Duke() {
        list = new ArrayList<>();
    }

    public void run() {
        String helloText = "Hello! I'm Duke\nWhat can I do for you?";
        System.out.println(helloText);

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            boolean willExit = false;
            String[] input = sc.nextLine().split("\\s+", 2);
            String[] details;

            switch(input[0]) {
                case "bye":
                    System.out.println("Bye. Hope to see you again soon!");
                    willExit = true;
                    break;
                case "list":
                    displayList();
                    break;
                case "done":
                    setTaskDone(Integer.parseInt(input[1]));
                    break;
                case "todo":
                    addTask(new Todo(input[1]));
                    break;
                case "deadline":
                    details = input[1].split("\\s+\\/by\\s+", 2);
                    addTask(new Deadline(details[0], details[1]));
                    break;
                case "event":
                    details = input[1].split("\\s+\\/at\\s+", 2);
                    addTask(new Event(details[0], details[1]));
                    break;
                default:
                    break;
            }

            if (willExit) {
                break;
            }
        }
        sc.close();
    }

    protected void addTask(Task task) {
        list.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + list.size() + " tasks in the list.");
    }

    protected Task getTask(int taskNo) {
        return list.get(taskNo - 1);
    }

    protected void setTaskDone(int taskNo) {
        Task task = getTask(taskNo);
        task.markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    protected void displayList() {
        int listSize = list.size();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < listSize; i++) {
            System.out.println((i + 1) + "." + list.get(i));
        }
    }

    public static void main(String[] args) {
        Duke duke = new Duke();
        duke.run();
    }
}
