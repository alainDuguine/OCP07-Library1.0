package org.alain.library.api.consumer.repository;

import org.alain.library.api.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleLike(String title);
    List<Book> findByAuthorsLike(String author);

    @Query("select b from Book b join BookCopy bc where b.title like %:title% and bc.available = true")
    List<Book> findBookWithCopies(@Param("title")String title);

//    @Query("SELECT b, a FROM Book b INNER JOIN b.authors = Auth ON a.id = b.authors.id WHERE b.title LIKE %:title% AND a.firstname LIKE %:author% OR a.lastname LIKE %:author%")
//    List<Book> findByTitleLikeAndAuthorsLike(@Param("title") String title, @Param("author")String author);

}
