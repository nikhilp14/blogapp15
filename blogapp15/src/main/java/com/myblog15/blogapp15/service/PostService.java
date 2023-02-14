package com.myblog15.blogapp15.service;



import com.myblog15.blogapp15.payload.PostDto;
import com.myblog15.blogapp15.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto, long id);
    void deletePost(long id);


}