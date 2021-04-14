import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class ProjectRepository implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Assign each newly created project to an array
     */
    public ArrayList<Project> ProjectList = new ArrayList<Project>();

    /**
     * @return Project
     * @throws ParseException An example project is created for the user to use in
     *                        the program
     */

    public Project exampleProject() throws ParseException {
        Person willem = new Person("Contractor", "Willem du Plessis", "074 856 4561", "willem@dup.com",
                "452 Tugela Avenue");
        Person rene = new Person("Architect", "Rene Malan", "074 856 4561", "rene@malan", "452 Tugela Avenue");
        Person tish = new Person("Client", "Tish du Plessis", "074 856 4561", "tish@dup.com", "452 Tugela Avenue");
        Date deadline = Main.dateformat.parse("30/03/2021");

        return create(789, "House Plessis", "House", "123 Jasper Avenue", "1025", 20000, 5000, deadline, willem, rene,
                tish);
    }

    /**
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
     * @return Project // Method to create a new project
     */

    public Project create(int projNum, String projName, String buildingType, String address, String erfNum,
            int totalFee, int amountPaid, Date deadline, Person contractor, Person architect, Person client) {
        Project newproj = new Project(projNum, projName, buildingType, address, erfNum, totalFee, amountPaid, deadline,
                contractor, architect, client);
        this.ProjectList.add(newproj);
        return newproj;
    }

    /**
     * @param projNum
     * @return Project
     * @throws Exception // Method to get all the projects in the ArrayList
     *                   ProjectList.
     */

    public Project get(int projNum) throws Exception {
        for (Project proj : ProjectList) {
            if (proj.projNum == projNum) {
                return proj;
            }
        }

        throw new Exception("Project number " + projNum + " could not be found.");
    }

    /**
     * @param projectToUpdate
     * @throws Exception // Method to update the projects in the arrayList
     *                   ProjectList
     */

    public void update(Project projectToUpdate) throws Exception {
        for (Project listproj : ProjectList) {
            if (projectToUpdate.projNum == listproj.projNum) {
                listproj = projectToUpdate;
                return;
            }
        }

        throw new Exception("Project number " + projectToUpdate.projNum + " could not be found.");
    }

    /**
     * Method to write the projects to a binary file when the program is ended
     */
    public void writeToFile() {
        try {
            FileOutputStream outputFile = new FileOutputStream("Projectfile.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(outputFile);
            oos.writeObject(ProjectList);
            oos.close();

        } catch (IOException e) {
            System.out.println("Cannot load projects to file.");
        }
    }

    /**
     * Mehod to read the projects saved in a binary file
     */
    public void readFromFile() {

        try {
            FileInputStream inputFile = new FileInputStream("Projectfile.tmp");
            ObjectInputStream ois = new ObjectInputStream(inputFile);
            ProjectList = (ArrayList<Project>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("File cannot be found");
            // System.out.println("File cannot be found");
        }
    }

    /**
     * @return ArrayList<Project> Method to print uncompleted projects
     */
    public ArrayList<Project> uncompletedProj() {
        ArrayList<Project> uncompletedList = new ArrayList<Project>();
        for (Project proj : ProjectList) {
            if (!proj.isFinalised) {
                uncompletedList.add(proj);
            }
        }
        return uncompletedList;
    }

    /**
     * @return ArrayList<Project> Method to create a list of projects that are
     *         overdue
     */
    public ArrayList<Project> overdueProj() {
        java.util.Date date = new java.util.Date();
        ArrayList<Project> overdueList = new ArrayList<Project>();
        for (Project proj : ProjectList) {
            if (date.compareTo(proj.deadline) > 0) {
                overdueList.add(proj);
            }
        }
        return overdueList;
    }

}

// Reference:
// https://stackoverflow.com/questions/16111496/java-how-can-i-write-my-arraylist-to-a-file-and-read-load-that-file-to-the
// To save and read an ArrayList to and from a file as an object
// Reference: https://www.geeksforgeeks.org/compare-dates-in-java/
// To compare two dates with each other.
