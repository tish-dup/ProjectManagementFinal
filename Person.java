import java.io.Serializable;

class Person implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * Attributes
     */
    String type;
    String name;
    String contactNo;
    String email;
    String address;

    /**
     * 
     * @param type
     * @param name
     * @param contactNo
     * @param email
     * @param address
     */
    public Person(String type, String name, String contactNo, String email, String address) {
        this.type = type;
        this.name = name;
        this.contactNo = contactNo;
        this.email = email;
        this.address = address;
    }

    /**
     * @return String // Output for the class Person
     */

    public String toString() {
        String output = "\nName: " + name;
        output += "\nType: " + type;
        output += "\nContact Details: " + contactNo;
        output += "\nEmail address: " + email;
        output += "\nResidentail Address: " + address;

        return output;
    }

    /**
     * @param contactNo
     * @param email
     * @param address   The contact details for either the client, contractor or
     *                  architect for the project is updated
     */
    public void updateDetails(String contactNo, String email, String address) {
        this.contactNo = contactNo;
        this.email = email;
        this.address = address;
    }
}
