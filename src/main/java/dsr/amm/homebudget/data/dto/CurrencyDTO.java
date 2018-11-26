package dsr.amm.homebudget.data.dto;

import javax.validation.constraints.NotNull;

public class CurrencyDTO {
    private String code;

    @NotNull
    private String humanReadableName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHumanReadableName() {
        return humanReadableName;
    }

    public void setHumanReadableName(String humanReadableName) {
        this.humanReadableName = humanReadableName;
    }
}
