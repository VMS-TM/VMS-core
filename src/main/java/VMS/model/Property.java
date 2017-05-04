package VMS.model;

import javax.persistence.*;

/**
 * Created by Кирилл on 03.05.2017.
 */
@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Property() {
    }

    public Property(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
