package com.myblog15.blogapp15.controller;
import com.myblog15.blogapp15.entities.Role;
import com.myblog15.blogapp15.entities.User;
import com.myblog15.blogapp15.payload.JWTAuthResponse;
import com.myblog15.blogapp15.payload.LoginDto;
import com.myblog15.blogapp15.payload.SignUpDto;
import com.myblog15.blogapp15.repository.RoleRepository;
import com.myblog15.blogapp15.repository.UserRepository;
import com.myblog15.blogapp15.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){//When your Username and Password is valid then it will further run. It will call line 53 and it will generate token and that token I am sending it back as Response.
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));// 1st signIn in AuthController then it will call the method tokenProvider.generateToken(authentication); Generate token from JwtTokenProvider return it to AuthController and response back to JWTAuthResponse which is Payload
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}


//import com.myblog15.blogapp15.entities.Role;
//import com.myblog15.blogapp15.entities.User;
//import com.myblog15.blogapp15.payload.LoginDto;
//import com.myblog15.blogapp15.payload.SignUpDto;
//import com.myblog15.blogapp15.repository.RoleRepository;
//import com.myblog15.blogapp15.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//import java.util.Collections;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {//This will take the JSON content put that into LoginDto
//
//    @Autowired// Object for AuthenticationManager not automatically created like modelmapper, here should go to the config file  create or override a AuthenticationManager and apply @Bean annotation into it. Then its object address will be created and inject into authenticationManager variable
//    private AuthenticationManager authenticationManager;// Security config file it's object is being created by applying @Bean Annotation.For verification purpose we use AuthenticationManager interface for verification. This is the interface which will automatically verify whether the username or password is correct or not in the data base.But in order create the object of AuthenticationManager, go to the security-config and you need to override the method protected AuthenticationManager authenticationManager() and on that applying @Bean. only then this is present object will get created here of this @AutoWired AuthenticationManager authenticationManager; this object authenticationManager has a method called as authenticationManager. authenticate(); This authenticate method will check username and password. Inorder to check user and password Inside this method you need to create object new UsernamePasswordAuthenticationToken() what it does its takes the username and password if it is valid it will generate a token if it is not valid it will not generate a token.
//    //authenticationManager This will verify the username and password.
//    @Autowired
//    private UserRepository userRepository; //This is for @PostMapping("/signup")
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @PostMapping("/signin")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
//        Authentication authentication = authenticationManager.authenticate(  // Authentication authenticate whether the password is correct or not you have Authentication authentication details in it. If its correct then it will create token and put that into authentication. Valid details are present in authentication. then it will go to the getContext and here it will set the valid details. if it's not correct  it will just go to the context and tell that username and password provide is invalid, please don't give this user any further details.
//                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
//                        loginDto.getPassword())
//
//        );//To authenticate here what parameter we supply username and password.//How to verify it by just writing this and this will take two parameter one is the username and password.
//        SecurityContextHolder.getContext().setAuthentication(authentication); //Whatever user name and password we people have basically even if its correct it will move further and it will set the authentication token here, if it's false then its not set
//
//        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);// If its correct now it will return one message. I want to display this message in postman or frontend part or sent response back to the postman.
//    }//If its wrong then go to the postman it says bad credential. Here authenticationManager work like if and else condition. If true continue further if false stop there only line 29. Go to the postman tell bad credential.
//@PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
//
//        // add check for username exist in a DB
//    if(userRepository.existsByUsername(signUpDto.getUsername())){//It will go to userRepository and search this sign details username already exist or not if it is exist then it response BAD Request and return string message "Username is already taken!" and BADRequest return status code is 400.
//        return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
//    }
//    // add check for email exist in a DB
//    if(userRepository.existsByEmail(signUpDto.getUsername())){//It will go to userRepository and search this sign details email already exist or not if it is exist then it response BAD Request and return string message "Email is already taken!" and BADRequest return status code is 400.
//        return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
//    }
//
//    //create user object// Why user objet because signUpDto content I will copy that to User entity
//    User user = new User();// Create an Entity Object and copy. Here you need to define role of this new user. How do we define the role. So entity class user have one more method called user.setRoles it can take a set so here we convert roles object to set by using method called Collections.singleton(roles) putting inside roles object.
//    user.setName(signUpDto.getName());
//    user.setUsername(signUpDto.getUsername());
//    user.setEmail(signUpDto.getEmail());
//    user.setPassword(passwordEncoder.encode(signUpDto.getPassword())); //But when you set the password in data base I cannot give password as it is I have to encode the password and then set. How we encode the password here we have to create it's object  @Autowiredprivate PasswordEncoder passwordEncoder; Earlier we have created EncodePassword class after creating password then I set into the data base.
//// here I am hardcoding particular user his role as admin by default due to hard coding below like "ROLE_ADMIN" or Role roles = roleRepository.findByName("ROLE_ADMIN").get();
//    Role roles = roleRepository.findByName("ROLE_ADMIN").get();// Here I am telling you go to the role repository and find the record for ADMIN and covert that into Optional to Entity Object Role// It will give the Object id=2 and role name = ROLE_ADMIN and that Object you need to set here in user "user.setRoles(Collection.singleton(roles));"
//    user.setRoles(Collections.singleton(roles)); // Here I need to convert roles object to Set<Role> roles. Because user.setRoles should take a set because of collection. In order to convert roles Object to Set<Role> we use the method Collections.singleton(roles) it will convert roles object into set.
//    //These above two lines Will Set Admin role for user by default. By default it will become admin.
//    userRepository.save(user);// This Collections class has a singleton method when you give  object address to it, it will store the object address as set.
//
//return new ResponseEntity<>("User registered successfully",HttpStatus.OK);
//    }//Whenever you create user you have to give roles to that user
//}
