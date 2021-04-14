import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

    /**
     * Scanner Method for user's input
     */

    static Scanner input = new Scanner(System.in);

    /**
     * Global method to format date in an appropriate format
     */
    static DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Create instance of Project repository
     */
    static ProjectRepository projRepo = new ProjectRepository();

    /**
     * @return String
     * @throws ParseException // Method to display a menu from which the user can
     *                        selects
     */

    public static String menuSelection() throws ParseException {
        System.out.println("""
                Welcome to Poised!
                1\t-\tAdd a new project
                2\t-\tChange due date of a project
                3\t-\tChange payment on project
                4\t-\tUpdate contact details
                5\t-\tFinalise a project
                6\t-\tView list of uncompleted projects
                7\t-\tView list of overdue Projects
                8\t-\tExit
                """);

        // User's selection determine which method should be called
        int userSelection = getUserInt("Please make your selection: ");

        switch (userSelection) {
        // If user's selection is '1', then user can create a new project
        case 1:
            createProject();
            break;

        // If user's selection is '2', then user can amend the due date of the project
        case 2:
            newDeadline();
            break;

        // If user selects '3', then user can change the payment on the project
        case 3:
            makePayment();
            break;

        // If user selects '4', then user can update the details of either the
        // contractor, client or architect.
        case 4:
            updatePersonDetails();
            break;

        // If user selects '5', then user can finalise the project
        case 5:
            finalisation();
            break;

        // User can view list of uncompleted projects by entering '6'
        case 6:
            ArrayList<Project> incomplete = projRepo.uncompletedProj();
            for (Project proj : incomplete) {
                System.out.println(proj.toString() + "\n");
            }
            break;

        // User can view list of overdue projects by entering '7'
        case 7:
            ArrayList<Project> overdue = projRepo.overdueProj();
            for (Project proj : overdue) {
                System.out.println(proj.toString() + "\n");
            }
            break;

        // User can exit program by entering '8'
        case 8:
            exitProgram();
            break;

        default:
            System.out.println("Incorrect selection. Please enter a valid option");
        }

        return menuSelection();
    }

    /**
     * @param message
     * @return String // Method to get user input as a string
     */

    public static String getUserString(String message) {
        System.out.println(message);
        return input.nextLine();
    }

    /**
     * @param message
     * @return int // Method to get user input as an integer
     */

    public static int getUserInt(String message) {
        try {
            System.out.println(message);
            int value = input.nextInt();
            input.nextLine();
            return value;
        } catch (Exception e) {
            input.nextLine();
            System.out.println("That is not a valid entry. Please enter a valid number");
            return getUserInt(message);
        }

    }

    /**
     * @throws ParseException // Method to create a project by requesting for user's
     *                        input for each instance in the Project class. If the
     *                        user did not enter a name for the project, then the
     *                        program will concatenate the building type and the
     *                        client's surname as the new project name.
     */

    public static void createProject() throws ParseException {

        int projNum = getUserInt("Please enter the project number: ");
        String projName = getUserString("Please enter the project name: ");
        String buildingType = getUserString("Please enter the type of building: ");
        String address = getUserString("Please enter the physical address for the project: ");
        String erfNum = getUserString("Please enter the ERF number: ");
        int totalFee = getUserInt("Please enter the total fee for the project: ");
        int amountPaid = getUserInt("Please enter the total amount paid to date: ");
        String sdeadline = getUserString("Please enter the deadline for the project (dd/MM/yyyy): ");
        Date deadline = dateformat.parse(sdeadline);

        Person architect = createPerson("architect", input);
        Person contractor = createPerson("contractor", input);
        Person client = createPerson("client", input);

        // If the user did not enter a name for the project, then the program will
        // concatenate the building type and the client's surname as the new project
        // name.
        if (projName == "") {
            String[] clientname = client.name.split(" ");
            String lastname = clientname[clientname.length - 1];
            projName = buildingType + " " + lastname;
        }

        projRepo.create(projNum, projName, buildingType, address, erfNum, totalFee, amountPaid, deadline, contractor,
                architect, client);
    }

    /**
     * @param type
     * @param input
     * @return Person // Method lets the user add the details of each person who
     *         works on the project.
     */

    static Person createPerson(String type, Scanner input) {
        System.out.println("\nPlease enter the details of the " + type + ": ");
        String name = getUserString("\nName: ");
        String contactNo = getUserString("\nContact number: ");
        String email = getUserString("\nEmail address : ");
        String address = getUserString("\nAddress: ");

        return new Person(type, name, contactNo, email, address);
    }

    /**
     * @return Project // Method to get the project that the user wishes to work on.
     *         If there is no project in the program an appropriate message will
     *         appear.
     */

    public static Project getProject() {
        int projNum = getUserInt("\nPlease enter the project number: ");
        try {
            return projRepo.get(projNum);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return getProject();
    }

    /**
     * @throws ParseException // Method to change the due date of the project, by
     *                        requesting for user's input and changing the input in
     *                        the appropriate format.
     */

    public static void newDeadline() throws ParseException {
        // Get the project number the user wishes to work on
        Project proj = getProject();

        System.out.print("Please enter the new deadline for the project (dd/MM/yyyy): ");
        String snewDeadline = input.nextLine();
        try {
            Date newDeadline = dateformat.parse(snewDeadline);
            proj.changeDeadline(newDeadline);
            projRepo.update(proj);

        } catch (ParseException ex) {
            System.out.println("Date is not valid. Please try again.");
            newDeadline();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            newDeadline();
        }
    }

    /**
     * User can add the payments made for the project to the amount paid by the
     * client.
     * 
     */

    public static void makePayment() {
        // Get the project number the user wishes to work on
        Project proj = getProject();
        int newAmmount = getUserInt("Add client's next payment: ");
        proj.addAmount(newAmmount);
        try {
            projRepo.update(proj);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to update a client, architect or contractor's details. User selects
     * the type of person he/she wishes to update and the program will prompt the
     * user for the new details.
     */

    public static void updatePersonDetails() {
        // Get the project number the user wishes to work on
        Project proj = getProject();

        String type = getUserString("Whose details do you want to update (contractor / architect / client): ");
        String contactNo = getUserString("\nContact number: ");
        String email = getUserString("\nEmail address : ");
        String address = getUserString("\nAddress: ");

        if (type == "architect") {
            proj.architect.updateDetails(contactNo, email, address);
        } else if (type == "contractor") {
            proj.contractor.updateDetails(contactNo, email, address);
        } else if (type == "client") {
            proj.client.updateDetails(contactNo, email, address);
        }

        try {
            projRepo.update(proj);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    /**
     * To finalise a project, user needs to complete the date on which the project
     * has been finalised and an invoice will be generated. Project will be stored
     * in it's own file. If the project has been paid in full, the appropriate
     * message will display
     */
    public static void finalisation() {
        // Get the project number the user wishes to work on
        Project proj = getProject();

        if (proj.isFinalised) {
            System.out.println("Project already completed on " + dateformat.format(proj.completionDate));
            return;
        }

        String scomplete = getUserString("Please enter the date of completion for the project (dd/MM/yyyy): ");
        try {
            Date complete = dateformat.parse(scomplete);

            // If there is an outstanding amount on the project, an invoice will be
            // generated. Project will be stored in it's own file
            // If the project has been paid in full, the appropriate message will
            // display
            Invoice invoice = proj.finalise(complete);
            projRepo.update(proj);
            if (invoice != null) {
                System.out.print(invoice.toString());

                ArrayList<Project> completedList = new ArrayList<Project>();
                completedList.add(proj);
                FileWriter writer = new FileWriter("Completed project.txt");
                writer.write(completedList + System.lineSeparator());
                writer.close();

            } else {
                System.out.print("Project complete and paid in full");
            }

        } catch (ParseException pex) {
            System.out.println("Date is not valid. Please try again.");
            finalisation();
            return;

        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }

    /**
     * @throws ParseException // Method to exit program and save projects
     */

    public static void exitProgram() throws ParseException {
        String answer = getUserString("Are you sure you want to exit program (y/n)");
        if (answer.equals("y")) {
            projRepo.writeToFile();
            System.exit(0);
        } else {
            menuSelection();
        }
    }

    /**
     * @param args
     * @throws ParseException Main Method - User has the choice to either use the
     *                        example project in program or to generate a new
     *                        project to navigate through the program.
     */
    public static void main(String[] args) throws ParseException {
        try {

            projRepo.readFromFile();
            String selection = getUserString("Would you like to use the example project? (y/n) ");
            if (selection.equals("y")) {
                Project example = projRepo.exampleProject();
                System.out.print(example.toString());
                menuSelection();
            } else {
                menuSelection();
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}

// Reference:
// https://www.codegrepper.com/code-examples/java/taking+date+as+input+in+java
// To covert user's date input into readable format.