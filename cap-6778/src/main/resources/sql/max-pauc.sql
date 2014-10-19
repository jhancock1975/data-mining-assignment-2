set @expTime=(select max(experimentStartTime) from ClassifierResults);
select classifierClassName, filterClassName, featureSetSize, pAUC, fpr, fnr from
	ClassifierResults cr where pAUC=(select max(pAUC) 
		from ClassifierResults
		where experimentStartTime=@expTime
		and ClassifierClassName=cr.classifierClassName
		and filterClassName=cr.filterClassName
		)
	and experimentStartTime=@expTime
	group by classifierClassName, filterClassName 
	order by max(pAUC) desc;
