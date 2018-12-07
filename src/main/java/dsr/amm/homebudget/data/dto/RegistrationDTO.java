package dsr.amm.homebudget.data.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class RegistrationDTO {

    @NotNull
    @Length(min = 1, max = 40)
    private String username;
    @NotNull
    @Length(max = 256)
    private String password;
    @NotNull
    @Length(max = 256)
    private String fullName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
