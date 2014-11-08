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

DROP PROCEDURE IF EXISTS getFeatureSetForDataSetNoiseLevelSelectorFold;
delimiter //
CREATE PROCEDURE getFeatureSetForDataSetNoiseLevelSelectorFold(IN dataSetName varchar(255), 
	IN noiseLevel double, in selectorName varchar(255), in fold int)
BEGIN
	declare lastExpTime timestamp;
	select max(experimentStartTime) from RawFeatureList into lastExpTime;
	select f.* from FeatureLists f,
		RawFeatureList r
		where r.dataSetName like concat(dataSetName, '-', noiseLevel, '-', fold, '%')
			and r.evaluatorName=selectorName
			and r.experimentStartTime=lastExpTime
			and f.attribute != 'CLASS'
			and r.rawFeatureListId=f.experimentResultId;
END //
delimiter ;

/* queries for answering part 2 about overlap with j48 */
set @expTime=(select max(experimentStartTime) from ClassifierResults);
select group_conct(fl.attribute) as 'j48 overlap', classifierClassName, filterClassName 
	from ClassifierResults cl 
	inner join FeatureLists fl on cl.classifierResultId=fl.classifierResultId 
	where fl.attribute in ('GENE1125X', 'GENE1391X', 'GENE1567X', 'GENE2996X', 'GENE3732X', 'GENE3941X') 
		and experimentStartTime=@expTime 
		and featureSetSize=6 
	group by classifierClassName, filterClassName;

/* queries for answering auc questions */
select classifierClassName, filterClassName, pauc, fpr, fnr 
	from ClassifierResults 
	where pAuc=(select max(pAuc) from ClassifierResults where experimentStartTime=@expTime and featureSetSize=6) 
	and experimentStartTime=@expTime and featureSetSize=6;
	
select classifierClassName, filterClassName, nauc, fpr, fnr 
	from ClassifierResults 
	where nAuc=(select max(nAuc) from ClassifierResults where experimentStartTime=@expTime and featureSetSize=6) 
	and experimentStartTime=@expTime and featureSetSize=6;
	
select classifierClassName, filterClassName, wauc, fpr, fnr 
	from ClassifierResults 
	where wAuc=(select max(wAuc) from ClassifierResults where experimentStartTime=@expTime and featureSetSize=6) 
	and experimentStartTime=@expTime and featureSetSize=6;
	
/* for finding optimium AUC values */
select classifierClassName, filterClassName, featureSetSize, pAUC from
	ClassifierResults cr where pAUC=(select max(pAUC) 
		from ClassifierResults
		where experimentStartTime=@expTime
		and ClassifierClassName=cr.classifierClassName
		and filterClassName=cr.filterClassName
		)
	and experimentStartTime=@expTime
	group by classifierClassName, filterClassName 
	order by max(pAUC) desc;
	
/* best 6 feature ranker */
select classifierClassName, filterClassName, max(pAUC) from ClassifierResults where experimentStartTime=@expTime and featureSetSize=6 and classifierClassName='weka.classifiers.lazy.IBk';

/* dump data for anova */
select classifierClassName, filterClassName, featureSetSize, pAuc from ClassifierResults where experimentStartTime=@expTime;