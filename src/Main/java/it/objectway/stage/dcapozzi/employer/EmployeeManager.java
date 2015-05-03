package Main.java.it.objectway.stage.dcapozzi.employer;

import Main.java.it.objectway.stage.dcapozzi.employer.exception.MultipleValuesException;
import Main.java.it.objectway.stage.dcapozzi.employer.exception.NotFoundException;
import Main.java.it.objectway.stage.dcapozzi.employer.model.Employee;

import java.util.List;

/**
 * Created by pong on 30/04/15.
 */
public interface EmployeeManager {
    public Employee add(String name, String lastname, String address, String telephone);
    public Employee add(String name, String lastname, String address);
    public Employee add(String name, String lastname);
    public Employee remove(String fullname) throws MultipleValuesException, NotFoundException;
    public Employee remove(String fullname, int id);
    public Employee edit(String fullname, String address, String telephone) throws MultipleValuesException;
    public Employee edit(String fullname, int id, String address, String telephone);
    public List<Employee> lookup(String fullname);
}
