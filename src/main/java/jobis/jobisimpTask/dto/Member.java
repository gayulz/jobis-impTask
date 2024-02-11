package jobis.jobisimpTask.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/*
* 회원가입 포멧 */
@Entity
@Data
@Table(name="MEMBER")
public class Member {
    @Id
    private String userId;
    private String password;
    private String name;
    private String regNo;


    public Member(String userId, String password, String name, String regNo) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
    }

    public Member() {
    }
}
