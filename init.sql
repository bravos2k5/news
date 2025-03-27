create table category
(
    id   int identity
        primary key,
    name nvarchar(50)
)
go

create table letter
(
    id       nvarchar(255) not null
        primary key,
    isEnable bit           not null
)
go

create table [user]
(
    id       uniqueidentifier default newid() not null
        primary key,
    birthDay date,
    email    nvarchar(255),
    fullName nvarchar(55),
    mobile   nvarchar(13),
    password nvarchar(255)                    not null,
    role     nvarchar(8),
    sex      nvarchar(6),
    username nvarchar(255)                    not null
        unique
)
go

create table news
(
    id         uniqueidentifier default newid() not null
        primary key,
    content    nvarchar(max),
    image      nvarchar(255),
    isHome     bit              default 0       not null,
    postedDate date             default getdate(),
    title      nvarchar(255)                    not null,
    viewCount  int              default 0,
    authorId   uniqueidentifier
        references [user],
    categoryId int
        references category
)
go

CREATE PROCEDURE GetHomePageItems
AS
BEGIN
    SELECT n.id, n.title, n.image, n.postedDate, u.fullName AS authorName
    FROM news n
             LEFT JOIN [user] u ON n.authorId = u.id
    WHERE n.isHome = 1;
END;
go

CREATE PROCEDURE GetHomePageNews
AS
BEGIN
    SELECT *
    FROM news
    ORDER BY isHome DESC, postedDate DESC, viewCount DESC;
END;
go

CREATE PROCEDURE GetNewsByCategory
@categoryId INT
AS
BEGIN
    SELECT *
    FROM news
    WHERE news.categoryId = @categoryId
    ORDER BY isHome DESC, postedDate DESC, viewCount DESC;
END;
go

CREATE PROCEDURE GetNewsItemsByCategory
@categoryId INT
AS
BEGIN
    SELECT n.id, n.title, n.image, n.postedDate, u.fullName AS authorName
    FROM news n
             LEFT JOIN [user] u ON n.authorId = u.id
    WHERE n.categoryId = @categoryId
    ORDER BY n.isHome DESC, n.postedDate, n.viewCount;
END;
go

CREATE PROCEDURE GetNewsItemsByReporter
@reporterId UNIQUEIDENTIFIER
AS
BEGIN
    SELECT n.id, n.title, n.image, n.postedDate, u.fullName AS authorName
    FROM news n
             LEFT JOIN [user] u ON n.authorId = u.id
    WHERE n.authorId = @reporterId
    ORDER BY n.isHome DESC, n.postedDate, n.viewCount;
END;
go

CREATE PROCEDURE GetNewsThread
@newsId UNIQUEIDENTIFIER
AS
BEGIN
    SELECT n.content, u.fullName, n.image, n.postedDate, n.title, n.viewCount, n.authorId, n.categoryId
    FROM news n
             INNER JOIN [user] u ON n.authorId = u.id
    WHERE n.id = @newsId;
END;
go

CREATE   PROC getImportantNews
as
begin
    SELECT TOP 10 id, title
    FROM news
    ORDER BY viewCount DESC, postedDate DESC
end
go

CREATE   PROC getLatestNews
as
begin
    SELECT TOP 10 id, title
    FROM news
    ORDER BY postedDate DESC
end
go

CREATE   PROC spDashboard
AS
BEGIN
    SELECT
        (SELECT COUNT(*) FROM category) AS categoryCount,
        (SELECT COUNT(*) FROM news) AS newsCount,
        (SELECT COUNT(*) FROM news WHERE isHome = 1) AS homeNewsCount;
END
go

-- 5. Xóa bản tin theo khóa chính
CREATE PROCEDURE spDeleteNews
@Id uniqueidentifier
AS
BEGIN
    DELETE FROM news WHERE id = @Id
END
go

CREATE   PROC spFindNewsByAuthorAndKey
    @authorId UNIQUEIDENTIFIER,
    @keyword NVARCHAR(255)
