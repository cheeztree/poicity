package poicity.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import poicity.dto.AuthResponse;
import poicity.dto.LoginDTO;
import poicity.dto.UserDTO;
import poicity.entity.Language;
import poicity.entity.Role;
//import org.springframework.security.core.userdetails.User;
import poicity.entity.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import poicity.repository.LanguageRepository;
import poicity.repository.RoleRepository;
import poicity.service.AuthService;
import poicity.service.CustomUserDetailsService;
import poicity.service.JwtService;
import poicity.service.UserService;
import poicity.utils.FilesUtils;
import poicity.utils.PasswordGenerator;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{


	private final ModelMapper mapper;
	private final UserService userService;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final CustomUserDetailsService customUserDetailsService;
	private final LanguageRepository langRepo;
	private final RoleRepository roleRepo; 

	@Override
	public AuthResponse login(LoginDTO request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		UserDetails user = customUserDetailsService.loadUserByEmail(request.getEmail());
		String token = jwtService.getToken(user);
		return AuthResponse.builder()
				.token(token)
				.build();
	}

	@Override
	public AuthResponse register(UserDTO request) {
		User user = mapper.map(request, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Language lang = langRepo.findById(request.getLang_id()).get();
		user.setLang(lang);
		
//		user.setRoles(new HashSet<Role>(roleRepo.findAll()));
		Set<Role> asd = new HashSet<Role>();
		asd.add(roleRepo.findByName("USER"));
		user.setRoles(asd);
		
//		userService.saveUser(user);
		User userNew = userService.save(user);

		if(userNew.getAvatar() != null) {
			if (userNew.getAvatar().equals("") || userNew.getAvatar().equals("string")) {
				user.setAvatar(FilesUtils.immagazzinaAvatarDefault2(userNew.getId()));
			}
		} else {
			user.setAvatar(FilesUtils.immagazzinaAvatarDefault2(userNew.getId()));
		}
		
		userService.save(user);
		
		return AuthResponse.builder().token(jwtService.getToken(user)).build();
	}

	@Override
	public AuthResponse registerFromGoogle(User user) {

		String password = PasswordGenerator.generate();
		user.setPassword(passwordEncoder.encode(password));

		userService.saveUser(user);

		return AuthResponse.builder().token(jwtService.getToken(user)).build();
		
	}

}
