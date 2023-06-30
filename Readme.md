# StudentDB-SpringBoot-CRUD

<p align="center">
 <img alt="springboot" src="https://github.com/TharunBalaji2004/StudentDB-SpringBoot-CRUD/assets/95350584/b4d1c505-6c91-4c81-ad6a-18a6865ba0d5" height="110px" width="220px" />
 <img alt="springboot" src="https://github.com/TharunBalaji2004/StudentDB-SpringBoot-CRUD/assets/95350584/c944f170-17e4-479d-822e-2c1866cfbc94" height="150px" width="150px"" />
 <img alt="springboot" src="https://github.com/TharunBalaji2004/StudentDB-SpringBoot-CRUD/assets/95350584/94f4ef0a-8307-4f5d-8e67-881b7f730886" height="100px" width="200px" />
</p>

<p align="center">
<img alt="GitHub" src="https://img.shields.io/github/license/TharunBalaji2004/StudentDB-SpringBoot-CRUD">
<img alt="Static Badge" src="https://img.shields.io/badge/SpringBoot-passing-brightgreen?logo=springboot">
<img alt="GitHub last commit (by committer)" src="https://img.shields.io/github/last-commit/TharunBalaji2004/StudentDB-SpringBoot-CRUD?logo=github&color=%238A2BE2">
</p>


