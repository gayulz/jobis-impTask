package jobis.jobisimpTask.entity;

import jakarta.persistence.*;
import lombok.Data;

/*
* 회원가입 포멧 */
@Entity
@Data
@Table(name="MEMBER")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    private String password;
    private String name;
    private String regNo;


    public MemberEntity(String userId, String password, String name, String regNo) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
    }

    public MemberEntity() {
    }
}
