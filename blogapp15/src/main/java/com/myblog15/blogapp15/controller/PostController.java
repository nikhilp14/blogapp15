package com.myblog15.blogapp15.controller;




        import com.myblog15.blogapp15.payload.PostDto;
        import com.myblog15.blogapp15.payload.PostResponse;
        import com.myblog15.blogapp15.service.PostService;
        import com.myblog15.blogapp15.utils.AppConstants;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.security.access.prepost.PreAuthorize;
        import org.springframework.validation.BindingResult;
        import org.springframework.web.bind.annotation.*;


        import javax.validation.Valid;
        import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }//BindingResult bindingResult its treated as ModelMap
    @PreAuthorize("hasRole('ADMIN')")//Only role is an Admin This method can run that means create post method can be run. To whom I authorize this method only admin. So admin can access this method. In Security config file if you write @EnableGlobalMethodSecurity(prePostEnabled = true) then it can enable and provide the preAuthorize this method to admin only. After that this annotation will be work @PreAuthorize
    @PostMapping//public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult)
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){ //If you  are saving the record in database give me the status code 201.
        // PostDto postDto = postService.createPost(postDto);
        //return new ResponseEntity<>(postDto, HttpStatus.CREATED);
if(bindingResult.hasErrors()){
    return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

    }
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
}



    @GetMapping //http:/localhost:8080/api/posts/?pageNo=0&pageSize=5&sortBy=title&sortDir=dsc the value will come to the backend. @requestParam will read the value from URL. If I don't give any value in the URL Like pageNo. By default it will be zero. Give that as a String "0".
    public PostResponse getAllPost( ///Before it was public List<PostDto> getAllPost, after pagination it is public PostResponse getAllPost
                                    ///Before it was defaultValue = "0". Now we can make it public final static variable through create class called AppConstant. Declare as public final static and Initialize thr value.
                                    @RequestParam(value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,      // Here If I give required = true then ULR should have this value.
                                    @RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,  //Here I want page size to be 10. Ever page there will be 10 records. If 20 records are there I should get 2 pages 0 and 1st page in both the page 10 and 10 Records. 25 record for 3 pages, 0 page will be 10, 1st pag will 10 and 3rd page will be 5.
                                    @RequestParam(value="sortBy",defaultValue=AppConstants.DEFAULT_SORT_BY,required=false) String sortBy,
                                    @RequestParam(value="sortDir",defaultValue =AppConstants.DEFAULT_SORT_DIR,required = false ) String sortDir
    ){
        return postService.getAllPost(pageNo,pageSize,sortBy,sortDir);     //Once we get the pageNo & pageSize, then we can implement on postService by supplying its value.   When I call getAllPost() I will supply value. Now When I am getting the record from the db, I will have to give the pageNo & pageSize                                            //        List<PostDto> postDto = postService.getAllPost();
        /// From postserviceimpl layer It will return or send the object back. What all things response object has List of contents, pageNo, pageSize,totalPages, total elements, isLastPage.     In postman postResponse Object getting converted to   JSON Object.                                                                  //        return postDto;
    }                                                                     //required = false thats make not mandatory field by the way. Like page no. can be there in the URL or cannot be there in the URL.

    @GetMapping("/{id}") //http:/localhost:8080/api/posts/1
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){

        return ResponseEntity.ok( postService.getPostById(id));// PostDto dto = postService.getPostById(id);
        //return ResponseEntity.ok(dto); After that Response Status will go there.
        //Whenever you see HttpStatus.OK-> It will return the status 200 back to PostMan.
    }//If you just want to return 200, then you can just write ResponseEntity.ok
    //In case of update we should provide Unique Parameter like Email, mobile number and id..
    @PutMapping("/{id}")//http:/localhost:8080/api/posts/1
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("id") long id){
        PostDto dto = postService.updatePost(postDto, id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //http:/localhost:8080/api/posts/1
    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post entity deleted successfully",HttpStatus.OK);

    }

}
