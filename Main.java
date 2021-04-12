import java.util.Scanner;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {
    // Scanner Method for user's input
    static Scanner input = new Scanner(System.in);

    // Global method to format date in an appropriate format
    static DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

    // Create instance of Project repository
    static ProjectRepository projRepo = new ProjectRepository();

    // Method to display a menu from which the user selects
    public static String menuSelection() throws ParseException {
        System.out.print("\nWelcome to Poised! \n1\t-\tAdd a new project \n2\t-\tChange due date of a project"
                + "\n3\t-\tChange payment on project \n4\t-\tUpdate contact details \n5\t-\tFinalise a project"
                + "\nPlease make your selection: ");

        // User's selection determine which method should be called
        // If user's selection is '1', then user can create a new project
        int userSelection = input.nextInt();
        input.nextLine();
        if (userSelection == 1) {
            createProject();
        }

        // If user selection '2', then can amend the due date of the project
        else if (userSelection == 2) {
            Project selectedProj = getProject();
            if (selectedProj == null) {
                return menuSelection();
            }

            newDeadline(selectedProj);
        }

        // If user selects '3', then user can change the payment on the project
        else if (userSelection == 3) {
            Project selectedProj = getProject();
            if (selectedProj == null) {
                return menuSelection();
            }

            makePayment(selectedProj);
        }

        // If user selects '4', then user can update the details of either the
        // contractor, client or architect.
        else if (userSelection == 4) {
            Project selectedProj = getProject();
            if (selectedProj == null) {
                return menuSelection();
            }

            updatePersonDetails(selectedProj);
        }

        // If user selects '5', then user can finalise the project
        else if (userSelection == 5) {
            Project selectedProj = getProject();
            if (selectedProj == null) {
                return menuSelection();
            }

            finalisation(selectedProj);
        }

        return menuSelection();
    }

    // Method to get user input as a string
    public static String getUserString(String message) {
        System.out.println(message);
        return input.nextLine();
    }

    // Method to get user input as an integer
    public static int getUserInt(String message) {
        System.out.println(message);
        int value = input.nextInt();
        input.nextLine();
        return value;
    }

    // Method to create a project by requesting for user's input for each instance
    // in the Project class.
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

    // CreatePerson method lets the user add the details of each person who works on
    // the project.
    static Person createPerson(String type, Scanner input) {
        System.out.println("\nPlease enter the details of the " + type + ": ");
        String name = getUserString("\nName: ");
        String contactNo = getUserString("\nContact number: ");
        String email = getUserString("\nEmail address : ");
        String address = getUserString("\nAddress: ");

        return new Person(type, name, contactNo, email, address);
    }

    // Method to get the project that the user wishes to work on. If there is no
    // project in the program an appropriate message will appear.
    public static Project getProject() {
        int projNum = getUserInt("\nPlease enter the project number: ");
        try {
            return projRepo.get(projNum);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return getProject();
    }

    // Method to change the due date of the project, by requesting for user's input
    // an changing the input in the appropriate format.
    public static void newDeadline(Project proj) throws ParseException {
        System.out.print("Please enter the new deadline for the project (dd/MM/yyyy): ");
        String snewDeadline = input.nextLine();
        try {
            Date newDeadline = dateformat.parse(snewDeadline);
            proj.changeDeadline(newDeadline);
            projRepo.update(proj);
        } catch (ParseException ex) {
            System.out.println("Date is not valid. Please try again.");
            newDeadline(proj);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            newDeadline(proj);
        }
    }

    // User can add the payments made for the project to the amount paid by the
    // client.
    public static void makePayment(Project proj) {
        int newAmmount = getUserInt("Add client's next payment: ");
        proj.addAmount(newAmmount);
        try {
            projRepo.update(proj);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to update a client, architect or contractor's details. User selects
    // the type of person he/she wishes to update and the program will prompt the
    // user for the new details.
    public static void updatePersonDetails(Project proj) {
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

    // To finalise a project, user needs to complete the date on which the project
    // has been finalised and an invoice will be generated
    public static void finalisation(Project proj) {
        if (proj.isFinalised) {
            System.out.println("Project already completed on " + dateformat.format(proj.completionDate));
            return;
        }

        String scomplete = getUserString("Please enter the date of completion for the project (dd/MM/yyyy): ");
        try {
            Date complete = dateformat.parse(scomplete);

            // If there is an outstanding amount on the project, an invoice will be
            // generated. If the project has been paid in full, the appropriate message will
            // display
            Invoice invoice = proj.finalise(complete);
            projRepo.update(proj);
            if (invoice != null) {
                System.out.print(invoice.toString());
            } else {
                System.out.print("Project complete and paid in full");
            }
        } catch (ParseException pex) {
            System.out.println("Date is not valid. Please try again.");
            finalisation(proj);
            return;
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }

    public static void main(String[] args) throws ParseException {
        // User has the choice to either use the example project in program or to
        // generate a new project to navigate through the program.
        String selection = getUserString("Would you like to use the example project? (y/n) ");
        if (selection.equals("y")) {
            Project example = projRepo.exampleProject();
            System.out.print(example.toString());
            menuSelection();
        } else {
            menuSelection();
        }
    }

}

// Reference:
// https://www.codegrepper.com/code-examples/java/taking+date+as+input+in+java
// To covert user's date input into readable format.