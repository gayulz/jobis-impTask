package jobis.jobisimpTask.service;

import jobis.jobisimpTask.dto.MemberDto;
import jobis.jobisimpTask.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void saveMember(MemberDto memberDto) {
        memberRepository.save(memberDto);
    }
}
