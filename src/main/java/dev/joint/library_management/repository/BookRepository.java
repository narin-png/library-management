package dev.joint.library_management.repository;

import dev.joint.library_management.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer>, JpaSpecificationExecutor<Book> {
    List<Book> findByAvailableCopiesGreaterThan(Integer copies);

    // Books published within a year range (e.g. "books from the 90s").
    List<Book> findByPublishedYearBetween(Integer startYear, Integer endYear);

    // --- JPQL ---

    // Books belonging to a given category (by name), case-insensitive,
    // joining through the book_categories join table.
    @Query("select b from Book b join b.categories c where lower(c.name) = lower(:categoryName)")
    List<Book> findByCategoryName(@Param("categoryName") String categoryName);

    // Books whose author matches (partial, case-insensitive) AND that were
    // published in/after a given year - a slightly more complex, multi-condition JPQL example.
    @Query("select b from Book b where lower(b.author.name) like lower(concat('%', :authorName, '%')) " +
            "and b.publishedYear >= :fromYear")
    List<Book> findByAuthorNameContainingAndPublishedYearFrom(@Param("authorName") String authorName,
                                                              @Param("fromYear") Integer fromYear);


    @Query(value = "select c.name as categoryName, count(bc.book_id) as bookCount " +
            "from categories c " +
            "left join book_categories bc on bc.category_id = c.id " +
            "group by c.name " +
            "order by bookCount desc", nativeQuery = true)
    List<Object[]> countBooksPerCategoryNative();
}
