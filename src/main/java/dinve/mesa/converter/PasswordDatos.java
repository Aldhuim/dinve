package dinve.mesa.converter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordDatos {
    private String actual_password;
    private String new_password;
    private String confirm_password;
}
