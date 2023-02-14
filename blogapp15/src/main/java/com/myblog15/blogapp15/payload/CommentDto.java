package com.myblog15.blogapp15.payload;

import lombok.Data;

@Data
public class CommentDto { // CommentDto is required to connect with entity Comment class and Postman via controller layer and service layer.
    private long id;
    private String body;
    private String email;
    private String name;

}
// When I post a comment, comment id, body, email and name will go to this CommentDto object like from JSON to DTO. DTO to Entity. Entity to DTO, DTO to JSON.

