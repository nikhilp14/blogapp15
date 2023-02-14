package com.myblog15.blogapp15.security;

import com.myblog15.blogapp15.entities.Role;
import com.myblog15.blogapp15.entities.User;
import com.myblog15.blogapp15.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;



@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));//Here Set<Role> converted into GrantedAuthority.
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){// Why granted Authority is required because it is responsible for authenticating and authorizing users.The service must have the authority to access and verify the user's credentials, such as their username and password, and to determine if the user has the necessary permissions to access certain resources or perform certain actions. Without granted authority, the service would not be able to properly authenticate and authorize users, potentially allowing unauthorized access to sensitive information or resources.
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}

//
//public class CustomUserDetailsService implements UserDetailsService { //UserDetailsService Which is a built in class, which is present here spring security core. Now we override the method from public class CustomUserDetailsService extends UserDetailsService { //UserDetailsService Which is a built in class, which is present here spring security core.
//
//    private UserRepository userRepository;
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override //Whenever you people are implementing a security layer this is a fixed rule you should override a method called as loadUserBy. Example public UserDetails loadUserByUsername(String usernameOrEmail) or  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
//    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {//Now we override the method from this class UserDetailsService. This is here we check whether this UserName exist or not in database.                   //mike or email mike14@gmail.com       //mike or email mike14@gmail.com
//        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow( //Here If i will supply username mike or email mike14@gmail.com both side it will go like (usernameOrEmail, usernameOrEmail ) in UserRepository Optional<User> findByUsernameOrEmail(String username, String email); //mike or email mike14@gmail.com       //mike or email mike14@gmail.com
//
//                () -> new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));//UsernameNotFoundException this is built in one. It will create one exception for us. This all built in features given by the spring security.
//
//        return new  org.springframework.security.core.userdetails.User(user.getEmail(),//For verify purpose we use it //P1.A a1=new P1.A();  //Now we have a conflict here because we have given User table name as User and here also the built in class name is User, now there are two classes with the same name but different purpose I need to use it. Spring security built in class called as User.
//               //After you find a record then next step what you do. You create an object of User details then there are three parameter you need to supply to it. When three parameter you supply to it, it's automatically give you UserDetails object after verifying it.
//               user.getPassword(), mapRolesToAuthorities(user.getRoles())); // Now here take Set<Role> or (user.getRoles()) and convert it into a Authorities and gives it back. It will convert from Set<Role> into Granted Authorities Object we need a method called private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles). This method available inside User class of core spring security.
//        // Set<Role> roles = user.getRoles();
//
//
//    }
//    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()); //role.getName() Getting the first role convert that into SimpleGrantedAuthority Object,Getting the second role convert that into SimpleGrantedAuthority Object. then you collecting that to the collectors and returning back here above mapRolesToAuthorities(user.getRoles())); Line number 32.
//    }
//
//}
//}
