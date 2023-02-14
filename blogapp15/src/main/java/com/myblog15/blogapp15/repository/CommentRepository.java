package com.myblog15.blogapp15.repository;

import com.myblog15.blogapp15.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


//This is the DAO LAYER Data Access Object.
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(long postId);// Based on id I have a built-in method, but here I don't have any method to find the record based on postId or based on Email and Name. So In order to find a record based on the choice of your column in the table. We have to write extra line into repository layer.
}//Here one post many comments that's why we have taken List<Comment>
//If Mobile number so it is unique, Comment findByMobile(long mobile); Based on mobile I find the record.
//This is custom method.

