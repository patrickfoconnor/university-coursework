package edu.montana.csci.csci440.helpers;

import edu.montana.csci.csci440.model.Employee;
import edu.montana.csci.csci440.util.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EmployeeHelper {

    public static String makeEmployeeTree() {

        // Possibly a HashTable
        Employee employee = Employee.find(1); // root employee
        // and use this data structure to maintain reference information needed to build the tree structure
        // Hashtable that has list of employees that report to value(EmployeeId)
        Hashtable<Employee, List<Employee>> employeeTable = new Hashtable<>();
        List<Employee> resultList = new LinkedList<>();
        try (Connection conn = DB.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM employees")) {
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                assert employee != null;
                resultList.add(employee.getEmployee(results));
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }
        for (Employee emp: resultList) {
            List<Employee> employeeList = new LinkedList<>();
            for (Employee employee1 : resultList){
                if (emp.getEmployeeId().equals(employee1.getReportsTo())){
                    employeeList.add(employee1);
                }
            }
            employeeTable.put(emp, employeeList);
        }

        assert employee != null;
        return "<ul>" + makeTree(employee, employeeTable) + "</ul>";
    }


    public static String makeTree(Employee employee, Hashtable<Employee, List<Employee>> employeeMap) {

        StringBuilder list = new StringBuilder("<li><a href='/employees" + employee.getEmployeeId() + "'>"
                + employee.getEmail() + "</a><ul>");
        // Gets the reports to that employee/boss
        //List<Employee> reports = employee.getReports();
        List<Employee> reports = employeeMap.get(employee);
//        Collection<Employee> bosses = employeeMap.values();
//        for (Employee boss :)
        for (Employee report : reports) {
            list.append(makeTree(report, employeeMap));
        }
        return list + "</ul></li>";
    }
}
