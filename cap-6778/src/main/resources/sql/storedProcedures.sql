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
	select classifierClassName from ClassifierResults 
		where experimentStartTime = lastExpTime;
END //
delimiter ;


DROP PROCEDURE IF EXISTS getAttribEvalNames;
delimiter //
CREATE PROCEDURE getAttribEvalNames() 
BEGIN
	declare lastExpTime timestamp;
	select max(experimentStartTime) from ClassifierResults into lastExpTime;
	select filterClassName from ClassifierResults 
		where experimentStartTime = lastExpTime;
END //
delimiter ;