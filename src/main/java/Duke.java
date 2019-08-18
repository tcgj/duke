import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
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
                default:
                    System.out.println(input);
                    break;
            }

            if (willExit) {
                break;
            }
        }
        sc.close();
    }
}
