package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/*세션관리*/

@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private final Map<String, Object> sessionStore = new ConcurrentHashMap<>();//다중스레드처리

    /*세션생성
     * *세션id생성(추정불가능한랜덤값)
     * *세션 저장소에 세션id와 보관할 값 저장
     * *세선Id로 응답 쿠키를 생성해서 클라이언트에 전달*/
    public void createSession(Object value, HttpServletResponse response) {
        //세선 id생성, 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);//ctrl alt c 상수로만듬
        response.addCookie(mySessionCookie);

    }

    //세션 조회
    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
    }

    /*세션 만료 */
    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }
}