### Contents
- [Creating a project in SpringBoot](#springboot-project-creation)
- [SpringBoot RESTful Architecture](#springboot-restful-architecture)
   - [1. Creating Model Class](#1-creating-a-model-class)
   - [2. Creating a Controller Class](#2-creating-a-controller-class)
   - [3. Creating Service Class](#3-creating-service-class)
   - [4. Create Repository Interface](#4-create-repository-interface)
   - [5. Create instance of Repository in Service](#5-create-instance-of-repository-in-service)
   - [6. Connecting Controller to Service](#6-connecting-controller-to-service)
   - [7. Making HTTP requests to the Server](#7-making-http-requests-to-the-server)
- [Performing CRUD Operations](#performing-crud-operations)
   - [C: Create List of student data](#adding-list-of-students)
   - [R: Read student data using Id](#get-student-by-id)
   - [U: Update entire or specific fields](#update-student-by-id)
   - [D: Delete student data using Id](#delete-student-by-id)

## SpringBoot Project creation

![Pasted image 20230624202413](https://github.com/TharunBalaji2004/StudentDB-SpringBoot-CRUD/assets/95350584/bb726336-815f-4da3-a8bc-13f9ee416c54)

Spring Initializr 🌏 - https://start.spring.io/

- **Lombok** - A prominent Java library for annotations(@) and reduces boilerplate code

- **H2 Database** - Library for fast in-memory and internal database which supports JDBC API

- **Spring Data JPA** - It helps in managing Relational databases and allows to access and persist data between Java Classes and Relational database

- **Spring Web** - Useful library for building RESTful applications and Spring MVC

On creating the package and extracting, the java folder looks like this

```java
import org.springframework.boot.SpringApplication;  
import org.springframework.boot.autoconfigure.SpringBootApplication;  
  
@SpringBootApplication  
public class SpringbootpracticeApplication {  
  public static void main(String[] args) {  
    SpringApplication.run(SpringbootpracticeApplication.class, args);  
  }  
}
```

## SpringBoot RESTful Architecture

![image](https://github.com/TharunBalaji2004/StudentDB-SpringBoot-CRUD/assets/95350584/e5a83359-e554-4c33-82ee-7572274f5c0c)

1. **Controller** - acts as an entry point, and consists the path (Eg: addMethod(path))
2. **Service** - contains the implementation of method
3. **Repository** - saves the data fetched by the method
4. **Model** - the class which consists of data properties (also called Model or Encapsulation Class)

**Flow:** Controller ➡️ Service ➡️ Repository


## 1. Creating a Model Class

The Model Class should contain the data properties such as the datatype, constraints of data which is going to be stored in the database. Checkout the following example:

```java
public class StudentModel {  
  public String firstName;  
  public String lastName;  

  fun setFirstName(String firstName){
    this.firstName = firstName;
  }

  fun getFirstName(){
    return this.firstName;
  }

  fun setLastName(String lastName){
    this.lastName = lastName;
  }

  fun getLastName(){
    return this.lastName;
  }
}
```

Generally, a Java Model class consists of data members `firstName` and `lastName`, getter and setter methods to update and retrieve value of the object.
<br>

🤔 *As the data members start to increase in count, it becomes complex to implement getter and setter methods to each of the members.*

😎 *That's where Annotators(@) comes into the picture.*
<br>

Checkout the follow example, where the same Model Class is being implemented with the help of annotators:
```java
@Data
@Entity  
public class StudentModel {  
  public String firstName;  
  public String lastName;  
}
```

As we can see the Model class is being reduced only to the declaration of data members.

- **@Data** - By adding this annotator getters and setters need not be created manually
- **@Entity** - It represents the class as Model Class and maps the class to database

Since we will be using H2 Database for storing and fetching, the CRUD Operations require unique ID to be generated to each data stored. The generation of Id can be auto generated by adding @GeneratedValue annotator.

```java
@Data 
@Entity 
public class StudentModel {  
  @jakarta.persistence.Id  
  @GeneratedValue(strategy = GenerationType.IDENTITY)  
  private int Id;  
  private String firstName;  
  private String lastName;  
  private String email;  
  public String address;  
  private int age;  
}
```

- **@Id** - Represents the `Id` property as ID of data to be stored in database
- **@GeneratedValue** - On adding the annotator, the `Id` gets auto generated on adding data and indicates it as the primary key column
    - `GenrationType.IDENTITY` - The id gets auto incremented on inserting new data to database
    - `GenrationType.SEQUENCE` - The id gets randomly generated unique value on adding new data
    - `GenerationType.AUTO` - The id generation follows any strategy depending on the database


## 2. Creating a Controller Class

The Controller class acts as the main entry point of any requests sent to the server. A general Controller class is created as:

```java
@RestController
@RequestMapping("/student")
public class StudentController {
	
}
```

- **@RestController** - the annotator specifies that the class is a Controller type class
- **@RequestMapping(path)** - the annotator defines the main path to the Controller class
    - `"/"` - indicates the root path
    - `"/path"` - indicates the specified path

Now as the request enters into the main path, there may exist many subpaths which are represented as methods. The following code shows a method attached with sub-path that handles **POST** request.

```java
@RestController
@RequestMapping("/student")
public class StudentController {

  @PostMapping("/addstudent")
  public void addMethod() {}
  
  @GetMapping("/getall")
  public List<StudentModel> getaAll() {}
	
}
```

- **@PostMapping(path)** - the annotator specifies the sub-path `/addstudent` and the attached method `addMethod()` handles the **POST** request received through main path
- **@GetMapping(path)** - the annotator specifies the sub-path `/getall` and the attached method `getAll()` handles the **GET** request received through main path

🤔 _As we know that, Controller acts as main path and contains the sub-paths with respective methods. Then how do we implement the logic for those methods ?_

😎 _Here's where Service comes into the picture. The Service class contains only the implementation of the sub-path methods with connection to Repository (or) Database_

## 3. Creating Service Class

The Service class acts as a medium between Controller and Repository (or) Database. It consists of the implementations of methods and operations declared in Controller class. Here's the code of Service class:

```java
@Service
public class StudentService {

  public void createStudent() {}
  
  public List<StudentModel> getStudents() {}

}
```

- **@Service** - the annotator indicates the class as Service class

Now, the Service should create an instance of Repository to perform CRUD operations in database.

## 4. Create Repository Interface

Since all the CRUD operations to be performed are implemented by libraries such as Spring JPA, we have to create a Repository interface and extend with the pre built Repository interface. We will be using Spring JPA library to implement CRUD operations:

```java
@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Integer> {

}
```

The JPA Repository requires two generic arguments - `JpaRepository<T, ID>`
- T - the first argument should be the data model class i.e. `StudentModel`
- ID - the second argument represents the ID of data, which is an `Integer`

- **@Repository** - the annotator specifies the interface as Repository interface

## 5. Create instance of Repository in Service

As we have created all the required Controller class, Service class and Repository interface its time to create instances of each and call the methods.

The following code creates an object of `StudentRepository` and calls inside service method:

```java
@Service
public class StudentService {

  @Autowired
  StudentRepository studentRepository;

  public void createStudent(StudentModel studentModel) {
    studentRepository.save(studentModel);
  }

  public List<StudentModel> getStudents() {
    return studentRepository.findAll();
  }
	
}
```

- **@Autowired** - the annotator helps to create instance of the `StudentRepository`, i.e. object creation of any class (or) interface
- **.save(newdata)** - this method is available from StudentRepository from JPA Repository which performs the creation of data operation, and the `createStudent(model)` contains the new data as the argument passed from Controller class
- **.findAll()** - this method is available from StudentRepository from JPA Repository which performs the reading of entire data from the database, and does not require any parameters.

Note that, the `createStudent()` method does not returns anything, whereas the `getStudents()` method returns all the data stored in the form of `List<StudentModel>`

## 6. Connecting Controller to Service

As we implemented all the methods inside the Service class, now we have to call those methods in Controller class to perform operations. Here, the Controller class calls the suitable methods of Service class inside respective sub-path methods:

```java
@RestController
@RequestMapping("/student")
public class StudentController {

  @Autowired
  private StudentService studentService;

  @PostMapping("/addStudent")
  public String addMethod(@RequestBody StudentModel studentModel) {
    studentService.createStudent(studentModel);
    return "New student data added successfully!!";
  }

  @GetMapping("/getAll")
  public List<StudentModel> getAll() {
    return studentService.getStudents();
  }
}
```

- **@RequestBody** - the annotation tells Spring Boot to automatically deserialize the request body into the corresponding Java object `studentModel`

Here, the `addMethod()` sends the converted java object to `createStudent()` method from Service class, and return a String message as return. The `getAll()` method returns a list of `StudentModel` data by calling the `getStudents()` method from Service class.

| Controller | Service | Repository |
| :------: | :-------: | :--------: |
| addMethod(StudentModel) | createStudent(StudentModel) | save(StudentModel) |
| getAll() | getStudents() | findAll() |

## 7. Making HTTP requests to the Server

Since we have completed all the implementation part lets bootstrap the server and test by making sample HTTP requests. Generally the HTTP requests are given as:

![Pasted image 20230625222534](https://github.com/TharunBalaji2004/StudentDB-SpringBoot-CRUD/assets/95350584/c9d2ec82-3ecc-4117-8280-c8cc6af7f856)

We have implemented our Controller to handle **POST** and **GET** requests received by the server, to add new student data and retrieve all the data. Lets try playing around our server using Postman 📫. The following steps will help us to understand how to perform HTTP requests without using Frontend:

### 1. Build the project and run the Server

Once we have built the project and started running the application, the SpringBoot server gets setup and listens to `localhost: port` as shown below:


```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.1.1)
 
Tomcat started on port(s): 8080 (http) with context path

SpringbootpracticeApplication: Started SpringbootpracticeApplication in 4.42 seconds (process running for 4.776)
```

As we can see the SpringBoot server has been started, and listens to `localhost: 8080` with HTTP protocol on root path. Lets try opening using our browser:

![Pasted image 20230625223347](https://github.com/TharunBalaji2004/StudentDB-SpringBoot-CRUD/assets/95350584/257333dc-a238-4a05-a8ab-0a3f793305d4)

It shows as _Whitelabel Error Page_ because, we didn't sent any HTML or XML file as response to the request sent to server. We will not be requiring frontend for passing data, and that's where Postman comes into the picture.

### 2. Using Postman

![Pasted image 20230625223758](https://github.com/TharunBalaji2004/StudentDB-SpringBoot-CRUD/assets/95350584/4799e25d-274f-47f6-a244-251727f2d48e)

Lets perform **POST** operation, adding new student data to the database using our server.

| Sub path | CRUD Operation |
| :---: | :---: |
| /addstudent | POST |
| /getall | GET |

In order to add new student data, change `Body` and data type to `raw` and specify as `JSON`, now lets add single student data by specifying as JSON.

```json
{
  "firstName" : "Tharun",
  "lastName" : "Balaji"
}
```

On clicking **SEND** button, POST request along with the data entered would be sent to the server which is listening at `port: 8080`

![Pasted image 20230625224459](https://github.com/TharunBalaji2004/StudentDB-SpringBoot-CRUD/assets/95350584/d07a797f-deb3-4475-a076-02f6fe553337)

On successful handling of request, the string message is been returned as we have implemented in the Controller class.

Lets perform the **GET** operation after adding few number of student data as we done above. On sending the request, all the students data added to the database is being fetched as list:

![Pasted image 20230625225208](https://github.com/TharunBalaji2004/StudentDB-SpringBoot-CRUD/assets/95350584/f99eaec3-16af-4437-8ac0-71511f263edc)

```json
[
  {
    "firstName": "Tharun",
    "lastName": "Balaji"
  },
  {
    "firstName": "Harish",
    "lastName": "Balaji"
  }
]
```

A lot of operations can be implemented by using SpringBoot CRUD Repository operations.

### 3. Using H2 Database console

The H2 Database, stores the data in the form of Relational schema. The H2 Database also SQL for querying the data and fetch the desired results. In order to access H2 Database, the `action.properties` file in the project has to be specified with certain required information.

``` properties
server.port = 8080  
spring.datasource.url=jdbc:h2:mem:testdb  
spring.datasource.driver-class-name=org.h2.Driver  
spring.datasource.username=TharunBalaji  # Your desired name
spring.datasource.password=2004  # Your desired password
spring.h2.console.enabled=true  
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

On saving the file, and go to the browser where we opened the `localhost: 8080` initially, add an extra path as `http://localhost:8080/h2-console` and it redirects to the H2 Database console, where we can login to the database session with specified `username` and `password`

![Pasted image 20230625230019](https://github.com/TharunBalaji2004/StudentDB-SpringBoot-CRUD/assets/95350584/29129ae2-b3da-4a56-babb-e449b6ed40b9)

As we can see the H2 Database console is being logged in, and on the left side we can see out `StudentModel` as `STUDENT_MODEL` relational table and the data members as respective columns

![Pasted image 20230625230054](https://github.com/TharunBalaji2004/StudentDB-SpringBoot-CRUD/assets/95350584/70af9820-bab1-469d-8daf-2536a2b7c62e)

On querying the data, we can see that the data which was added using the HTTP requests with Postman are stored in the database under `STUDENT_MODEL` relational table.

## **Performing CRUD Operations**

We have have implemented all the necessary classes, it's time to implement operations to be performed.

### **Adding List of students**

In order to add list of students directly to the database, the sub-path method should accept List of `StudentModel` data to store, by sending **POST** request. Here's the example code:

**Controller class**

```java
@PostMapping("/addStudentList")  
public String addStudentList(@RequestBody List<StudentModelClass> studentList) {  
  studentService.addStudents(studentList);  
  return "List of students added successfully!!";  
}
```

**Service class**

```java
public void addStudents(List<StudentModelClass> studentList) {  
  studentRepository.saveAll(studentList);  
}
```

The `saveAll(model)` method from JPA Repository is used to store all the list of data.


### **Get student by Id**

In order to get the student from the database by using respective Id, then the desired `id` has to be passed in the sub-path itself, by sending **GET** request. Here's the implementation:

**Controller class**

```java
@GetMapping("/get/{id}")  
public StudentModelClass getStudent(@PathVariable int id) {  
  return studentService.getStudent(id);  
}
```

- **@PathVariable** - the annotation retrieves the value `id` passed in the sub-path

**Service class**

```java
public StudentModelClass getStudent(int id) {  
  return studentRepository.findById(id).orElse(null);  
}
```

- If the `id` is not present in the database, then `null` is being returned.

As we can see, the `findById(id)` method from JPA Repository is being used for finding the student data of respective `id` passed from Controller, is being fetched and returned.

### **Delete student by Id**

In order to delete student by Id, similar to get Student by id, the desired `id` has to be passed in the sub-path itself, by sending **GET** request. Here's an example:

**Controller class**

```java
@GetMapping("/delete/{id}")  
public String deleteStudent(@PathVariable int id) {  
  studentService.deleteStudent(id);  
  return "Student with Id: " + id + " deleted";  
}
```

**Service class**

```java
public void deleteStudent(int id) {  
  studentRepository.deleteById(id);  
}
```

As we can see, the `deleteById(id)` method from JPA Repository is being used for deleting the student data of respective `id` passed from Controller.

### **Update student by Id**

To update the data by using the student `id` stored in the database, usually **PUT** http request is sent to the server for updating the existing data of respective `id` . Here's an example code:

**Controller class**

```java
@PatchMapping("/update/{id}")  
public String updateStudent(@PathVariable int id, @RequestBody StudentModel studentModel){  
  boolean idFound = studentService.updateStudent(id, studentModel);  
  if (idFound) {  
      return "Student with Id: " + id + " updated";  
  } else {  
      return "Student with Id: " + id + " is unavailable";  
  }  
}
```

- The boolean variable `idFound` contains `true`, if the student data with `id` is available, otherwise it contains `false` as the method `updateStudent()` in Service class returns the result.

**Service class**

```java
public boolean updateStudent(int id, StudentModelClass studentModelClass) {  
  boolean idFound = false;  
  StudentModelClass oldData = null;  
  Optional<StudentModelClass> optional = studentRepository.findById(id);  
    
  if (optional.isPresent()) {  
      idFound = true;  
      oldData = optional.get();
        
      oldData.setFirstName(studentModelClass.getFirstName());  
      oldData.setLastName(studentModelClass.getLastName());  
      oldData.setAge(studentModelClass.getAge());  
      oldData.setAddress(studentModelClass.getAddress());  
      oldData.setEmail(studentModelClass.getEmail());  
      
      studentRepository.save(oldData);  
  }  
    
  return idFound;  
}
```

- **Optional&lt;div&gt;** - Optional is a container object in java, which may or may not contain a non-null value.
- **isPresent()** - This method checks whether the Optional object contains any value or not
- **get()** - This method returns the data model  `T` (_say StudentModel_) stored

🤔 _What if specific fields of data has only to be updated, instead of all fields ?_

😎 _In that case, check whether the fields of new data is null or not, update those fields which are not null_

The modified code for `updateStudent()` is given as:

```java
public boolean updateStudent(int id, StudentModelClass studentModelClass) {  
  boolean idFound = false;  
  StudentModelClass oldData = null;  
  Optional<StudentModelClass> optional = studentRepository.findById(id);  
    
  if (optional.isPresent()) {  
      idFound = true;  
      oldData = optional.get();  
      
      if (studentModel.getFirstName() != null) {
	oldData.setFirstName(studentModel.getFirstName());
      }
      if (studentModel.getLastName() != null) {
	oldData.setLastName(studentModel.getLastName());
      }
      if (studentModel.getAge() != 0) {
	oldData.setAge(studentModel.getAge());
      }
      if (studentModel.getAddress() != null) {
	oldData.setAddress(studentModel.getAddress());
      }
      if (studentModel.getEmail() != null) {
	oldData.setEmail(studentModel.getEmail());
      }  
        
      studentRepository.save(oldData);  
  }  
    
  return idFound;  
}
```

