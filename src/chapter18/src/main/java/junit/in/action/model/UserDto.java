package junit.in.action.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class UserDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private final List<String> telephones = new ArrayList<>();
}
