package singleton;

public class SingletonService {

    // static을 사용하면 객체(인스턴스)에 소속된 멤버가 아니라 클래스에 고정된 멤버입니다.
    // 그렇기에 클래스로더가 클래스를 로딩해서
    // 메소드 메모리 영역에 적재할때 클래스별로 관리됩니다.
    // 따라서 클래스의 로딩이 끝나는 즉시 바로 사용할 수 있습니다.
    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

//    // private 자신의 클래스 내부에서만 접근 가능하다
    private SingletonService() {

    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
