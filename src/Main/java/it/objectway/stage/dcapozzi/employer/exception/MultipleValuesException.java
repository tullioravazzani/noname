package Main.java.it.objectway.stage.dcapozzi.employer.exception;

import Main.java.it.objectway.stage.dcapozzi.employer.model.Employee;

import java.util.Collection;

/**
 * Created by pong on 03/05/15.
 */
public class MultipleValuesException extends Exception {
    private Collection<Employee> values;
    public MultipleValuesException(String s, Collection<Employee> values) {
        super(s);
        this.values = values;
    }

}
