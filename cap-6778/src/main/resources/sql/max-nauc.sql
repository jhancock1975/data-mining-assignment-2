set @expTime=(select max(experimentStartTime) from ClassifierResults);
select classifierClassName, filterClassName, featureSetSize, nAUC, fpr, fnr from
	ClassifierResults cr where nAUC=(select max(nAUC) 
		from ClassifierResults
		where experimentStartTime=@expTime
		and ClassifierClassName=cr.classifierClassName
		and filterClassName=cr.filterClassName
		)
	and experimentStartTime=@expTime
	group by classifierClassName, filterClassName 
	order by max(nAUC) desc; 
