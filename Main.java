import java.util.*;

class Employee {
    private String name;
    private int cyclesSurvived;

    public Employee(String name) {
        this.name = name;
        this.cyclesSurvived = 0;
    }

    public String getName() {
        return name;
    }

    public int getCyclesSurvived() {
        return cyclesSurvived;
    }

    public void incrementCycles() {
        cyclesSurvived++;
    }

    @Override
    public String toString() {
        return name + " (Cycles: " + cyclesSurvived + ")";
    }
}

class Company {

    // Permanent employees (founders + promoted employees)
    private List<Employee> permanentEmployees;

    // Replaceable employees
    private Queue<Employee> employeeQueue;

    // History of replaced employees
    private Stack<Employee> replacedEmployees;

    public Company() {
        permanentEmployees = new ArrayList<>();
        employeeQueue = new LinkedList<>();
        replacedEmployees = new Stack<>();
    }

    public void hire(Employee employee) {
        if (permanentEmployees.size() < 3) {
            permanentEmployees.add(employee); // founders
        } else {
            employeeQueue.offer(employee);
        }
    }

    public void replaceEmployee(Employee newHire) {

        if (employeeQueue.isEmpty()) {
            System.out.println("No replaceable employees available.");
            return;
        }

        // Remove oldest replaceable employee
        Employee removed = employeeQueue.poll();
        replacedEmployees.push(removed);

        System.out.println("Replaced: " + removed.getName()
                + " with " + newHire.getName());

        // Everyone remaining survives one more cycle
        int size = employeeQueue.size();

        for (int i = 0; i < size; i++) {
            Employee current = employeeQueue.poll();

            current.incrementCycles();

            if (current.getCyclesSurvived() >= 3) {
                permanentEmployees.add(current);

                System.out.println(
                    current.getName()
                    + " has survived 3 cycles and is now permanent!"
                );
            } else {
                employeeQueue.offer(current);
            }
        }

        // Add new hire to replaceable queue
        employeeQueue.offer(newHire);
    }

    public void displayEmployees() {

        System.out.println("\n===== PERMANENT EMPLOYEES =====");
        for (Employee employee : permanentEmployees) {
            System.out.println(employee.getName());
        }

        System.out.println("\n===== REPLACEABLE EMPLOYEES =====");
        for (Employee employee : employeeQueue) {
            System.out.println(employee);
        }
    }

    public void displayReplacementHistory() {

        System.out.println("\n===== REPLACEMENT HISTORY =====");

        for (int i = replacedEmployees.size() - 1; i >= 0; i--) {
            System.out.println(replacedEmployees.get(i).getName());
        }
    }
}

public class Main {

    public static void main(String[] args) {

        Company company = new Company();

        // Founders
        company.hire(new Employee("Alice"));
        company.hire(new Employee("Bob"));
        company.hire(new Employee("Charlie"));

        // Initial employees
        company.hire(new Employee("David"));
        company.hire(new Employee("Eve"));
        company.hire(new Employee("Frank"));

        company.displayEmployees();

        company.replaceEmployee(new Employee("Grace"));
        company.replaceEmployee(new Employee("Henry"));
        company.replaceEmployee(new Employee("Ivy"));
        company.replaceEmployee(new Employee("Jack"));

        company.displayEmployees();
        company.displayReplacementHistory();
    }
}

/*
# PROGRAM OUTPUT

 ===== PERMANENT EMPLOYEES =====
 Alice
 Bob
 Charlie
 
 ===== REPLACEABLE EMPLOYEES =====
 David (Cycles: 0)
 Eve (Cycles: 0)
 Frank (Cycles: 0)
 Replaced: David with Grace
 Replaced: Eve with Henry
 Replaced: Frank with Ivy
 Replaced: Grace with Jack
 
 ===== PERMANENT EMPLOYEES =====
 Alice
 Bob
 Charlie
 
 ===== REPLACEABLE EMPLOYEES =====
 Henry (Cycles: 2)
 Ivy (Cycles: 1)
 Jack (Cycles: 0)
 
 ===== REPLACEMENT HISTORY =====
 Grace
 Frank
 Eve
 David
 
  ----jGRASP: Operation complete.
 
  ----jGRASP exec: java Main
 
 ===== PERMANENT EMPLOYEES =====
 Alice
 Bob
 Charlie
 
 ===== REPLACEABLE EMPLOYEES =====
 David (Cycles: 0)
 Eve (Cycles: 0)
 Frank (Cycles: 0)
 Replaced: David with Grace
 Replaced: Eve with Henry
 Replaced: Frank with Ivy
 Replaced: Grace with Jack
 
 ===== PERMANENT EMPLOYEES =====
 Alice
 Bob
 Charlie
 
 ===== REPLACEABLE EMPLOYEES =====
 Henry (Cycles: 2)
 Ivy (Cycles: 1)
 Jack (Cycles: 0)
 
 ===== REPLACEMENT HISTORY =====
 Grace
 Frank
 Eve
 David

*/