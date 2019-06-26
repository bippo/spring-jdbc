package bippotraining;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application
        implements CommandLineRunner {

    @Autowired
    private EmployeeDAO employeeDao;

    public static void main(String...args){
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Employee emp = new Employee();
        emp.setEmployeeId("01");
        emp.setEmployeeAddress("San Jose");
        emp.setEmployeeEmail("john@playground.com");
        emp.setEmployeeName("John Doe");
        employeeDao.addEmployee(emp);

        Employee emp2 = new Employee();
        emp2.setEmployeeId("02");
        emp2.setEmployeeAddress("San Jose");
        emp2.setEmployeeEmail("john@playground.com");
        emp2.setEmployeeName("John Doe");
        employeeDao.addEmployee(emp2);

        for(Employee tmp: employeeDao.getEmployee()){
            System.out.println(tmp);
        }

        emp2.setEmployeeId("02");
        emp2.setEmployeeAddress("California");
        emp2.setEmployeeEmail("john@playground.com");
        emp2.setEmployeeName("John Doe");
        employeeDao.updateEemployee(emp2);

        for(Employee tmp: employeeDao.getEmployee()){
            System.out.println(tmp);
        }

        employeeDao.removeEmployee(emp.getEmployeeId());
        employeeDao.removeEmployee(emp2.getEmployeeId());

    }
}
