package study.querydsl.dto;

import lombok.Data;

@Data
public class MemberSearchCondition {
    
    // 회원명, 팀명, 나이(ageGoe, ageLoe)
    private String username;
    private String teamName;
    // Integer를 사용하는 이유는 null이 넘어 올 수 있기 떄문
    private Integer ageGoe;
    private Integer ageLoe;
}
