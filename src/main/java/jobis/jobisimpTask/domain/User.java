package jobis.jobisimpTask.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class User {
    private String userId;
    private String password;
    private String name;
    private String regNo;
}
