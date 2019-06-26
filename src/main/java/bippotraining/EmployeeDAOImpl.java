package bippotraining;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeDAOImpl implements EmployeeDAO {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void addEmployee(Employee emp) {
        final String sql = "insert into employee(employeeId, employeeName , employeeAddress, employeeEmail) values(:employeeId,:employeeName,:employeeAddress,:employeeEmail)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("employeeId", emp.getEmployeeId())
                .addValue("employeeName", emp.getEmployeeName())
                .addValue("employeeEmail", emp.getEmployeeEmail())
                .addValue("employeeAddress", emp.getEmployeeAddress());
        jdbcTemplate.update(sql, param, holder);
    }

    @Override
    public void removeEmployee(String employeeId) {
        final String sql = "delete from employee where employeeId=:employeeId";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("employeeId", employeeId);
        jdbcTemplate.update(sql, param, holder);
    }

    @Override
    public void updateEemployee(Employee emp) {
        final String sql = "update employee set employeeName=:employeeName, employeeAddress=:employeeAddress, employeeEmail=:employeeEmail where employeeId=:employeeId";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("employeeId", emp.getEmployeeId())
                .addValue("employeeName", emp.getEmployeeName())
                .addValue("employeeEmail", emp.getEmployeeEmail())
                .addValue("employeeAddress", emp.getEmployeeAddress());
        jdbcTemplate.update(sql, param, holder);
    }

    @Override
    public List<Employee> getEmployee() {
        return jdbcTemplate.query("select * from employee", new EmployeeRowMapper());

    }
}
