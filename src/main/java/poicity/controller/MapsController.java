package poicity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("maps")
public class MapsController {

    @GetMapping("/prova")
    public String index(ModelMap map) {
    	map.addAttribute("affila", "affila123");
    	map.addAttribute("lat", "41.31429");
    	map.addAttribute("message", "hello11111111");

        return "index";
    }
    
    @GetMapping("/prova2")
    public String index2(ModelMap map) {
    	
    	map.addAttribute("affila", "affila123");
    	map.addAttribute("lat", "41.31429");
    	map.addAttribute("message", "hello11111111");

        return "index";
        
    }
}