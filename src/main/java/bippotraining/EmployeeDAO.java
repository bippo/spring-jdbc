package bippotraining;

import java.util.List;

public interface EmployeeDAO {
    void addEmployee(Employee employee);
    void removeEmployee(String employeeId);
    void updateEemployee(Employee employee);
    List<Employee> getEmployee();
}
