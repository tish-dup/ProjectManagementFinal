import java.text.SimpleDateFormat;
import java.util.Date;

public class Invoice {
    // Atributes
    Person client;
    int amountOut;
    int projNum;
    String projName;
    Date completionDate;

    // Constructor
    public Invoice(int projNum, String projName, Person client, int amountOut, Date completionDate) {
        this.projNum = projNum;
        this.projName = projName;
        this.client = client;
        this.amountOut = amountOut;
        this.completionDate = completionDate;
    }

    // Methods
    // Output for the class Invoice
    public String toString() {
        String output = "Invoice";
        output += "\nCustomer Name: " + client.name;
        output += "\nProject Number: " + projNum;
        output += "\nProject Name: " + projName;
        output += "\nAmount due: R" + amountOut;
        output += "\nDate of completion: " + new SimpleDateFormat("dd/mm/yyy").format(completionDate) + "\n";

        return output;
    }
}
