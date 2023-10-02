package poicity.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("error")
public class NotFoundController implements ErrorController{

    @GetMapping
    public String index() {
        return "error404";
    }
    
	
}
