package lt.vu.mif.jate.tasks.task03.jpa.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author
 */
@Entity
@DiscriminatorValue("C")
public class Company extends Subject {

    @Column(name = "sub_company_title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
