package com.minakov.blog.model;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(min = 1,
            message = "Tittle length must be > 0")
    private String tittle;
    @NotNull
    @Size(min = 1,
            message = "post length must be > 0")
    private String fullText;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @ManyToMany
    private List<User> viewers;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    private String date;
    private boolean edited ;

    @ManyToMany
    private List<User> likes;



}