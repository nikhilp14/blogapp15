package com.myblog15.blogapp15.repository;



import com.myblog15.blogapp15.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

}

//public interface JpaRepository<T, ID> extends PagingAndSortingRepository<T, ID>
//public interface PagingAndSortingRepository<T, ID> extends CrudRepository<T, ID>
// PagingAndSortingRepository it has two methods:-
//Iterable<T> findAll(Sort sort);
//Page<T> findAll(Pageable pageable);
