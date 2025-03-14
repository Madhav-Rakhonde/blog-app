package com.madhav.blog.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="category")
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    @Column(name="Title",length =100,nullable = false)
    private String categoryTitle;
    @Column(name="Description")
    private String categoryDescription;
    @OneToMany(mappedBy = "category",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> posts= new ArrayList<>();
}
