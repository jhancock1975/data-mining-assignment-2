use assign4;
DROP PROCEDURE IF EXISTS getLastResults;
delimiter //
CREATE PROCEDURE getLastResults(classifier VARCHAR(255), filter VARCHAR(255)) 
BEGIN
	declare lastExpTime timestamp;
	select max(experimentStartTime) from ClassifierResults into lastExpTime;
	select * from ClassifierResults 
		where experimentStartTime = lastExpTime and 
		classifierClassName=classifier and 
		filterClassName=filter;
END //
delimiter ;

DROP PROCEDURE IF EXISTS getClassifierNames;
delimiter //
CREATE PROCEDURE getClassifierNames() 
BEGIN
	declare lastExpTime timestamp;
	select max(experimentStartTime) from ClassifierResults into lastExpTime;
	select distinct classifierClassName from ClassifierResults r 
		inner join FeatureLists f on r.classifierResultId=f.classifierResultId  
		where experimentStartTime = lastExpTime;
END //
delimiter ;


DROP PROCEDURE IF EXISTS getAttribEvalNames;
delimiter //
CREATE PROCEDURE getAttribEvalNames() 
BEGIN
	declare lastExpTime timestamp;
	select max(experimentStartTime) from ClassifierResults into lastExpTime;
	select distinct filterClassName from ClassifierResults r 
		inner join FeatureLists f on r.classifierResultId=f.classifierResultId  
		where experimentStartTime = lastExpTime;
END //
delimiter ;

/* queries for answering part 2 about overlap with j48 */
set @expTime=(select max(experimentStartTime) from ClassifierResults);
select count(fl.attribute) as 'j48 overlap', classifierClassName, filterClassName 
	from ClassifierResults cl 
	inner join FeatureLists fl on cl.classifierResultId=fl.classifierResultId 
	where fl.attribute in ('GENE1125X', 'GENE1391X', 'GENE1567X', 'GENE2996X', 'GENE3732X', 'GENE3941X') 
		and experimentStartTime=@expTime 
		and featureSetSize=6 
	group by classifierClassName, filterClassName;
