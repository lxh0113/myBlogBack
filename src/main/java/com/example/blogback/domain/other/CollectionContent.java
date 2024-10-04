package com.example.blogback.domain.other;

import com.example.blogback.domain.Collect;
import com.example.blogback.domain.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionContent {

    private Collection collection;

    private List<Collect> collects;

}
