set @expTime=(select max(experimentStartTime) from ClassifierResults);
select classifierClassName, filterClassName, featureSetSize, wAUC, fpr, fnr from
	ClassifierResults cr where wAUC=(select max(wAUC) 
		from ClassifierResults
		where experimentStartTime=@expTime
		and ClassifierClassName=cr.classifierClassName
		and filterClassName=cr.filterClassName
		)
	and experimentStartTime=@expTime
	group by classifierClassName, filterClassName 
	order by max(wAUC) desc; 
