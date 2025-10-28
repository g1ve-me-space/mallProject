@Controller
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Die Anwendung funktioniert!";
    }
}
