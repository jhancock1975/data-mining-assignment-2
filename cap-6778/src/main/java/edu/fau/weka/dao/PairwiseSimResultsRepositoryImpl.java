package edu.fau.weka.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.fau.weka.model.FeatureLists;
import edu.fau.weka.model.PairwiseSimResults;
@Repository
@Transactional
public class PairwiseSimResultsRepositoryImpl extends RepositoryBase implements PairwiseSimResultsRepository{

	public void saveResults(PairwiseSimResults results) {
		currentSession().save(results);
	}

	public List<PairwiseSimResults> getPairwiseSim(String dataSetName,
			double noiseLevel) {
		SQLQuery query = currentSession()
				.createSQLQuery("call getPairwiseSim('"+dataSetName+"'," +
												"'"+noiseLevel+"')").addEntity(PairwiseSimResults.class);
		List<PairwiseSimResults> result = query.list();
		return result;
	}

}
