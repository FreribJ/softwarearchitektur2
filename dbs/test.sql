update attraction set name = 'neutest' where name = 'test';
update attraction set name = ?         where name = ?;

delete from attraction where name = 'jannes' ;

select tour.*, (select string_agg(attractions_name, ',') as test from tour_attractions) as test from tour;


select nearest_tour_name from attraction_nearest_tour where attraction_name = 'test';


select attractions_name from tour_attractions where tour_name = 'tour';