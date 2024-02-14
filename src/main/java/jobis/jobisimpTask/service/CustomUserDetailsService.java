package jobis.jobisimpTask.service;


import jobis.jobisimpTask.details.CustomUserDetails;
import jobis.jobisimpTask.dto.CustomUserInfoDto;
import jobis.jobisimpTask.entity.MemberEntity;
import jobis.jobisimpTask.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findByUserId(userId);
        if(member == null ) new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        CustomUserInfoDto dto = mapper.map(member, CustomUserInfoDto.class);
        return new CustomUserDetails(dto);
    }
}
