package org.alain.library.api.consumer.repository;

import org.alain.library.api.model.book.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    List<BookCopy> findAllByBookId(Long id);
}
