import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private ArrayList<String> list;

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

            switch(input) {
                case "bye":
                    System.out.println("Bye. Hope to see you again soon!");
                    willExit = true;
                    break;
                case "list":
                    displayList();
                    break;
                default:
                    addToList(input);
                    System.out.println("added: " + input);
                    break;
            }

            if (willExit) {
                break;
            }
        }
        sc.close();
    }

    private void displayList() {
        int listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    private void addToList(String item) {
        list.add(item);
    }

    public static void main(String[] args) {
        Duke duke = new Duke();
        duke.run();
    }
}
