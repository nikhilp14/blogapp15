package com.myblog15.blogapp15.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor  ///1st step done.
public class PostResponse { ///Here content as a List. Because it will not be one content.It will be several content

    private List<PostDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElement;
    private int totalPages;
    private boolean last; /// Until and unless you are not reaching last page it will show false. If it's a last page it will show true.


}
