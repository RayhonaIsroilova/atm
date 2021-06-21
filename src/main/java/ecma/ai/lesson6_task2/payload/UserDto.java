package ecma.ai.lesson6_task2.payload;

import ecma.ai.lesson6_task2.entity.Role;
import lombok.Data;

@Data
public class UserDto {
    private String fullName;
    private Role role;
}
