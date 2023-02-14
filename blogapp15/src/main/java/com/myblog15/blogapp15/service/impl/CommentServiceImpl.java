package com.myblog15.blogapp15.service.impl;

import com.myblog15.blogapp15.entities.Comment;
import com.myblog15.blogapp15.entities.Post;
import com.myblog15.blogapp15.exception.ResourceNotFoundException;
import com.myblog15.blogapp15.payload.CommentDto;
import com.myblog15.blogapp15.repository.CommentRepository;
import com.myblog15.blogapp15.repository.PostRepository;
import com.myblog15.blogapp15.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

      private CommentRepository commentRepository; // But whenever you are saving a Comment, the Comment has to be saved against the Post
    private PostRepository postRepository; // Here we will use constructor based Injection.//private CommentRepository commentRepository; This is a component of springboot so here we cannot write @Bean.

    private ModelMapper mapper; // This is not an Component of Springboot. Spring boot not know. There is no logic in spring IOC. Spring IOC Does not KNOW which Bean to create and put into it. So I need to tell spring boot, which Bean to create, that configuration we do here
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper=mapper;
    }




    @Override//Based on postId we have save the comments. Tell for this postId you save the comments. Once you have postId based on id we get post object through postRepository findById() method we get the post object based on that object we save the comments.
    public CommentDto createComment(long postId, CommentDto commentDto) {//long postId the reason being here for that Post you need to save the Comments.
        Post post = postRepository.findById(postId).orElseThrow(

                () -> new ResourceNotFoundException("post", "id", postId) //If record found put the record in post object
        );

        Comment comment = mapToComment(commentDto);
        comment.setPost(post);// In above Based on postId I got the post object. Now I am telling comment.setPost(post); for this post you save the particular comments. After that I initialize // private Post post; this variable created on Comment class. Otherwise, comment will not save for that post.
        Comment commentEntity = commentRepository.save(comment);
        return mapToDto(commentEntity);

    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(y->mapToDto(y)).collect(Collectors.toList());

    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
        postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("post","id",id)
        );
        Comment comment =commentRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("comment","id",id)
        );

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
       Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long id) {
        postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("post","id",id)
        );

        commentRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("comment","id",id)
        );
        commentRepository.deleteById(id);
    }

    public Comment mapToComment(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);

//        Comment comment = new Comment();
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }

    public CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment, CommentDto.class);

//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }
}
