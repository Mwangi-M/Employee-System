/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author MWANGI
 */

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.Statement;  
import java.sql.ResultSet;  
import java.util.ArrayList;  
import java.util.Map;  
import javax.faces.bean.ManagedBean;  
import javax.faces.bean.RequestScoped;  
import javax.faces.context.FacesContext;  
@ManagedBean  
@RequestScoped  

    public class User{  
                int id;  
                String firstname;
                String lastname;
                String email;  
                String phonenumber;  
                String hiredate;  
                String jobid;
                String salary;
                ArrayList usersList ;  
                private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();  
                Connection connection;  
  
                public int getId() {  
                    return id;  
                }  
                public void setId(int id) {  
                    this.id = id;  
                }
                
                public String getFirstName() {  
                    return firstname;  
                }  
                public void setFirstName(String firstname) {  
                    this.firstname = firstname;  
                }
                
                public String getLastName() {  
                    return lastname;  
                }  
                public void setLastName(String lastname) {  
                    this.lastname = lastname;  
                }
                
                public String getEmail() {  
                    return email;  
                }  
                public void setEmail(String email) {  
                    this.email = email;  
                }
                
                public String getPhoneNumber() {  
                    return phonenumber;  
                }  
                public void setPhoneNumber(String phonenumber) {  
                    this.phonenumber = phonenumber;  
                }
                
                public String getHireDate() {  
                    return hiredate;  
                }  
                public void setHireDate(String hiredate) {  
                    this.hiredate = hiredate;  
                }
                
                public String getJobId() {  
                    return jobid;  
                }  
                public void setJobId(String jobid) {  
                    this.jobid = jobid;  
                }
                
                public String getSalary() {  
                    return salary;  
                }  
                public void setSalary(String salary) {  
                    this.salary = salary;  
                }
                
                
                
                // Database Connection  
                public Connection getConnection(){  
                try{  
                Class.forName("com.mysql.jdbc.Driver");     
                connection = DriverManager.getConnection( "(MySQL)","root","Mwangi@254#");  
                }catch(Exception e){  
                System.out.println(e);  
                }  
                return connection;  
                } 
                
                // Fetch Employee Details  
                public ArrayList usersList(){  
                try{  
                    usersList = new ArrayList();  
                    connection = getConnection();  
                    Statement stmt=getConnection().createStatement();    
                    ResultSet rs=stmt.executeQuery("select * from employees");    
                    while(rs.next()){  
                    User employee = new User();  
                    employee.setId(rs.getInt("id"));  
                    employee.setFirstName(rs.getString("firstname"));  
                    employee.setLastName(rs.getString("lastname"));  
                    employee.setEmail(rs.getString("email"));  
                    employee.setPhoneNumber(rs.getString("phonenumber"));  
                    employee.setHireDate(rs.getString("hiredate"));  
                    employee.setJobId(rs.getString("jobid"));  
                    employee.setSalary(rs.getString("salary")); 
                    usersList.add(employee);  
                    }  
                    connection.close();          
                } catch(Exception e){  
                    System.out.println(e);  
                }  
                return usersList;  
                }
                
                  
                public String save(){  
                int result = 0;  
                try{  
                    connection = getConnection();  
                    PreparedStatement stmt = connection.prepareStatement(  
                    "insert into employees(firstname,lastname,email,phonenumber,hiredate,jobid,salary) values(?,?,?,?,?,?,?)");
                    stmt.setString(1, firstname);  
                    stmt.setString(2, lastname);  
                    stmt.setString(3, email);  
                    stmt.setString(4, phonenumber);  
                    stmt.setString(5, hiredate);  
                    stmt.setString(6, jobid);  
                    stmt.setString(7, salary);  
                    result = stmt.executeUpdate();  
                    connection.close();  
                } catch(Exception e){  
                System.out.println(e);  
                }  
                if(result !=0)  
                return "welcomePrimefaces.xhtml?faces-redirect=true";  
                else return "create.xhtml?faces-redirect=true";  
                }
                
                  
                public String edit(int id){  
                User employee = null;  
                System.out.println(id);  
                try{  
                    connection = getConnection();  
                    Statement stmt=getConnection().createStatement();    
                    ResultSet rs=stmt.executeQuery("select * from employees where id = "+(id));  
                    rs.next();  
                    employee = new User();  
                    employee.setId(rs.getInt("id"));  
                    employee.setFirstName(rs.getString("firstname"));  
                    employee.setLastName(rs.getString("lastname"));  
                    employee.setEmail(rs.getString("email"));  
                    employee.setPhoneNumber(rs.getString("phonenumber"));  
                    employee.setHireDate(rs.getString("hiredate"));  
                    employee.setJobId(rs.getString("jobid"));  
                    employee.setSalary(rs.getString("salary"));  
                    sessionMap.put("editUser", employee);  
                    connection.close();  
                } catch(Exception e){  
                System.out.println(e);  
                }         
                return "/edit.xhtml?faces-redirect=true";  
                }
                
                  
                public String update(User emp){  
                //int result = 0;  
                try{  
                    connection = getConnection();    
                    PreparedStatement stmt=connection.prepareStatement(  
                    "update employees set firstname=?,lastname=?,email=?,phonenumber=?,hiredate=?,jobid=?,salary=? where id=?");    
                    stmt.setString(1,emp.getFirstName());    
                    stmt.setString(2,emp.getLastName());    
                    stmt.setString(3,emp.getEmail());    
                    stmt.setString(4,emp.getPhoneNumber());    
                    stmt.setString(5,emp.getHireDate());    
                    stmt.setString(6,emp.getJobId());    
                    stmt.setString(7,emp.getSalary());    
                    stmt.setInt(8,emp.getId());    
                    stmt.executeUpdate();  
                    connection.close();  
                } catch(Exception e){  
                System.out.println();  
                }  
                return "/welcomePrimefaces.xhtml?faces-redirect=true";        
                }
                
                
                // Delete Employee Details
                public void delete(int id){  
                try{  
                    connection = getConnection();    
                    PreparedStatement stmt = connection.prepareStatement("delete from employees where id = "+id);    
                    stmt.executeUpdate();    
                } catch(Exception e){  
                System.out.println(e);  
                }  
                }
                
}  