import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

public class FinalProject {
    public static void main(String[] args) {
        // Test code goes here
        Scanner scan = new Scanner(System.in);
        int ans = 0;
        int creditHr = 0;
        double gpa = 0.0;
        String name, ID;
        boolean x;
        ArrayList<Person> person = new ArrayList<>();

        System.out.println("Welcome to my Personal Management Program");

        while (ans != 8) {
            System.out.println("Choose one of the options:\n");
            System.out.println("1- Enter the information a faculty");
            System.out.println("2- Enter the information of a student");
            System.out.println("3- Print tuition invoice for a student");
            System.out.println("4- Print faculty information");
            System.out.println("5- Enter the information of a staff member");
            System.out.println("6- Print the information of a staff member");
            System.out.println("7- Delete a person");
            System.out.println("8- Exit Program\n");
            System.out.print("Enter your selection: ");

            try {
                ans = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection. Try again.");
                System.out.println();
                continue;
            }

            // System.out.println(ans);
            if (ans == 8) {
                System.out.print("Would you like to create the report? (Y/N):");
                String report = scan.nextLine().toLowerCase();
                System.out.println();

                if (report.equalsIgnoreCase("y")) {
                    System.out.print(
                            "Would like to sort your students by descending gpa or name (1 for gpa, 2 for name): ");
                    int sort = Integer.parseInt(scan.nextLine());
                    System.out.println();
                    ArrayList<Student> s = new ArrayList<>();
                    ArrayList<Faculty> f = new ArrayList<>();
                    ArrayList<Staff> staff = new ArrayList<>();

                    if (sort == 1 || sort == 2) {
                        for (Person p : person) {
                            if (p instanceof Student) {
                                s.add((Student) p);
                            } else if (p instanceof Faculty) {
                                f.add((Faculty) p);
                            } else if (p instanceof Staff) {
                                staff.add((Staff) p);
                            }
                        }
                        // Sorting by gpa
                        if (sort == 1) {
                            Collections.sort(s); // Default: Sort by GPA descending
                        }
                        // Sorting by name
                        else {
                            s.sort(Comparator.comparing(Student::getName).reversed());
                        }

                        // Write
                        String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
                        try (PrintWriter writer = new PrintWriter("report.txt")) {
                            writer.println("Report created on " + date);
                            writer.println("*********************************************");
                            writer.println();
                            String dept, ranks, state;
                            writer.println("Faculty Members");
                            writer.println("-----------------------------------------------");
                            int cnt = 1;
                            for (Faculty member : f) {
                                writer.println(cnt + ". " + member.getName());
                                writer.println("ID: " + member.getID());
                                dept = member.getDepartment();
                                dept = Character.toUpperCase(dept.charAt(0)) + dept.substring(1).toLowerCase();
                                ranks = member.getRank();
                                ranks = Character.toUpperCase(ranks.charAt(0)) + ranks.substring(1).toLowerCase();
                                writer.println(ranks + ", " + dept);
                                writer.println();
                                cnt++;
                            }

                            writer.println("Staff Members");
                            writer.println("-----------------------------------------------");
                            cnt = 1;
                            for (Staff member : staff) {
                                writer.println(cnt + ". " + member.getName());
                                writer.println("ID: " + member.getID());
                                dept = member.getDepartment();
                                dept = Character.toUpperCase(dept.charAt(0)) + dept.substring(1).toLowerCase();
                                state = member.getStatus();
                                state = Character.toUpperCase(state.charAt(0)) + state.substring(1).toLowerCase();
                                writer.println(dept + ", " + state);
                                writer.println();
                                cnt++;
                            }
                            writer.println();

                            String word;
                            if (sort == 1) {
                                word = "gpa";

                            } else {
                                word = "name";
                            }
                            writer.println("Students (Sorted by " + word + " in descending order)");
                            writer.println("-----------------------------------------------");
                            cnt = 1;
                            for (Student member : s) {
                                writer.println(cnt + ". " + member.getName());
                                writer.println("ID: " + member.getID());
                                writer.println("GPA: " + member.getGPA());
                                writer.println("Credit Hours: " + member.getCreditHr());
                                writer.println();
                                cnt++;
                            }
                            writer.println();

                            System.out.println("Report created and saved on your hard drive!");
                            System.out.println("Goodbye!");

                        } catch (Exception e) {
                            System.out.println("Error");
                        }

                    }
                    // Didnt pick 1 or 2
                    else {
                        while (sort != 1 || sort != 2) {
                            System.out.println("Pick 1 for sorting by gpa and 2 for name");
                            sort = Integer.parseInt(scan.nextLine());
                            System.out.println();
                        }
                    }

                } else if (report.equalsIgnoreCase("n")) {
                    System.out.println("Goodbye!");
                }
                // Didnt input y or n
                else {
                    System.out.println("Invalid entry.");
                    continue;
                }

                break;
            }
            // Would you like to create the report? (Y/N): y
            // Would like to sort your students by descending gpa or name (1 for gpa, 2 for
            // name): 1
            // Report created and saved on your hard drive!
            // Goodbye!

            if (ans == 1) {
                Faculty.addFaculty(scan, person);
            } else if (ans == 2) {
                Student.addStudent(scan, person);
            } else if (ans == 3) {
                Student.invoice(scan, person);
            } else if (ans == 4) {
                Faculty.printFacultyInfo(scan, person);
            } else if (ans == 5) {
                Staff.addStaff(scan, person);
            } else if (ans == 6) {
                Staff.printStaffInfo(scan, person);
            } else if (ans == 7) {
                System.out.print("Enter the id of the person to delete:");
                ID = scan.nextLine();
                System.out.println();
                boolean r = false;
                for (Person a : person) {
                    if (a.getID().equalsIgnoreCase(ID)) {
                        person.remove(a);
                        System.out.println("Person removed");
                        r = true;
                        break;
                    }
                }
                if (!r) {
                    System.out.println("No person has that ID");
                }
            }
        }

        scan.close();

    }

}

