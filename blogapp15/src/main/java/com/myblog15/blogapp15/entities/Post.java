package com.myblog15.blogapp15.entities;


import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="posts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="title", nullable = false)
    private String title;
    @Column(name="description", nullable = false)
    private String description;
    @Column(name="content", nullable = false)
    private String content;
    //cascade  which means any changes made in parent table will have the impact on child. // When I delete the post I want comments to be deleted automatically.
    //cascade =CascadeType.ALL, The meaning here is you modify something in the parent, you remove the parent row the Cascade type should remove all the data from child table or entity. which means any changes made in parent table will have the impact on child.
    @OneToMany(mappedBy = "post", cascade =CascadeType.ALL, orphanRemoval = true)// (mappedBy = "post") why? Because In Comment class private Post post; here reference variable name is post telling Set<Comment> comments this comments is mapped to the post variable present in another class.  //OneToMany and ManyToOne it's becomes bidirectional mapping. This is called as Bi-Directional mapping
            Set<Comment> comments = new HashSet<>(); //How thus Post Table link with Comments Table @OneToMany. If I am just create a variable that's not enough. There should be HashSet.
}// Set has having unique value  out here that's why I am using Set here.
//Set<Comment> comments = new HashSet<>(If you delete something in the parent table, the child table data also get deleted.); If you delete a video then comments will gone or it's deleted automatically for this I have to write some code inside hashtable()
//Here comments variable mapped to post variable present in another class.
//orphanRemoval = true    meaning I now delete the post after delete the post, comments will become orphan. Because who is the father of comments post now let says father died now what will be do with comments. That's automatically gets removed.
