package ru.gurkin.spring.library.model;

import java.util.Set;

import javax.persistence.*;

import com.google.common.collect.Sets;

@Entity
@Table(name = "books")
@NamedEntityGraph(name = "graph.BookGraph", attributeNodes = {@NamedAttributeNode(value = "authors"), @NamedAttributeNode(value = "genres")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @ManyToMany(targetEntity = Author.class, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = Sets.newHashSet();

    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "book_genre", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = Sets.newHashSet();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Comment> comments = Sets.newHashSet();

    public Book() {
    }

    public Book(Long id, String title, Set<Author> authors, Set<Genre> genres, Set<Comment> comments) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.genres = genres;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}