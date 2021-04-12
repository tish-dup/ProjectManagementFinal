import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class ProjectRepository {

    // Assign each newly created project to an array
    public ArrayList<Project> ProjectList = new ArrayList<Project>();

    // An example project is created for the user to use in the program
    public Project exampleProject() throws ParseException {
        Person willem = new Person("Contractor", "Willem du Plessis", "074 856 4561", "willem@dup.com",
                "452 Tugela Avenue");
        Person rene = new Person("Architect", "Rene Malan", "074 856 4561", "rene@malan", "452 Tugela Avenue");
        Person tish = new Person("Client", "Tish du Plessis", "074 856 4561", "tish@dup.com", "452 Tugela Avenue");
        Date deadline = Main.dateformat.parse("30/03/2021");

        return create(789, "House Plessis", "House", "123 Jasper Avenue", "1025", 20000, 5000, deadline, willem, rene,
                tish);
    }

    public Project create(int projNum, String projName, String buildingType, String address, String erfNum,
            int totalFee, int amountPaid, Date deadline, Person contractor, Person architect, Person client) {
        Project newproj = new Project(projNum, projName, buildingType, address, erfNum, totalFee, amountPaid, deadline,
                contractor, architect, client);
        this.ProjectList.add(newproj);
        return newproj;
    }

    public Project get(int projNum) throws Exception {
        for (Project proj : ProjectList) {
            if (proj.projNum == projNum) {
                return proj;
            }
        }

        throw new Exception("Project number " + projNum + " could not be found.");
    }

    public void update(Project projectToUpdate) throws Exception {
        for (Project listproj : ProjectList) {
            if (projectToUpdate.projNum == listproj.projNum) {
                listproj = projectToUpdate;
                return;
            }
        }

        throw new Exception("Project number " + projectToUpdate.projNum + " could not be found.");
    }
}
