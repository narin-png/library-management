package dev.joint.library_management.specification;

import dev.joint.library_management.entity.Book;
import dev.joint.library_management.entity.Category;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class BookSpecification {
    private BookSpecification() {
    }

    public static Specification<Book> hasTitleLike(String title) {
        if (!StringUtils.hasText(title)) {
            return null;
        }
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Book> hasAuthorNameLike(String authorName) {
        if (!StringUtils.hasText(authorName)) {
            return null;
        }
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("author").get("name")), "%" + authorName.toLowerCase() + "%");
    }

    public static Specification<Book> hasCategoryName(String categoryName) {
        if (!StringUtils.hasText(categoryName)) {
            return null;
        }
        return (root, query, cb) -> {
            // distinct avoids duplicate Book rows if it ever matched more
            // than one joined category row for the same book.
            query.distinct(true);
            Join<Book, Category> categories = root.join("categories");
            return cb.equal(cb.lower(categories.get("name")), categoryName.toLowerCase());
        };
    }

    public static Specification<Book> publishedFromYear(Integer fromYear) {
        if (fromYear == null) {
            return null;
        }
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("publishedYear"), fromYear);
    }

    public static Specification<Book> publishedToYear(Integer toYear) {
        if (toYear == null) {
            return null;
        }
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("publishedYear"), toYear);
    }

    public static Specification<Book> isAvailable(Boolean available) {
        if (available == null) {
            return null;
        }
        return available
                ? (root, query, cb) -> cb.greaterThan(root.get("availableCopies"), 0)
                : (root, query, cb) -> cb.lessThanOrEqualTo(root.get("availableCopies"), 0);
    }
}
