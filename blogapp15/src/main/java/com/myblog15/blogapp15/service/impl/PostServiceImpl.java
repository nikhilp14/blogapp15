package com.myblog15.blogapp15.service.impl;


;
import com.myblog15.blogapp15.entities.Post;
import com.myblog15.blogapp15.exception.ResourceNotFoundException;
import com.myblog15.blogapp15.payload.PostDto;
import com.myblog15.blogapp15.payload.PostResponse;
import com.myblog15.blogapp15.repository.PostRepository;
import com.myblog15.blogapp15.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
        import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

        import java.util.List;

        import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepo, ModelMapper mapper) {
        this.postRepo = postRepo;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {


        Post post = mapToEntity(postDto);
        Post  postEntity = postRepo.save(post);
        PostDto dto = mapToDto(postEntity);

        return dto;
    }

    ///
//Here 1st create an pageable object. To create a pageable object we don't need any new keyword. This PageRequest class has a "of();" method.you just go to the "of();" method and supply value to it. That will automatically give you pageable object. This object variable will take pageNo and pageSize.
    @Override // Before findAll return type List<Post> means List of Post Object but, here in (Pageable) Page<Post> here Post is acting like a collection, which is lots of Post Object in it. Inside Post lots of Post object present in it. Before List of post object means Inside List lots of Post object present in it.
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) { ///Here we return postResponse so that's why public PostResponse getAllPost  ///public List<PostDto> getAllPost(int pageNo, int pageSize) //Now I am fetch the record from the db based on the pageSize and display only it.
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();///sortDir it has asc and dsc,  whatever value present in it now  asc or dsce so just ingroe it, after ignoring the case let us compare. I am compare with Sort.Direction.ASC.name(). Here whatever the value is present in sortDir is it ascending if it is ascending then I will put a ? left side : right side if it is true then execute left side of colon part if it is false then execute right hand side of colon part. but what to execute Sort.by(sortBy).ascending() : Sort.by(sortBy).descending. Instead of if else we rae using this type of statements
        ///This is behaved like if else condition.  Sort sort Whatever the results it is present in it Either asc or desc.
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort); ///Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy));///Because Sort sort data type is Sort here we need to convert Sort to String here. //This will Automatically create one special Object, which is Pageable pageable in this object we will supply = PageRequest.of(pageNo,pageSize); and If you supply pageNo. = 1 and pageSize=10. It will return or display table record based on supply value.
        Page<Post> posts = postRepo.findAll(pageable);            // Now I am giving this pageable reference object.This pageable reference hase two things one is pageNo and 2nd one pageSize.// And the return type of this if you observe Page<Post>.
        List<Post> content = posts.getContent();                  //Page<Post> I want to convert it into List. //posts.getContent(); What it does? This will covert that into List. public abstract java.util.List<T> getContent()    Returns the page content as List. Because ultimately I want List Of Object. I don't want Page class with collect concept. I want List collection with an object.
        //List<Post> posts = postRepo.findAll();// But now further we cannot use postRepo.findAll(); and directly work with it.
        List<PostDto> contents = content.stream().map(x -> mapToDto(x)).collect(Collectors.toList());///return  content.stream().map(x -> mapToDto(x)).collect(Collectors.toList());///Instead of taking all data from content and returning it to the PostDto or returning the List<PostDto>.

        PostResponse postResponse= new PostResponse();//Here We create PostResponse object, then we need to initialized  all the variable of PostResponse.
        postResponse.setContent(contents);/// Here PostResponse will send the all content back to postman. ///Here I am taking contents and initializing the List. Which is set into PostResponse. Then All my JSON Object is now set into PostResponse
        postResponse.setPageNo(posts.getNumber());///Page<Post> posts, this posts reference variable will give me the pageNo. getNumber(); will give you current pageNo
        postResponse.setPageSize(posts.getSize()); ///Page<Post> posts this class object which is posts have all the built in method like total elements, pageSize, pageNo.
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElement(posts.getTotalElements());///Data base might have 50Lakhs record then the total elements would be 50lakhs and the data type is long totalElement, because its a huge data
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(//If record is not found orElseThrow will and it's  automatically create ResourceNotFoundException or Exception object through lamda expression

                () -> new ResourceNotFoundException("post", "id", id) //If record found put the record in post object

        );

        PostDto postDto = mapToDto(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(

                () -> new ResourceNotFoundException("post", "id", id)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post newPost = postRepo.save(post);
        return mapToDto(newPost);


    }

    @Override
    public void deletePost(long id) {
        Post post = postRepo.findById(id).orElseThrow(

                () -> new ResourceNotFoundException("post", "id", id)

        );
        postRepo.deleteById(id);

    }


    public Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);//We need to convert here postDto to Post Object. This will automatically copy the data from dto to Post    //map(Object source, Class<D> destinationType)

//        Post post= new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
    public PostDto mapToDto(Post post){
        PostDto dto = mapper.map(post, PostDto.class); //Taking the content of one object moving that into another object.

//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());

        return dto;
    }

}
