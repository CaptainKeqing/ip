import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections.*;
public class Lindi {
    private final static ArrayList<Task> taskList = new ArrayList<>(10);
//    private final static Task[] taskList = new Task[100];
//    private static int taskListCount = 0;

    private static void parseInputAndExecute(String userInput) {
        class LocalFuncs {
            void executeList(){
                taskList.forEach(System.out::println);
                if (taskList.size() == 0) {
                    System.out.println("There are no tasks for you today. Enjoy :)");
                }
            }

            void executeCreateTask(String s) {
                Task task;
                try{
                    task = Task.create(s);
                } catch (CreateTaskException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                try {
                    taskList.add(task);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Oh no! I can't handle that many tasks yet.. shutting down :(((((");
                    throw new RuntimeException(e.getMessage());
                }
                System.out.println("Got it. I've added this task:\n\t" + task +
                                   "\nNow you have " + taskList.size() + " tasks in the list.");
            }

            /**
             * Prints a goodbye message and Exits the program (return code 0)
             */
            void goodByeAndExit() {
                System.out.println("Bye. Hope to see you again soon!");
                printSeparator();
                System.exit(0); // exit with code 0, terminates program
            }

            /**
             * @param listIndex: listIndex as shown by the `list` command, starts from 1
             */
            void executeMark(int listIndex) {
                // Index starts from 1, up to 100
                if (listIndex < 1 || listIndex > taskList.size()) {
                    System.out.println("Sorry, I can't find that task. Please enter a valid index\n" +
                            "You can see the tasks list by inputting \"list\"");
                    return;
                }
                Task task = taskList.get(listIndex - 1);
                task.markDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("\t" + task);
            }
            /**
             * @param listIndex: listIndex as shown by the `list` command, starts from 1
             */
            void executeUnmark(int listIndex) {
                // Index starts from 1, up to 100
                if (listIndex < 1 || listIndex > taskList.size()) {
                    System.out.println("Sorry, I can't find that task. Please enter a valid index\n" +
                            "You can see the tasks list by inputting \"list\"");
                    return;
                }
                Task task = taskList.get(listIndex - 1);
                task.unmarkDone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("\t" + task.toString());
            }
        }
        // if command called, will be index 0, rest of string in index 1
        String[] userInputTokens = userInput.split(" ", 2); 
        LocalFuncs lf = new LocalFuncs();

        switch (userInputTokens[0]) {
            case "list":
                lf.executeList();
                break;
            case "unmark":
                lf.executeUnmark(Integer.parseInt(userInputTokens[1]));
                break;
            case "mark":
                lf.executeMark(Integer.parseInt(userInputTokens[1]));
                break;
            case "todo":
            case "event":
            case "deadline":
                lf.executeCreateTask(userInput);
                break;
            case "bye":
                lf.goodByeAndExit();
                break;
            default:
                System.out.println("Uhh, English please? Haha, just kidding...\n" +
                        "but for reals I didn't really understand that :(");
                break;
        }
    }


    /**
     * Prints a horizontal line for better readability
     */
    private static void printSeparator() {
        System.out.println("-".repeat(50));
    }

    /**
     * Prints a greeting message.
     */
    private static void greeting() {
        String name = "Lindi";  // Log It N Do It -> LINDI

        printSeparator();
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");
        printSeparator();
    }



    private static void chatLoop() {
        Scanner scanner = new Scanner(System.in);
        String userInput;
        while (true) { // This will not be an infinite loop, because goodByeAndExit() terminates the program when called
            userInput = scanner.nextLine();
            printSeparator();
            parseInputAndExecute(userInput);
            printSeparator();
        }
    }

    private static boolean isNumber(String s) {
        try {
            Integer.valueOf(s);
            System.out.println("true number");
            return true;
        } catch (NumberFormatException e) {
            System.out.println("false number");
            return false;
        }
    }
    public static void main(String[] args) {
        greeting();
        chatLoop();
    }
}
