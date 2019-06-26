# Akses ke PostgreSQL menggunakan Spring JDBC

Totorial ini akan menjelaskan bagaimana cara mengakses Database menggunakan JDBC. Struktur direktori pada akhir tutorial akan seperti di bawah ini. Untuk memahami Spring Basic, silakan baca tutorial di sini:
[Spring IoC dan DI](https://github.com/bippo/spring-ioc-di)


```file
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── bippotraining
    │   │       ├── Application.java
    │   │       ├── Employee.java
    │   │       ├── EmployeeDAO.java
    │   │       ├── EmployeeDAOImpl.java
    │   │       └── EmployeeRowMapper.java
    │   └── resources
    │       ├── application.properties
    │       └── schema.sql
    └── test
        └── java
```

## 1. Menyiapkan Database
Siapkan database PostgreSQL, buat user training dengan query berikut:
```SQL
CREATE USER training WITH PASSWORD 'training';
```
Kemudian buat struktur data dengan query berikut:
```SQL
CREATE TABLE employee
(
  employeename varchar(100) NOT NULL,
  employeeid varchar(11) NOT NULL ,
  employeeaddress varchar(100) DEFAULT NULL,
  employeeemail varchar(100) DEFAULT NULL,
  PRIMARY KEY (employeeid)
);
```
## 2. Konfigurasi Spring
Konfigurasi diperlukan untuk memberikan informasi kepada Spring tentang lokasi database.
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/training
spring.datasource.username=training
spring.datasource.password=training
```

## 3. Akses ke Database
Untuk akses ke database, kita akan menggunakan kelas yang khusus, kelas ini akan kita namakan `EmployeeDAOImp` (implement interface `EmployeeDAO`). DAO (Data Access Object), sesuai namanya berarti objek yang digunakan untuk mengakses data. Untuk berkomunikasi dengan PostgreSQL, kita akan menggunakan library yang disediakan Spring yaitu `NamedParameterJdbcTemplate`. Kelas ini akan kita deklarasikan sebagai private variable dan kita tambahkan anotasi `@Autowired`.  Spring akan secara otomatis membuat object dari kelas ini dan melakukan konfigurasi agar kelas tersebut mengakses database sesuai dengan konfigurasi yang ada di `application.properties`.

Berikut contoh method untuk memasukkan data ke database:
```java
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
```
Pada method di atas, kita menggunakan SQL yang mempunyai parameter (:employeeId, :employeeName...) untuk memasukkan ke database. Kita mengirim SQL tersebut ke database dengan memanggil method `NamedParameterJdbcTemplate.update()`.

Untuk mengambil data ke database, kita gunakan metho berikut:
```java
@Override
public List<Employee> getEmployee() {
    return jdbcTemplate.query("select * from employee", new EmployeeRowMapper());
}
```
Pada method tersebut, hasil dari operasi query `select` akan dimapping oleh kelas `EmployeeRowMapper`.  Adapun method mapper sebagai berikut:
```java
@Override
public Employee mapRow(ResultSet rs, int arg1) throws SQLException {
    Employee emp = new Employee();
  emp.setEmployeeId(rs.getString("employeeId"));
  emp.setEmployeeName(rs.getString("employeeName"));
  emp.setEmployeeAddress(rs.getString("employeeAddress"));
  emp.setEmployeeEmail(rs.getString("employeeEmail"));
 return emp;
}
```

## 3. Aplikasi Utama
Melalui aplikasi utama, kita menguji apakah kelas DAO kita sudah berfungsi sebagaimana mestinya. Kelas utama sebagai berikut:
```java
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
  ....
}
```

Private variable employeeDAO kita instantiasi dengan menggunakan Spring DI melalui anotasi `@Autowired`.