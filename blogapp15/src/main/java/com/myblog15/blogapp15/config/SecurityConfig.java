package com.myblog15.blogapp15.config;
import com.myblog15.blogapp15.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired  //We created a bean of CustomUserDetailsService
    private CustomUserDetailsService userDetailsService;//@AutoWired When I am creating the object of this which means I am getting loadUserByUsername(). What this method does you supply the username to it. It will check whether the username exist and further it will also check the authentication whether userName and password in the database which is present is a valid one or not
    //CustomUserDetailsService //When you create its bean
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {//This will create an object and this object address inject into  private AuthenticationManager authenticationManager;  in AuthController
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()//sign up page is expose to all we write here permit it to everyone. If you sign in or registration both the page is open for all.
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override //This is the fixed method

    protected void configure(AuthenticationManagerBuilder auth) throws Exception { //Just create the object of it @AutoWired CustomUserDetailsService supply to this built in code. With the passwordEncoder() method you call that.
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder()); // You call this passwordEncoder method which has been created above. This method will return the bean.

    }//auth.userDetailsService(userDetailsService) You just supply the userDetailsService and this has now a userName,password and role or GrantedAuthority which is present in loadByUserName() method

}

   // @Autowired  //We created a bean of CustomUserDetailsService
   // private CustomUserDetailsService userDetailsService;










//I am not going with In Memory Authentication.
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//    @Bean    //In memory part
//    PasswordEncoder passwordEncoder(){ //By doing this spring boot takes the encrypted password. What if my password not encrypted there is an problem because in springboot password should be encrypted. If you don't encrypted the password your are loosing the security.
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception { //For basic authentication we override one of the method from WebSecurityConfigurerAdapter.
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//    }
// //.antMatchers(HttpMethod.GET, "/api/**").permitAll() //In memory part
//
//
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() { //In memory part
//        UserDetails nikkhil = User.builder().username("nikkhil").password(passwordEncoder()
//                .encode("password")).roles("USER").build();
//        UserDetails admin = User.builder().username("admin").password(passwordEncoder()
//                .encode("admin")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(nikkhil, admin);
//    }
//}


//@Configuration
//@EnableWebSecurity //We will get to know all the method of WebSequrityConfigurerAdapter by using press ctrl 'o'.
//@EnableGlobalMethodSecurity(prePostEnabled = true)// I want admin to access certain method like createPost only admin can do. user cannot do. Delete the post only admin can do user cannot do. Update post only admin can do. User only Get. But admin can create the post delete the post , update the post and read the post. User can only read the post. After this we can go to post controller and put one Annotation  @PreAuthorize("hasRole('ADMIN')")//Only role is an Admin This method can run that means create post method can be run.
//public class SecurityConfig extends WebSecurityConfigurerAdapter { //You make this class a subclass of WebSecurityConfigurerAdapter. Like how we created custom exception just by making sub class of Runtime. When you extends this class has some built in method. Which we will inherit, override and modify the method and do my own configuration.
//// Here we encrpt the password. so one can see.
//    @Bean// Why we use @Bean because this method returning an object and maintain by Spring Boot.
//    PasswordEncoder passwordEncoder(){// Bean annotation will help us to create an object in spring boot where in that object is managed by spring boot
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override //You will get this method (protected void configure(HttpSecurity http) throws Exception) from WebSecurityConfigurerAdapter just by pressing ctrl + O. In this method we configure which url should excess by whome.
//    protected void configure(HttpSecurity http) throws Exception { //One of the method is here very important which is configure(http:HttpSecurity):void
//      http
//              .csrf().disable()
//              .authorizeRequests() //Here I am telling all the URL anybody can access.
//              .antMatchers(HttpMethod.GET,"/api/**").permitAll()//Here we  can control which URL who can access.//When the HttpMethod is get. I am telling that antMatchers whenever you see the GetMapping method whatever you see that after /api/ it can be  anything permitAll which means any user can access that means user can access nd admin can access.
//              .anyRequest()
//              .authenticated()
//              .and()
//              .httpBasic();//Here we are not going with form based authentication. Now I am started with basic authentication concept here.
//      //(hcd4ah)Here we have to remember the formula //Here we configure which URL can be access by who.//This method present in this class WebSecurityConfigurerAdapter here we inheriting it and override and modify  the method logic.
//    }//Here I will do the setting so that everybody can excess. In order to do this I write some code here
//
//    @Override
//    protected UserDetailsService userDetailsService() { // Now I am not using application.properties file for store user name and password.  protected UserDetailsService userDetailsService() In this method I can store my User name and Paassword.
//        UserDetails nikkhil = User.builder().username("nikkhil").password(passwordEncoder().encode("password")).roles("USER").build();// Import it from import org.springframework.security.core.userdetails.User;
//        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();//This nikkhil reference object has username and password credential.
//
//      return new InMemoryUserDetailsManager(nikkhil, admin);//Now we need to encode the password. We don't want AutoGenerated password.
//    }//Now we have two object with two different credential.

