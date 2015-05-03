package Main.java.it.objectway.stage.dcapozzi.employer.impl;

import Main.java.it.objectway.stage.dcapozzi.employer.EmployeeManager;
import Main.java.it.objectway.stage.dcapozzi.employer.exception.MultipleValuesException;
import Main.java.it.objectway.stage.dcapozzi.employer.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pong on 01/05/15.
 */
public class EmployeeManagerDB implements EmployeeManager {
    private Connection connection;

    public EmployeeManagerDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/test", "pong", "id46kvx3");

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

    @Override
    public Employee add(String name, String lastname, String address, String telephone) {
        final String sqlInsert =
                "INSERT INTO employee(name, lastname, address, telephone) " +
                        "VALUES(?, ?, ?, ?)";
        final String selectSQL =
                "SELECT * " +
                        "FROM employee " +
                        "WHERE name = ? AND latname = ? AND address = ? AND telephone = ?";
        Integer id = null;
        PreparedStatement preparedStmtInsert = null;
        PreparedStatement preparedStmtSelect = null;
        try {
            preparedStmtInsert = connection.prepareStatement(sqlInsert);
            preparedStmtSelect = connection.prepareStatement(selectSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (preparedStmtInsert != null) {
            try {
                preparedStmtInsert.setString(1, name);
                preparedStmtInsert.setString(2, lastname);
                preparedStmtInsert.setString(3, address);
                preparedStmtInsert.setString(4, telephone);

                /* execute insert */
                preparedStmtInsert.execute();

                /* retrieve id */
                preparedStmtSelect.setString(1, name);
                preparedStmtSelect.setString(2, lastname);
                preparedStmtSelect.setString(3, address);
                preparedStmtSelect.setString(4, telephone);

                ResultSet rs = preparedStmtSelect.executeQuery();

                id = rs.getInt("id");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new Employee(name, lastname, address, telephone, id);
    }

    @Override
    public Employee add(String name, String lastname, String address) {
        return this.add(name, lastname, address, null);
    }

    @Override
    public Employee add(String name, String lastname) {
        return this.add(name, lastname, null, null);
    }

    @Override
    public Employee remove(String fullname) throws MultipleValuesException {
        String deleteSQL =
                "DELETE FROM employees " +
                        "WHERE CONCAT(name, ' ', lastname) = ?";
        List<Employee> result = lookup(fullname);
        if(result.size() > 1){
            throw new MultipleValuesException("This key has multiple associated values, " +
                    "Please use remove(String fullname, int id) method", result);
        }
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(deleteSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (preparedStmt != null) {
            try {
                preparedStmt.setString(1, fullname);

                /* execute select */
                preparedStmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result.get(0);
    }

    @Override
    public Employee remove(String fullname, int id) {
        String deleteSQL =
                "DELETE FROM employees " +
                        "WHERE id = ?";
        List<Employee> result = lookup(fullname);
        Employee ret = null;
        for(Employee e : result){
            if(e.getId() == id){
                ret = e;
                break;
            }
        }

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(deleteSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (preparedStmt != null) {
            try {
                preparedStmt.setInt(1, id);

                /* execute select */
                preparedStmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    @Override
    public Employee edit(String fullname, String address, String telephone) throws MultipleValuesException {
        String updateSQLaddr =
                "UPDATE employee " +
                        "SET address = ? " +
                        "WHERE CONCAT(name, ' ', lastname) = ?";
        String updateSQLtel =
                "UPDATE employee " +
                        "SET telephone = ? " +
                        "WHERE CONCAT(name, ' ', lastname) = ?";
        String updateSQLall =
                "UPDATE employee " +
                        "SET address = ?, " +
                        "telephone = ? " +
                        "WHERE CONCAT(name, ' ', lastname) = ?";
        List<Employee> result = lookup(fullname);
        if(result.size() > 1){
            throw new MultipleValuesException("This key has multiple associated values, " +
                    "Please use edit(String fullname, int id, String address, String telephone) method", result);
        }
        PreparedStatement preparedStmt = null;
        try {
            if(address != null && telephone != null) {
                preparedStmt = connection.prepareStatement(updateSQLall);
                preparedStmt.setString(1, address);
                preparedStmt.setString(2, telephone);
                preparedStmt.setString(3, fullname);
            }
            if(address != null){
                preparedStmt = connection.prepareStatement(updateSQLaddr);
                preparedStmt.setString(1, address);
                preparedStmt.setString(2, fullname);
            }
            if(telephone != null){
                preparedStmt = connection.prepareStatement(updateSQLtel);
                preparedStmt.setString(1, telephone);
                preparedStmt.setString(2, fullname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (preparedStmt != null) {
            try {
                /* execute select */
                preparedStmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result.get(0);
    }

    @Override
    public Employee edit(String fullname, int id, String address, String telephone) {
        String updateSQLaddr =
                "UPDATE employee " +
                        "SET address = ? " +
                        "WHERE id = ?";
        String updateSQLtel =
                "UPDATE employee " +
                        "SET telephone = ? " +
                        "WHERE id = ?";
        String updateSQLall =
                "UPDATE employee " +
                        "SET address = ?, " +
                        "telephone = ? " +
                        "WHERE id = ?";
        List<Employee> result = lookup(fullname);
        Employee oldEmp = null;
        for(Employee e : result){
            if(e.getId() == id){
                oldEmp = e;
                break;
            }
        }
        PreparedStatement preparedStmt = null;
        try {
            if(address != null && telephone != null) {
                preparedStmt = connection.prepareStatement(updateSQLall);
                preparedStmt.setString(1, address);
                preparedStmt.setString(2, telephone);
                preparedStmt.setInt(3, id);
            }
            if(address != null){
                preparedStmt = connection.prepareStatement(updateSQLaddr);
                preparedStmt.setString(1, address);
                preparedStmt.setInt(3, id);
            }
            if(telephone != null){
                preparedStmt = connection.prepareStatement(updateSQLtel);
                preparedStmt.setString(1, telephone);
                preparedStmt.setInt(3, id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (preparedStmt != null) {
            try {
                /* execute select */
                preparedStmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return oldEmp;
    }

    @Override
    public List<Employee> lookup(String fullname) {
        List<Employee> result = new ArrayList<Employee>();
        String selectSQL =
                "SELECT * " +
                        "FROM employees " +
                        "WHERE CONCAT(name, ' ', lastname) = ?";

        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(selectSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (preparedStmt != null) {
            try {
                preparedStmt.setString(1, fullname);

                /* execute select */
                ResultSet rs = preparedStmt.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("name");
                    String lastname = rs.getString("lastname");
                    String address = rs.getString("address");
                    String telephone = rs.getString("telephone");
                    Integer id = rs.getInt("id");
                    result.add(new Employee(name, lastname, address, telephone, id));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected void finalize() throws Throwable {
        connection.close();
        super.finalize();
    }
}