// -----------------------------------------------------
abstract class Person {
    public abstract void print();

    private String name, ID;

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Person(String name, String ID) {
        this.name = name;
        this.ID = ID;
    }

    // 2nd constructer
    public Person(String name) {
        this.name = name;
    }
}

// -----------------------------------------------------
class Student extends Person implements Comparable<Student> {
    private double gpa;
    private int creditHr;

    public double getGPA() {
        return gpa;
    }

    public int getCreditHr() {
        return creditHr;
    }

    public void setGPA(double gpa) {
        this.gpa = gpa;
    }

    public void setCreditHr(int creditHr) {
        this.creditHr = creditHr;
    }

    public Student(String name, String ID, double gpa, int creditHr) {
        super(name, ID);
        setGPA(gpa);
        setCreditHr(creditHr);
    }

    public Student(String name, String ID, double gpa) {
        super(name, ID);
        setGPA(gpa);
    }

    public void print() {
        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("%s, %s ", getName(), getID());
        System.out.printf("%.2f, %d", gpa, creditHr);
        System.out.println("---------------------------------------------------------------------------");
    }

    public static void invoice(Scanner scan, ArrayList<Person> person) {
        System.out.print("Enter the students id: ");
        String id = scan.nextLine();
        System.out.println();
        for (Person x : person) {
            if (x instanceof Student && x.getID().equalsIgnoreCase(id)) {
                Student y = (Student) x;
                double tuition = 52 + y.creditHr * 236.45;
                double finalTuition;
                double discount;
                if (y.gpa >= 3.85) {
                    finalTuition = tuition * 0.75;
                    discount = tuition * 0.25;
                } else {
                    finalTuition = tuition;
                    discount = 0.00;
                }
                System.out.printf("Here is the tuition invoice for %s: ", y.getName());
                System.out.println();
                System.out.println("---------------------------------------------------------------------------");
                System.out.println();
                System.out.println(y.getName() + "                    " + y.getID());
                System.out.println("Credit Hours: " + y.getCreditHr() + " ($236.45/credit hour)");
                System.out.println("Fees: 52");
                System.out.println("Total payment (after discount): $" + finalTuition
                        + "               ($" + discount + " discount applied)");
                System.err.println("---------------------------------------------------------------------------");
                System.out.println();
                return;
            }
        }
        System.out.println("Student does not exist");
    }

