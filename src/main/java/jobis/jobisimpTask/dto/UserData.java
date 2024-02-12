package jobis.jobisimpTask.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "USERDATA")
public class UserData{
    @Id
    @Column(name="REGNO")
    private String regNo;

    @Column(name="name")
    private String name;

    public UserData() {
    }

    public UserData(String regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }
}
