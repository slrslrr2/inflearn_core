package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean, DisposableBean {
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출 url " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect(){
        System.out.println("connect :" + url);
    }

    public void call(String message){
        System.out.println("call: " + url + " message = " + message);
    }

    // 서비스 종료시 호출
    public void disconnect(){
        System.out.println("close : " + url);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // InitializingBean interface에 정의된 함수
        // 초기화 진행하라고, 스프링빈의 의존관계 주입(@Autowired)이 끝나면 호출된다
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }

    @Override
    public void destroy() throws Exception {
        // DisposableBean interface에 정의된 함수
        // 해당 Bean소멸 되기 직전에 실행된다.
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
}