    public static void addStudent(Scanner scan, ArrayList<Person> person) {
        System.out.println("Enter the student info:");
        System.out.print("Name of Student:");
        String name = scan.nextLine();
        System.out.println();
        String ID = null;
        boolean x = false;
        while (!x) {
            System.out.print("ID: ");
            ID = scan.nextLine();
            System.out.println();
            if (ID.length() == 6
                    && Character.isLetter(ID.charAt(0))
                    && Character.isLetter(ID.charAt(1))
                    && Character.isDigit(ID.charAt(2)) && Character.isDigit(ID.charAt(3))
                    && Character.isDigit(ID.charAt(4)) && Character.isDigit(ID.charAt(5))) {

                boolean b = false;
                for (Person p : person) {
                    if (p.getID().equalsIgnoreCase(ID)) {
                        b = true;
                        System.out.println("ID already exists");
                        break;
                    }
                }

                if (!b) {
                    x = true;
                } else {
                    System.out.println("ID already exists");
                }
            }
            //
            else {
                System.out.println("Invalid ID format. Must be LetterLetterDigitDigitDigitDigit");
            }
        }

        x = false;
        Double gpa = 0.00;
        int creditHr = 0;
        while (!x) {
            try {
                System.out.print("Gpa: ");
                gpa = Double.parseDouble(scan.nextLine());
                x = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number");
            }
        }
        System.out.println();

        x = false;
        while (!x) {
            try {
                System.out.print("Credit Hours: ");
                creditHr = Integer.parseInt(scan.nextLine());
                x = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number");
            }
        }
        System.out.println();

        Person a = new Student(name, ID, gpa, creditHr);
        person.add(a);
        System.out.println("Student added!");
        System.out.println();
    }

    @Override
    public int compareTo(Student s) {
        return Double.compare(s.getGPA(), this.getGPA());
    }

}

// -----------------------------------------------------
abstract class Employee extends Person {
    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String dept) {
        this.department = dept;
    }

    public Employee(String name, String ID, String department) {
        super(name, ID);
        setDepartment(department);
        ;
    }

    public Employee(String name, String ID) {
        super(name, ID);

    }

    public abstract void print();
}

// ------------------------------------------------------
class Faculty extends Employee {
    private String rank;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Faculty(String name, String ID, String department, String rank) {
        super(name, ID, department);
        setRank(rank);
    }

    public Faculty(String name, String ID, String department) {
        super(name, ID, department);
    }

    public void print() {
        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("%s,           %s", getName(), getID());
        System.out.println();
        String dept = getDepartment();
        dept = Character.toUpperCase(dept.charAt(0)) + dept.substring(1).toLowerCase();
        System.out.printf("%s Department, %s", dept,
                (Character.toUpperCase(rank.charAt(0)) + rank.substring(1).toLowerCase()));

        System.out.println();
        System.out.println("---------------------------------------------------------------------------");

    }

    public static void addFaculty(Scanner scan, ArrayList<Person> person) {
        System.out.println("Enter the faculty info:");
        System.out.print("Name of the faculty:");
        String name = scan.nextLine();
        System.out.println();

        String ID = null;
        System.out.println();
        boolean x = false;
        while (!x) {
            System.out.print("ID: ");
            ID = scan.nextLine();
            System.out.println();
            if (ID.length() == 6
                    && Character.isLetter(ID.charAt(0))
                    && Character.isLetter(ID.charAt(1))
                    && Character.isDigit(ID.charAt(2)) && Character.isDigit(ID.charAt(3))
                    && Character.isDigit(ID.charAt(4)) && Character.isDigit(ID.charAt(5))) {

                boolean b = false;
                for (Person p : person) {
                    if (p.getID().equalsIgnoreCase(ID)) {
                        b = true;
                        break;
                    }
                }

                if (!b) {
                    x = true;
                } else {
                    System.out.println("ID already exists");
                }
            }
            //
            else {
                System.out.println("Invalid ID format. Must be LetterLetterDigitDigitDigitDigit");
            }
        }

        System.out.print("Rank: ");
        String rank = scan.nextLine().toLowerCase();
        System.out.println();
        x = false;
        while (!x) {
            if (rank.equals("professor") || rank.equals("adjunct")) {
                x = true;
            } else {
                System.out.println("Invalid choice. Must be professor or adjunct");
                System.out.print("Rank: ");
                rank = scan.nextLine().toLowerCase();
                System.out.println();
            }
        }

        System.out.print("Department: ");
        String department = scan.nextLine().toLowerCase();
        System.out.println();
        x = false;
        while (!x) {
            if (department.equals("mathematics") || department.equals("english") || department.equals("engineering")) {
                x = true;
            } else {
                System.out.println("Invalid choice. Must be engineering, english or mathematics");
                System.out.print("Department: ");
                department = scan.nextLine().toLowerCase();
                System.out.println();
            }
        }

        Person a = new Faculty(name, ID, department, rank);
        person.add(a);
        System.out.println("Faculty added!");

    }

