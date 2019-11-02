package org.alain.library.api.consumer.repository;

import org.alain.library.api.model.book.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    List<BookCopy> findAllByBookId(Long id);

    @Query("select b from BookCopy b where b.book.id = :bookId and b.id = :copyId")
    Optional<BookCopy> findOneByIdInBook(@Param("bookId")Long bookId, @Param("copyId")Long copyId);

    @Query("delete from BookCopy b where book.id = :bookId and b.id = :copyId")
    void deleteOneByIdInBook(Long bookId, Long copyId);
}
