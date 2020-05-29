package lt.vu.mif.jate.tasks.task03.jpa.model;

import javax.persistence.*;

/**
 * @author
 */
@Entity
@DiscriminatorValue("P")
public class Person extends Subject {

    @Column(name = "sub_first_name")
    private String firstName;
    @Column(name = "sub_last_name")
    private String lastName;
    @Column(name = "sub_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
