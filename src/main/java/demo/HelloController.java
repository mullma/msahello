package demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {
  @RequestMapping(method = RequestMethod.GET, path = "hello")
  public String hello() throws Exception{
    return "hello";
  }

  @RequestMapping(method = RequestMethod.GET, path = "helloMap")
  public Map helloMap() {
    Map<String, String> map = new HashMap<String, String>();
        map.put("test", "123456");
    return map;
  }
}
