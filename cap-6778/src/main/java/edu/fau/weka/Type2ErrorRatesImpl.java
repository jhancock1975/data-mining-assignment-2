package edu.fau.weka;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class Type2ErrorRatesImpl implements Type2ErrorRatesService {

	public List<Double> getType2ErrorRates() {
		return Arrays.asList(new Double[] {0.125, 0.5, 1.0, 2.0, 4.0, 8.0, 16.0, 20.0,
				21.0, 21.5, 22.0, 22.5, 23.0, 24.0, 28.0, 32.0, 34.0, 36.0, 40.0, 64.0, 128.0});
	}

}
