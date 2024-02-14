package jobis.jobisimpTask.service;

import jobis.jobisimpTask.entity.MemberEntity;

public interface MemberService {
    public boolean saveMember(MemberEntity memberEntity);
    public String login(MemberEntity memberEntity);
}
