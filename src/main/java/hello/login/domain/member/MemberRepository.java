package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();//static사용
    private static long sequence = 0L;//static사용

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findId(Long id) {
        return store.get(id);
    }

    //login id 찾기
    public Optional<Member> findByLoginId(String logonId) {
     /*   List<Member> all = findAll();
        for (Member m : all) {
            if (m.getLoginId().equals(logonId)) {
                return Optional.of(m);
            }
        }
        return Optional.empty();*/

/*lamda stream(list를 stream으로 변경 루프 돌림)사용으로 코드변경*/
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(logonId))
                .findFirst();
    }


    public List<Member> findAll() {
        return new ArrayList<>(store.values());//store에 value member를 가져옴
    }
/*테스트용 초기화코드*/
    public void clearStore() {
        store.clear();
    }
}
