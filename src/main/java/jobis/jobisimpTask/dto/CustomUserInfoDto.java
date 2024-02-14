package jobis.jobisimpTask.dto;
import jobis.jobisimpTask.entity.MemberEntity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomUserInfoDto extends MemberEntity {
    private String regNo;
    private String name;
    private String role;
    private String userId;
    private String password;
}
