package com.minakov.blog.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @Size(min = 3,
            message = "Comment length must be > 3")
    private String text;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne
    private Post post;

}
