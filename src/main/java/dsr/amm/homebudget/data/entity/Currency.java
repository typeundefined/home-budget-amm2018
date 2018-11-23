package dsr.amm.homebudget.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by knekrasov on 10/15/2018.
 */
@Entity
public class Currency {
    @Id
    @Column(length = 3)
    private String code;

    @Column(length = 40)
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
