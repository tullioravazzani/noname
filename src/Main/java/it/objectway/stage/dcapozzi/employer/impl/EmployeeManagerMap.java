package Main.java.it.objectway.stage.dcapozzi.employer.impl;

import Main.java.it.objectway.stage.dcapozzi.employer.EmployeeManager;
import Main.java.it.objectway.stage.dcapozzi.employer.exception.MultipleValuesException;
import Main.java.it.objectway.stage.dcapozzi.employer.exception.NotFoundException;
import Main.java.it.objectway.stage.dcapozzi.employer.model.Employee;

import java.util.*;

/**
 * Created by pong on 30/04/15.
 */
public class EmployeeManagerMap implements EmployeeManager {
    private Map<String, Map<Integer, Employee>> employees;
    private static Integer nextId = 0;

    public EmployeeManagerMap() {
        employees = new HashMap<String, Map<Integer, Employee>>();
    }

    @Override
    public Employee add(String name, String lastname, String address, String telephone) {
        Employee addEmp = new Employee(name, lastname, address, telephone, nextId++);
        Map<Integer, Employee> internalMap = employees.get(addEmp.getFullname());
        if(internalMap == null){
            internalMap = new HashMap<Integer, Employee>();
            employees.put(addEmp.getFullname(), internalMap);
        }
        internalMap.put(addEmp.getId(), addEmp);
        return addEmp;
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
    public Employee remove(String fullname) throws MultipleValuesException, NotFoundException {
        Map<Integer, Employee> internalMap = employees.get(fullname);
        if(internalMap.size() > 1) {
            throw new MultipleValuesException("This key has multiple associated values, " +
                    "Please use remove(String fullname, int id) method", internalMap.values());
        }
        Collection<Employee> emp = internalMap.values();
        employees.remove(fullname);
        for(Employee e : emp){
            return e;
        }
        throw new NotFoundException("Employee: " + fullname + " Not found");
    }

    @Override
    public Employee remove(String fullname, int id) {
        Map<Integer, Employee> internalMap = employees.get(fullname);
        return internalMap.remove(id);
    }

    @Override
    public Employee edit(String fullname, String address, String telephone) throws MultipleValuesException {
        Map<Integer, Employee> internalMap = employees.get(fullname);
        if(internalMap.size() > 1) {
            throw new MultipleValuesException("This key has multiple associated values, " +
                    "Please use remove(String fullname, int id) method", internalMap.values());
        }
        Collection<Employee> emp = internalMap.values();
        employees.remove(fullname);
        Employee editEmp = null;
        for (Employee e : emp) {
            editEmp = e;
            break;
        }
        Employee oldEmp = null;
        if (editEmp != null) {
            oldEmp = editEmp.clone();
        }
        if(address != null) { editEmp.setAddress(address); }
        if(telephone != null) { editEmp.setTelephone(telephone); }
        return oldEmp;
    }

    @Override
    public Employee edit(String fullname, int id, String address, String telephone) {
        Map<Integer, Employee> internalMap = employees.get(fullname);
        Employee editEmp = internalMap.get(id);
        if(address != null) { editEmp.setAddress(address); }
        if(telephone != null) { editEmp.setTelephone(telephone); }
        return editEmp;
    }

    @Override
    public List<Employee> lookup(String fullname) {
        List<Employee> result = new ArrayList<Employee>();
        result.addAll(employees.get(fullname).values());
        return result;
    }

    @Override
    public String toString() {
        return "EmployeeManagerMap{" +
                "employees=" + employees +
                '}';
    }

}
