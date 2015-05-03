package Main.java.it.objectway.stage.dcapozzi.employer.model;

/**
 * Created by pong on 30/04/15.
 */
public class Employee implements Cloneable{
    private String name, lastname, address, telephone;
    private Integer id;

    public Employee(String name, String lastname, String address, String telephone, int id) {
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.telephone = telephone;
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public Integer getId() {
        return id;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }

    public String getFullname() {
        return name + " " + lastname;
    }

    public Employee clone(){
        return new Employee(this.getName(),
                this.getLastname(),
                this.getAddress(),
                this.getTelephone(),
                this.getId());
    }
}
