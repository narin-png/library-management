Library Management System layihəsi kitabxana idarəetməsi üçün hazırlanmış backend tətbiqidir. Layihənin əsas məqsədi müəlliflər, kitablar və üzvlər haqqında məlumatların idarə olunmasını təmin etməkdir.

Layihə Spring Boot istifadə edilərək hazırlanıb və REST API arxitekturasına əsaslanır. Kod strukturu Controller, Service, Repository, Entity və DTO qatlarına bölünüb. Bu yanaşma layihənin daha oxunaqlı, genişlənə bilən və maintainable olmasına kömək edir.

Layihədə üç əsas modul yaradılıb:
- Author Management: müəlliflərin əlavə edilməsi, siyahılanması, yenilənməsi və silinməsi.
- Book Management: kitabların idarə olunması və müəlliflə əlaqələndirilməsi.
- Member Management: kitabxana üzvlərinin idarə olunması.

Database əlaqəsi üçün Spring Data JPA istifadə olunub. Entity-lər arasında əlaqələr qurulub:
- Author və Book arasında One-to-Many əlaqəsi.
- Book daxilində uyğun Author məlumatlarının idarə olunması.

Məlumatların entity və DTO arasında çevrilməsi üçün EnhancedObjectMapper yaradılıb. Bu yanaşma entity-lərin birbaşa API-dən qaytarılmasının qarşısını alır və daha düzgün data transfer təmin edir.

API-lər üçün Swagger/OpenAPI sənədləşdirilməsi əlavə olunub. Bunun vasitəsilə bütün endpoint-ləri yoxlamaq və test etmək mümkündür.

Layihədə əlavə olaraq:
- Validation mexanizmi əlavə edildi (@NotBlank, @Email, @NotNull və s.).
- Global Exception Handler yaradıldı və xətalar daha düzgün formada idarə olunur.
- Pagination əlavə edildi ki, böyük həcmdə məlumatların səhifələnmiş formada alınması mümkün olsun.
- Service qatı üçün unit test yazıldı. Mockito istifadə edilərək repository və mapper mock edildi və servis metodunun düzgün işləməsi yoxlanıldı.

İstifadə olunan texnologiyalar:
- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Lombok
- Swagger/OpenAPI
- Mockito
- JUnit

Nəticə olaraq bu layihə kitabxana proseslərinin backend səviyyəsində idarə olunmasını təmin edən, REST prinsiplərinə uyğun hazırlanmış tam funksional Spring Boot tətbiqidir.
