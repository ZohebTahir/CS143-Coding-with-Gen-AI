import java.util.*;

class Employee {
    private String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

class Company {
    private List<Employee> founders;
    private Queue<Employee> employees;
    private Stack<Employee> replacedEmployees;

    public Company() {
        founders = new ArrayList<>();
        employees = new LinkedList<>();
        replacedEmployees = new Stack<>();
    }

    // Hire an employee
    public void hire(Employee employee) {
        if (founders.size() < 3) {
            founders.add(employee);
        } else {
            employees.offer(employee);
        }
    }

    // Replace the oldest non-founder with a new hire
    public void replaceEmployee(Employee newHire) {
        if (!employees.isEmpty()) {
            Employee removed = employees.poll();
            replacedEmployees.push(removed);

            System.out.println("Replaced: " + removed +
                               " with " + newHire);

            employees.offer(newHire);
        } else {
            System.out.println("No non-founder employees available to replace.");
        }
    }

    public void displayEmployees() {
        System.out.println("\nCurrent Employees:");

        System.out.println("Founders:");
        for (Employee founder : founders) {
            System.out.println("  " + founder);
        }

        System.out.println("Other Employees:");
        for (Employee employee : employees) {
            System.out.println("  " + employee);
        }
    }

    public void displayReplacementHistory() {
        System.out.println("\nReplacement History (Most Recent First):");
        for (int i = replacedEmployees.size() - 1; i >= 0; i--) {
            System.out.println("  " + replacedEmployees.get(i));
        }
    }
}

public class Main {
    public static void main(String[] args) {

        Company company = new Company();

        // First 3 hires = founders
        company.hire(new Employee("Alice"));
        company.hire(new Employee("Bob"));
        company.hire(new Employee("Charlie"));

        // Regular employees
        company.hire(new Employee("David"));
        company.hire(new Employee("Eve"));
        company.hire(new Employee("Frank"));

        company.displayEmployees();

        // Cycle employees
        company.replaceEmployee(new Employee("Grace"));
        company.replaceEmployee(new Employee("Henry"));

        company.displayEmployees();
        company.displayReplacementHistory();
    }
}