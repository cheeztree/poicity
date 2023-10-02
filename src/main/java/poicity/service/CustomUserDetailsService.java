package poicity.service;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import poicity.entity.User;
import poicity.repository.UserRepository;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if(user != null){
            return new org.springframework.security.core.userdetails.User(user.getUsername()
                    , user.getPassword(),
                    user.getRoles().stream()
                            .map((role) -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList()));
//            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), null);

        }else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }
    
    public UserDetails loadUserByEmail(String email) {

        User user = userRepository.findByEmail(email);
        
        try{

            return new org.springframework.security.core.userdetails.User(user.getEmail()
                    , user.getPassword(),
                    user.getRoles().stream()
                            .map((role) -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toList()));

        
        } catch (Exception e) {
//        	e.printStackTrace();
//        	System.err.println("Invalid email");
        	return null;
        }

    }
    
//    public UserDetails loadUserByEmail(String email) {
//
//        User user = userRepository.findByEmail(email);
//        if(user != null){
//            return new org.springframework.security.core.userdetails.User(user.getEmail()
//                    , user.getPassword(),
//                    user.getRoles().stream()
//                            .map((role) -> new SimpleGrantedAuthority(role.getName()))
//                            .collect(Collectors.toList()));
//
//        }else {
//            throw new UsernameNotFoundException("Invalid email or password");
//        }
//    }
}
