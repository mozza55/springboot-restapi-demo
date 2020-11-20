
drop table if exists Post;
drop table if exists Member;
create table Member
(
    member_id int primary key auto_increment,
    user_id varchar(50) unique,
    name varchar(50) not null,
    password varchar(50) not null
);

create table Post
(
    post_id INT primary key auto_increment,
    member_id int not null,
    content varchar(255),
    foreign key (member_id) references Member(member_id),
    created_at datetime not null default current_timestamp
);