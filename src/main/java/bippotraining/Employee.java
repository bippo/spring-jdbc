package bippotraining;

public class Employee {

    String employeeId;
    String employeeName;
    String employeeEmail;
    String employeeAddress;


    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }


    @Override
    public String toString() {
        return "[" + this.getEmployeeId() + ", " + this.getEmployeeName() + ", " + this.getEmployeeAddress() + ", " + this.getEmployeeEmail() + "]";
    }
}