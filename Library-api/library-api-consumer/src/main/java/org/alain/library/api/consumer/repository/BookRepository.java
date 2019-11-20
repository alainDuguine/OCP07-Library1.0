package org.alain.library.api.consumer.repository;

import org.alain.library.api.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b, a from Book b join b.authors a where b.title like %:title% and concat(a.firstName, ' ', a.lastName) like %:author%")
    List<Book> findByTitleAndAuthor(@Param("title")String title,@Param("author") String author);

    List<Book> findByTitle(String title);

//    @Query(value = "select b, sum(case when (c.available = TRUE) then 1 else null end) from Book b left join b.copyList c where b.title like %:title% group by b")
    @Query(value = "select b, count(c.available), c.available from Book b left join b.copyList c where b.title like %:title% group by b, c.available")
//    @Query("select b, count(c) from Book b left join fetch b.copyList c where b.title like :title and c.available = true group by b")
    List<Object> findBooksByTitleWithCopies(@Param("title")String title);


}
