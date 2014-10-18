set @expTime=(select max(experimentStartTime) from ClassifierResults);
select group_concat(fl.attribute) as 'j48 overlap', classifierClassName, filterClassName 
	from ClassifierResults cl 
	inner join FeatureLists fl on cl.classifierResultId=fl.classifierResultId 
	where fl.attribute in ('GENE1125X', 'GENE1391X', 'GENE1567X', 'GENE2996X', 'GENE3732X', 'GENE3941X') 
		and experimentStartTime=@expTime 
		and featureSetSize=6 
	group by classifierClassName, filterClassName;

