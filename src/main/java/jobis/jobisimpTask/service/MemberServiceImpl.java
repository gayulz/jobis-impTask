package jobis.jobisimpTask.service;

import jobis.jobisimpTask.dto.MemberDto;
import jobis.jobisimpTask.repository.MemberRepository;

public class MemberServiceImpl implements MemberService{

    private MemberRepository memberRepository;

    @Override
    public void saveMember(MemberDto memberDto) {
        memberRepository.save(memberDto);
    }
}
