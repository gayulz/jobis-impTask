package jobis.jobisimpTask.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "USERDATA")
public class UserDataEntity {
    @Id
    @Column(name="REGNO")
    private String regNo;

    @Column(name="name")
    private String name;

}
