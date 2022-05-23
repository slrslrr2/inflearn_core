package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
    private final LogDemoService logDemoService;
//    private final MyLogger myLogger;
//    private final ObjectProvider<MyLogger> myLoggerProvider;
    /**
     * private final ObjectProvider<MyLogger> myLoggerProvider;
     생성되는 시점을 싱글톤으로 올려서 MyLogger객체가 생성되는 것이 아니라 (이렇게하면 Scope=request라서 request요청이 없기에 오류남)
     ==> ObjectProvider를 사용해서 생성시점을 getObject()할 때로 변경함으로써 아래 오류를 막아준다.
        Error creating bean with name 'myLogger': Scope 'request' is not active for the current thread;
     */


    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request){
        String requetURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requetURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");

        return "OK";
    }
}
