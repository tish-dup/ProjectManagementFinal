import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class Project implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * Atributes
     */
    int projNum;
    String projName;
    String buildingType;
    String address;
    String erfNum;
    int totalFee;
    int amountPaid;
    int amountOut;
    Date deadline;
    Person contractor;
    Person architect;
    Person client;
    Date completionDate;
    boolean isFinalised;

    /**
     * 
     * @param projNum
     * @param projName
     * @param buildingType
     * @param address
     * @param erfNum
     * @param totalFee
     * @param amountPaid
     * @param deadline
     * @param contractor
     * @param architect
     * @param client
     */
    public Project(int projNum, String projName, String buildingType, String address, String erfNum, int totalFee,
            int amountPaid, Date deadline, Person contractor, Person architect, Person client) {

        this.projNum = projNum;
        this.projName = projName;
        this.buildingType = buildingType;
        this.address = address;
        this.erfNum = erfNum;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
        this.deadline = deadline;
        this.contractor = contractor;
        this.architect = architect;
        this.client = client;
    }

    /**
     * @return String // Gives the class Project an output.
     */
    public String toString() {
        DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        String output = "Project Number: " + projNum;
        output += "\nProject Name: " + projName;
        output += "\nType of building: " + buildingType;
        output += "\nAddress: " + address;
        output += "\nERF number: " + erfNum;
        output += "\nTotal fee for project: " + totalFee;
        output += "\nTotal amount paid to date: " + amountPaid;
        output += "\nDate when project is due: " + dateformat.format(deadline);

        // A string to represent the completion date. If the project hasn't been
        // completed, then it will be represented by a '-', otherwise a completion date
        // will be entered

        String completionDateString = "-";
        if (completionDate != null) {
            completionDateString = dateformat.format(completionDate);
        }

        output += "\nDate of completion: " + completionDateString;

        // Output to determine whether the project has been completed or not
        String isFinalisedString = "-";
        if (isFinalised) {
            isFinalisedString = "Yes";
        } else {
            isFinalisedString = "No";
        }

        output += "\nFinalised (yes/no): " + isFinalisedString;

        // Details of the contractor, architect and client
        output += "\nContractor: " + contractor;
        output += "\nArchitect: " + architect;
        output += "\nClient: " + client;

        return output;
    }

    /**
     * @param newDeadline // Method to add a new due date to the project
     */

    public void changeDeadline(Date newDeadline) {
        this.deadline = newDeadline;
    }

    /**
     * @param newAmmount // Method to add the new amount paid by the client
     */

    public void addAmount(int newAmmount) {
        this.amountPaid = amountPaid + newAmmount;
    }

    /**
     * @param completionDate
     * @return Invoice // To complete a project, the completion date is assigned to
     *         the project, the finalisation status is changed to true, and the
     *         oustanding amount is calculated for the invoice
     */
    public Invoice finalise(Date completionDate) {
        this.completionDate = completionDate;
        this.isFinalised = true;
        this.amountOut = totalFee - amountPaid;

        if (amountOut > 0) {
            return new Invoice(projNum, projName, client, amountOut, completionDate);
        } else {
            return null;
        }
    }

}
