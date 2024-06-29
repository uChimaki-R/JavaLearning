use demos;

select *
from (select * from student_course WHERE course_id = 30001) a,
     (select * from student_course WHERE course_id = 30005) b
where a.score > b.score
  and a.student_id = b.student_id;

select student_course.id, avg(student_course.score)
from student_course
group by student_course.id
having avg(student_course.score) > 60;

select a.id, a.name, count(distinct b.course_id), sum(b.score)
from student a,
     student_course b
where a.id = b.student_id
group by a.id;

select a.course_id                               as 课程ID,
       b.name                                    as 课程名称,
       sum(IF(a.score between 85 and 100, 1, 0)) as "[100-85]",
       sum(IF(a.score between 70 and 85, 1, 0))  as "[85-70]",
       sum(IF(a.score between 65 and 70, 1, 0))  as "[70-60]",
       sum(IF(a.score < 60, 1, 0))               as "[<60]"
from student_course a,
     course b
where a.course_id = b.id
group by a.course_id, b.name;