package poicity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import poicity.dto.LoginDTO;
import poicity.entity.Role;
import poicity.entity.User;
import poicity.repository.RoleRepository;
import poicity.repository.UserRepository;
import poicity.service.AuthService;
import poicity.service.UserService;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	private final AuthService authService;
	private final RoleRepository roleRepo;

	@GetMapping
	public Object index(
//    		@CookieValue(value = "token", required = false) String token
	) {

		return "admin_login";
//    	if(token == null) {
//            return "admin_login";
//    	} else {
//    		
//    		System.out.println(authentication == null);
//    		
//    		HttpHeaders responseHeaders = new HttpHeaders();
//    	    responseHeaders.set("authorization", token);
//    	    System.out.println("TOKEN " + token);
//    	    return new ResponseEntity<String>("admin_panel", responseHeaders, HttpStatus.OK);
//    		
//    		
////            return "admin_panel";
//    	}

	}

	@PostMapping(value = "/login")
	public String login(@ModelAttribute(name = "loginForm") LoginDTO loginDTO, HttpServletResponse res) {

		if (userService.existsByEmail(loginDTO.getEmail())) {
			User user = userService.findByEmail(loginDTO.getEmail());
			Role roleAdmin = roleRepo.findByName("ADMIN");

			if (user.getRoles().contains(roleAdmin)) {
				return "redirect:/admin?notAdmin";
			}

			if (passwordEncoder.matches(String.valueOf(loginDTO.getPassword()), user.getPassword())) {

				String token = authService.login(loginDTO).getToken();

				Cookie cookie = new Cookie("token", token);
				cookie.setMaxAge(Integer.MAX_VALUE);
				res.addCookie(cookie);

//				return "admin_panel";
			} else {
//				System.out.println("PASSWORD NON MATCHA");

				return "redirect:/admin?wrongPassword";
			}
		} else {
//			System.out.println("NON esiste mail");

			return "redirect:/admin?wrongMail";

		}

//		HttpHeaders responseHeaders = new HttpHeaders();
//	    responseHeaders.set("authorization", tokenCookie);
//	    
//	    return new ResponseEntity<String>("admin_panel", responseHeaders, HttpStatus.OK);
//        return "admin_panel";

		// TUTTO OKAY
		return "redirect:/admin/panel";

	}

	@GetMapping("/panel")
	public String panel(@CookieValue(value = "token", required = false) String token, Model model) {
		if (token == null) {
			return "redirect:/admin?invalidAuthorization";
		} else {
//			System.out.println(token);

			if (!checkToken(token)) {
				return "redirect:/admin?invalidAuthorization";
			}
			
			
		}
		
		List<User> listaUsers = userRepo.findAll();
//		System.out.println(listaUsers);
		model.addAttribute("listaUsers", listaUsers);
		
		return "admin_panel";
	}

	private boolean checkToken(String token) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		Map<String, Object> map = new HashMap<>();

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
//		System.out.println(entity);

		ResponseEntity<String> respEntity = restTemplate.exchange("https://localhost:8081/auth/checkToken",
				HttpMethod.POST, entity, String.class);

//		System.out.println(respEntity);
//		System.out.println(respEntity.getBody());

		if (respEntity.getBody().equals("true")) {
			return true;
		}

		return false;

	}

//	@GetMapping("admin_panel/getAllUsers")
//	public String getAllUsers(Model model) {
////		List<User> listaUsers = userRepo.findAll();
//
//		User user = new User();
//		user.setName("AFFILA NOME");
//		user.setUsername("AFFILA");
//		user.setEmail("asd@asd.com");
//		List<User> listaUsers = new ArrayList<>();
//		listaUsers.add(user);
//		System.out.println(listaUsers);
//		model.addAttribute("listaUsers", listaUsers);
//
//		return "listaUsers";
//	}
}
