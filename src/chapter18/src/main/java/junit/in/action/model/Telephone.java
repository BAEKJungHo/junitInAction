package junit.in.action.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "phones")
public class Telephone {

    public static enum Type {
        HOME, OFFICE, MOBILE;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String number;

    private Type type;
}
