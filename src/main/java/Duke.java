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
            String input = sc.nextLine();
            String[] inputWords = input.split("\\s+");

            switch(inputWords[0]) {
                case "bye":
                    System.out.println("Bye. Hope to see you again soon!");
                    willExit = true;
                    break;
                case "list":
                    System.out.println("Here are the tasks in your list:");
                    System.out.println(getList());
                    break;
                case "done":
                    setTaskDone(Integer.parseInt(inputWords[1]));
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(getTask(Integer.parseInt(inputWords[1])));
                    break;
                default:
                    addTask(new Task(input));
                    System.out.println("added: " + input);
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
    }

    protected Task getTask(int taskNo) {
        return list.get(taskNo - 1);
    }

    protected void setTaskDone(int taskNo) {
        list.get(taskNo - 1).markAsDone();
    }

    protected String getList() {
        StringBuilder sb = new StringBuilder();
        int listSize = list.size();

        for (int i = 0; i < listSize; i++) {
            if (i != 0) {
                sb.append("\n");
            }
            sb.append((i + 1) + "." + list.get(i));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Duke duke = new Duke();
        duke.run();
    }
}
