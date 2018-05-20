package microbenchmark;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.shapleyvalue.application.facade.CoalitionStrategy;
import org.shapleyvalue.application.facade.ShapleyApplicationException;
import org.shapleyvalue.application.impl.parliament.ParliamentCalculation;
import org.shapleyvalue.application.impl.parliament.multithread.ParliamentCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public class ParliamentCalculationBenchmarkTest {

	private final Logger logger = LoggerFactory.getLogger(ParliamentCalculationBenchmarkTest.class);

	
	private void showAndCheckResult(Map<String, Double> output) {
		double phiNVA = output.get("NVA");
		logger.info("phiNVA= {}", String.format("%.3f", phiNVA));
		assertEquals(phiNVA, 0.230,0.01);

		double phiPS = output.get("PS");
		logger.info("phiPS= {}", String.format("%.3f", phiPS));

		/*double phiMR = output.get("MR");
		logger.info("phiMR= {}", String.format("%.3f", phiMR));

		double phiCDV = output.get("CD&V");
		logger.info("phiCD&V= {}", String.format("%.3f", phiCDV));

		double phiOpenVld = output.get("openVLD");
		logger.info("phiOpenVld= {}", String.format("%.3f", phiOpenVld));

		double phiPSA = output.get("PSA");
		logger.info("phiPSA= {}", String.format("%.3f", phiPSA));

		double phiEcoloGroen = output.get("EcoloGroen");
		logger.info("phiEcoloGroen= {}", String.format("%.3f", phiEcoloGroen));

		double phiCDH = output.get("CDH");
		logger.info("phiCDH= {}", String.format("%.3f", phiCDH));

		double phiVB = output.get("VB");
		logger.info("phiVB= {}", String.format("%.3f", phiVB));

		double phiDefi = output.get("Defi");
		logger.info("phiDefi= {}", String.format("%.3f", phiDefi));

		double phiPTB = output.get("PTB");
		logger.info("phiPTB= {}", String.format("%.3f", phiPTB));

		double phiVW = output.get("VW");
		logger.info("phiVW= {}", String.format("%.3f", phiVW));

		double phiPP = output.get("PP");
		logger.info("phiPP= {}", String.format("%.3f", phiPP));*/
	}


	@Test
	public void testExampleBelgium() throws ShapleyApplicationException {
		
		long sampleSize = 100_000;
		
		Stopwatch stopwatch = Stopwatch.createStarted();
		ParliamentCalculation parlementCalculation1 = new ParliamentCalculation.ParliamentCalculationBuilder()
				.addParty("NVA", 31).addParty("PS", 23).addParty("MR", 20).addParty("CD&V", 18).addParty("openVLD", 14)
				.addParty("PSA", 13).addParty("EcoloGroen", 12).addParty("CDH", 9).addParty("VB", 3).addParty("Defi", 2)
				.addParty("PTB", 2).addParty("VW", 2).addParty("PP", 1).build();

		Map<String, Double> output = parlementCalculation1.calculate(sampleSize, CoalitionStrategy.RANDOM);
	
		stopwatch.stop();
		long duration1 = stopwatch.elapsed(TimeUnit.SECONDS);
		logger.info("that took: {}", duration1);
		
		showAndCheckResult(output);
		
		
		stopwatch.reset();
		stopwatch.start();
		ParliamentCalculationService parlementCalculation2 = new ParliamentCalculationService.ParliamentCalculationBuilder()
				.addParty("NVA", 31).addParty("PS", 23).addParty("MR", 20).addParty("CD&V", 18).addParty("openVLD", 14)
				.addParty("PSA", 13).addParty("EcoloGroen", 12).addParty("CDH", 9).addParty("VB", 3).addParty("Defi", 2)
				.addParty("PTB", 2).addParty("VW", 2).addParty("PP", 1).build();

		output = parlementCalculation2.calculate(sampleSize, 1);
	
		stopwatch.stop();
		long duration2 = stopwatch.elapsed(TimeUnit.SECONDS);
		logger.info("that took: {}", duration2);
		
		showAndCheckResult(output);
		
		stopwatch.reset();
		stopwatch.start();
		ParliamentCalculationService parlementCalculation3 = new ParliamentCalculationService.ParliamentCalculationBuilder()
				.addParty("NVA", 31).addParty("PS", 23).addParty("MR", 20).addParty("CD&V", 18).addParty("openVLD", 14)
				.addParty("PSA", 13).addParty("EcoloGroen", 12).addParty("CDH", 9).addParty("VB", 3).addParty("Defi", 2)
				.addParty("PTB", 2).addParty("VW", 2).addParty("PP", 1).build();

		output = parlementCalculation3.calculate(sampleSize, 5);
	
		stopwatch.stop();
		long duration3 = stopwatch.elapsed(TimeUnit.SECONDS);
		logger.info("that took: {}", duration3);
		
		showAndCheckResult(output);
		
		assertTrue(duration3<= duration2);
		assertTrue(duration3<= duration1);

	}


}
