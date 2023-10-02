package poicity.controller;

import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.MalformedJwtException;

import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import poicity.dto.ErrorDTO;
import poicity.dto.GoogleLoginDTO;
import poicity.dto.LoginDTO;
import poicity.dto.PasswordDTO;
import poicity.dto.ResetPasswordDTO;
import poicity.dto.UserDTO;
import poicity.entity.User;
import poicity.repository.LanguageRepository;
import poicity.repository.UserRepository;
import poicity.service.AuthService;
import poicity.service.ImageService;
import poicity.service.JwtService;
import poicity.service.LanguageService;
import poicity.service.UserService;
import poicity.utils.JavaMail;
import poicity.utils.PasswordGenerator;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	@Autowired
	private UserRepository userRepo;
	private final UserService userService;
	private final AuthService authService;
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private LanguageRepository langRepo;
	@Autowired
	private LanguageService langService;
	@Autowired
	ImageService imageService;

	Logger log = LogManager.getLogger(AuthController.class);

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginDTO request) {
		if (userService.existsByEmail(request.getEmail())) {
			User user = userService.findByEmail(request.getEmail());
			if (passwordEncoder.matches(String.valueOf(request.getPassword()), user.getPassword())) {
				return ResponseEntity.ok(authService.login(request));
			} else {
				return new ResponseEntity<Object>(new ErrorDTO(new Date(), "Invalid password."), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Object>(
					new ErrorDTO(new Date(), "Email '" + request.getEmail() + "' doesn't exists."),
					HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody UserDTO request) {

		if (userRepo.existsByEmail(request.getEmail())) {
			return new ResponseEntity<Object>(
					new ErrorDTO("User with email '" + request.getEmail() + "' already exists."), HttpStatus.CONFLICT);
		}

		if (!langService.existsById(request.getLang_id())) {
			return new ResponseEntity<Object>(
					new ErrorDTO("Language with id '" + request.getLang_id() + "' doesn't exists."),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepo.existsByUsername(request.getUsername())) {
//			return new ResponseEntity<Object>(new ErrorDTO("Username '" + request.getUsername() + "' already exists."),
			return new ResponseEntity<Object>(new ErrorDTO("Username already exists."), HttpStatus.NOT_ACCEPTABLE);
		}

		try {
			return ResponseEntity.ok(authService.register(request));
		} catch (ConstraintViolationException e) {
			log.error(e.getMessage());
//			return new ResponseEntity<Object>(new ErrorDTO("'" + request.getEmail() + "' is not a valid mail."),
			return new ResponseEntity<Object>(new ErrorDTO("Email is not valid."),
					HttpStatus.NOT_ACCEPTABLE);
		}

	}

	@PostMapping("/resetPassword")
	public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordDTO resetPassDTO) {

		String email = resetPassDTO.getEmail();
		if (userRepo.existsByEmail(email)) {

			User user = userRepo.findByEmail(email);
			String newPassword = PasswordGenerator.generate();
			user.setPassword(passwordEncoder.encode(newPassword));

			userRepo.save(user);

			JavaMail.mandaEmailXresetPass2(user, newPassword);

		} else {
			return new ResponseEntity<Object>(new ErrorDTO("User with this email doesn't exists."),
					HttpStatus.NOT_FOUND);
		}

		return null;
	}

	@PostMapping("/checkPassword")
	public boolean checkPasswork(@RequestBody PasswordDTO password, Authentication authentication) {
		User user = userService.findByEmail(authentication.getName());
		if (passwordEncoder.matches(password.getPassword(), user.getPassword())) {
			return true;
		} else {
			return false;
		}

	}

	@PostMapping("/changePassword")
	public void changePassword(@RequestBody PasswordDTO password, Authentication authentication) {
		User user = userService.findByEmail(authentication.getName());
		user.setPassword(passwordEncoder.encode(password.getPassword()));

		userRepo.save(user);
	}

	@PostMapping("google")
	public ResponseEntity<Object> google(@RequestBody GoogleLoginDTO login) {
		User user = jwtService.decodeGoogleToken(login.getGoogleToken());
		user.setLang(langRepo.findById(login.getLang_id()).get());

		if (!user.getEmail().equals("")) {

			if (userRepo.existsByEmail(user.getEmail())) {
				return new ResponseEntity<Object>(
						new ErrorDTO(new Date(), "User with email '" + user.getEmail() + "' already exists."),
						HttpStatus.CONFLICT);
			} else {
				return ResponseEntity.ok(authService.registerFromGoogle(user));
			}

		} else {
			return new ResponseEntity<Object>(new ErrorDTO("Invalid token"), HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("checkToken")
	public boolean checkToken(@RequestHeader Map<String, String> headers, Authentication authentication) {

		String tokenHeader = headers.get("authorization");
//		System.out.println(headers);
//		System.out.println(tokenHeader.length());

		if (tokenHeader == null) {
			return false;
		}
//		if(tokenHeader.length() != 166) {
//			//MI ROMPO QUI
//			return false;
//		}
		try {
			String email = authentication.getName();

			return true;
		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
	}

//	@GetMapping(value = "/poi/{id_poi_img}"
////			, produces = "image/*"
//			)
//	@Async
//	public ResponseEntity<?> getAvatar(
//			@PathVariable("id_poi_img") String id_poi_img
//			, HttpServletResponse response
//			) {
//
//		try {
//			InputStream resource = imageService.getPoiImgById(Long.parseLong(id_poi_img));
//			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//			StreamUtils.copy(resource, response.getOutputStream());
//			resource.close();
//			
//			return new ResponseEntity<>(HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.err.println("Poi image with id '" + id_poi_img + "' not found.");
//			return new ResponseEntity<>(new ErrorDTO("Poi image with id '" + id_poi_img + "' not found."), HttpStatus.NOT_FOUND);
//
//		}
//	
//	}

}