    public static void printFacultyInfo(Scanner scan, ArrayList<Person> personList) {
        System.out.print("Enter the faculty's ID: ");
        String id = scan.nextLine();
        System.out.println();

        for (Person person : personList) {
            if (person instanceof Faculty && person.getID().equalsIgnoreCase(id)) {
                person.print();
                System.out.println();
                return;
            }
        }

        System.out.println("Faculty not found.");
    }

}

// ------------------------------------------------------
class Staff extends Employee {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Staff(String name, String ID, String department, String status) {
        super(name, ID, department);
        setStatus(status);
    }

    public Staff(String name, String ID, String department) {
        super(name, ID, department);
    }

    public void print() {
        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("%s,           %s", getName(), getID());
        System.out.println();
        String dept = getDepartment();
        dept = Character.toUpperCase(dept.charAt(0)) + dept.substring(1).toLowerCase();
        System.out.printf("%s Department, %s", dept,
                (Character.toUpperCase(status.charAt(0)) + status.substring(1).toLowerCase()));
        System.out.println();
        System.out.println("---------------------------------------------------------------------------");

    }

    public static void addStaff(Scanner scan, ArrayList<Person> person) {
        System.out.println("Enter the Staff info:");
        System.out.print("Name of the Staff:");
        String name = scan.nextLine();
        System.out.println();

        String ID = null;
        System.out.println();
        boolean x = false;
        while (!x) {
            System.out.print("ID: ");
            ID = scan.nextLine();
            System.out.println();
            if (ID.length() == 6
                    && Character.isLetter(ID.charAt(0))
                    && Character.isLetter(ID.charAt(1))
                    && Character.isDigit(ID.charAt(2)) && Character.isDigit(ID.charAt(3))
                    && Character.isDigit(ID.charAt(4)) && Character.isDigit(ID.charAt(5))) {

                boolean b = false;
                for (Person p : person) {
                    if (p.getID().equalsIgnoreCase(ID)) {
                        b = true;
                        break;
                    }
                }

                if (!b) {
                    x = true;
                } else {
                    System.out.println("ID already exists");
                }
            }
            //
            else {
                System.out.println("Invalid ID format. Must be LetterLetterDigitDigitDigitDigit");
            }
        }
        System.out.print("Department: ");
        String department = scan.nextLine().toLowerCase();
        System.out.println();
        x = false;
        while (!x) {
            if (department.equals("mathematics") || department.equals("english") || department.equals("engineering")) {
                x = true;
            } else {
                System.out.println("Invalid choice. Must be engineering, english or mathematics");
                System.out.print("Department: ");
                department = scan.nextLine().toLowerCase();
                System.out.println();
            }
        }

        System.out.print("Status, Enter P for Part Time, or Enter F for Full Time: ");
        String status = scan.nextLine().toLowerCase();

        System.out.println();
        x = false;
        while (!x) {
            if (status.equals("p") || status.equals("f")) {
                x = true;
                if (status.equals("p")) {
                    status = "Part Time";
                } else {
                    status = "Full Time";
                }
            } else {
                System.out.println("Invalid choice. Must be p or f");
                System.out.print("Status: ");
                status = scan.nextLine().toLowerCase();
                System.out.println();
            }
        }

        Person a = new Staff(name, ID, department, status);
        person.add(a);
        System.out.println("Staff member added!");
        System.out.println();

    }

    public static void printStaffInfo(Scanner scan, ArrayList<Person> personList) {
        System.out.print("Enter the Staff's ID: ");
        String id = scan.nextLine();
        System.out.println();

        for (Person person : personList) {
            if (person instanceof Staff && person.getID().equalsIgnoreCase(id)) {
                person.print();
                System.out.println();
                return;
            }
        }

        System.out.println("Staff not found.");
    }
}
