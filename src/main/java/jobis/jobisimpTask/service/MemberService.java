package jobis.jobisimpTask.service;

import jobis.jobisimpTask.dto.MemberDto;

public interface MemberService {
    public boolean saveMember(MemberDto memberDto);
    public String login(MemberDto memberDto);
}
