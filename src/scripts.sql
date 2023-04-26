select * from student;
select * from student where age > 11 and age < 20;
select name from student;
select * from student where name like '%o%';
select * from student where age < id;
select * from student ORDER BY age;
select s.* from student as s, faculty as f
where s.faculty_id = f.id
  and f.id = 6;