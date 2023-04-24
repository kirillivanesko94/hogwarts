select * from student;
select * from student where age > 11 and age < 20;
select name from student;
select * from student where name like '%o%';
select * from student where age < id;
select * from student ORDER BY age;
select * from student, faculty
where student.faculty_id = faculty.id
  and faculty.id = 6;