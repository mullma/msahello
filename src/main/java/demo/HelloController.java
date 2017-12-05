package demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {
    @RequestMapping(method = RequestMethod.GET, path = "hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(method = RequestMethod.GET, path = "helloMap")
    public Map helloMap() {
        Map map = new HashMap();
        map.put("test", "123456");
        if (1==1){
            System.out.printf("1231");
        }
        return map;
    }

}
