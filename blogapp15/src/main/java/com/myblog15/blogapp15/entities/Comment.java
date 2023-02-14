package com.myblog15.blogapp15.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="comments")
public class Comment {// Many comments and one post @ManyToOne. Here Post is a parent table and Comment is a child table. How the child table link with post table it is many to one.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;//id BIGINT
    private String body;//body VARCHAR(255)
    private String email;//email VARCHAR(255)
    private String name;//name VARCHAR(255)

    @ManyToOne(fetch = FetchType.LAZY)// I will go with the LAZY Loading because the performance is good.//Now I need to apply foreign key concept
    @JoinColumn(name="post_id", nullable = false)//How to define a foreign key? In order to define a foreign key. Here we are writing join column @JoinColumn(). This is my foreign key post_id where I am joining with the primary key. It's automatically created one foreign key. Here we join two tables.// nullable = false, Foreign key has to be there.Which cannot be null. If there is a video then you write a comment. because post is not there and you are writing a comment.
    private Post post;//post_id BIGINT//Foreign key here mapping concepts comes into play. Comment is many, post is one. Here the comment feature is mapped with @ManyToOne Annotation.

}
//Lazy Loading and Eager loading// Like Leads,Contacts and Billing is running same times. If user want to click on lead page info then lead entity will work. When it's requirement then it's work.
//@ManyToOne(fetch = FetchType.LAZY) When there is a requirement of that entity. Let us load that entity when there is requirement for that table in my project.
//@ManyToOne(fetch = FetchType.EAGER) But when I do eager loading that means let us load all the entity.Through RAM your program can access that. Everything is running in the RAM. Which entity to load in RAM  either load all the entity make it eager. If 100 tables all 100 tables are loaded it's reduce the performance  because yop unnecessary loaded 100 tables which is not required. I will do lazy load I am only loading that table. Which is require as per my programe execution.
