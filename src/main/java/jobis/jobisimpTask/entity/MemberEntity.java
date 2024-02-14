package jobis.jobisimpTask.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/*
* 회원가입 포멧 */
@Entity
@Data
@Table(name="MEMBER")
public class MemberEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    private String password;
    private String name;
    private String regNo;
    private String role;


    public MemberEntity() {
    }
}