as
begin
    SELECT n.id, n.title, n.postedDate FROM news n
    WHERE n.authorId = @authorId and n.title like N'%' + @keyword + '%'
end
go

CREATE   PROC spFindNewsByKey
@keyword NVARCHAR(255)
as
begin
    SELECT n.id, n.title, n.postedDate FROM news n
    WHERE n.title like N'%' + @keyword + '%'
end
go

-- 1. Truy vấn tất cả bản tin có thuộc tính home bằng true
CREATE PROCEDURE spGetHomeNews
AS
BEGIN
    SELECT * FROM news WHERE isHome = 1
END
go

-- 7. Truy vấn 3 bản tin được Post mới nhất
CREATE PROCEDURE spGetLatestNews
AS
BEGIN
    SELECT TOP 3 * FROM news ORDER BY postedDate DESC
END
go

-- 8. Truy vấn các bản tin của phóng viên theo id User
CREATE PROCEDURE spGetNewsByAuthor
@AuthorId uniqueidentifier
AS
BEGIN
    SELECT * FROM news WHERE authorId = @AuthorId
END
go

-- 2. Truy vấn bản tin theo khóa chính
CREATE PROCEDURE spGetNewsById
@Id uniqueidentifier
AS
BEGIN
    SELECT * FROM news WHERE id = @Id
END
go

-- 6. Truy vấn 3 bản tin có lượng view (viewcount) cao nhất
CREATE PROCEDURE spGetTopViewedNews
AS
BEGIN
    SELECT TOP 3 * FROM news ORDER BY viewCount DESC
END
go

-- 3. Thêm mới bản tin
CREATE PROCEDURE spInsertCategory
@Name NVARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO category (name)
    VALUES (@Name);

    SELECT SCOPE_IDENTITY() AS NewCategoryId;
END
go

-- 3. Thêm mới bản tin
CREATE PROCEDURE spInsertNews
    @Content nvarchar(max),
    @Image nvarchar(255),
    @IsHome bit,
    @PostedDate date,
    @Title nvarchar(255),
    @AuthorId uniqueidentifier,
    @CategoryId int
AS
BEGIN
    INSERT INTO news (content, image, isHome, postedDate, title, authorId, categoryId)
    VALUES (@Content, @Image, @IsHome, @PostedDate, @Title, @AuthorId, @CategoryId)
END
go

CREATE   PROC spNewsAdmin
AS
begin
    SELECT n.id, n.title, c.name, n.categoryId, u.fullName, n.postedDate, n.viewCount, n.isHome, n.content, n.image FROM news n
                                                                                                                             inner join dbo.category c on c.id = n.categoryId
                                                                                                                             inner join dbo.[user] u on u.id = n.authorId
    ORDER BY n.postedDate
end
go

CREATE PROCEDURE spSelectById
@Id uniqueidentifier
AS
BEGIN
    SET NOCOUNT ON;

    SELECT n.id, n.title, n.content, n.image, n.isHome, n.postedDate, n.viewCount,
           c.id AS categoryId, c.name AS categoryName,
           u.id AS authorId, u.fullName AS authorName
    FROM news n
             INNER JOIN category c ON n.categoryId = c.id
             INNER JOIN [user] u ON n.authorId = u.id
    WHERE n.id = @Id
END
go

CREATE   PROC spUpdateAndReturnNews
    @id UNIQUEIDENTIFIER,
    @title NVARCHAR(255),
    @content NVARCHAR(max),
    @image NVARCHAR(255)
AS
begin
    UPDATE news
    SET title = @title,
        content = @content,
        image = @image
    WHERE id = @id
end
go

CREATE   PROC spUpdateNews
    @id UNIQUEIDENTIFIER,
    @title NVARCHAR(255),
    @content NVARCHAR(max),
    @image NVARCHAR(255),
    @categoryId INT,
    @home BIT
AS
begin
    UPDATE news
    SET title = @title,
        content = @content,
        image = @image,
        categoryId = @categoryId,
        isHome = @home
    WHERE id = @id
end
go

