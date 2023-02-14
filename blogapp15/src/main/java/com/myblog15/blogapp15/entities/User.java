package com.myblog15.blogapp15.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name="users", uniqueConstraints = {@UniqueConstraint(columnNames ={"username"}),@UniqueConstraint(columnNames={"email"})} )
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   private String name;
   private String username;
   private String email;
   private String password;
   //LazyLoading means I am loading this table the other table content when required let us loaded. But here we don't have any option when required. here required both the tables to loaded at same time. Whenever the user accessing it role is required. It is mandatory here you load both table here. When user table loaded, role also ready to load for accessibility. So here we make it EAGER.
   @ManyToMany(fetch = FetchType.EAGER, cascade =CascadeType.ALL)// Why Eager because here whenever the Username is fetch I want it Role also to be fetched
   @JoinTable(name="user_roles", joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))// This is foreign key "user_id" from table 1 and this is primary key "id" from table 2.
   private Set<Role> roles; // Whenever you are applying many. So that many has to that collection. If it is one variable then OneToMany. Set<Role> roles = new HashSet<>(); Like this is one variable Set<Comment> comments = new HashSet<>(); so OneToMany
//For this user I need to give Set<Role> for that in AuthController class we have to search roles name by using roleRepository.findByName or Role roles = roleRepository.findByName("ROLE_ADMIN").get(); and again convert that into roles object into Set<roles> by using method called Collection.singleton() or user.setRoles(Collections.singleton(roles)); and setRoles into user object like user.setRoles
   //cascade =CascadeType.ALL if any changes in user tables it will impact on roles table or I want changes to happen in another table.

}// For this user what is the role you need to give  private Set<Role> roles;
