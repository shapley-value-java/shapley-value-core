package org.shapleyvalue.extension.parlement;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.shapleyvalue.util.FactorialUtil;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParlementCalculationTest {

	private final Logger logger = LoggerFactory.getLogger(ParlementCalculationTest.class);

	@Test
	public void testExampleMicronesia() {

		ParlementCalculation parlementCalculation = new ParlementCalculation.ParlementCalculationBuilder()
				.addParty("A", 45).addParty("B", 25).addParty("C", 15).addParty("D", 15).build();
		Map<String, Double> output = null;
		while (!parlementCalculation.isLastReached())
			output = parlementCalculation.calculate(5);

		double phiA = output.get("A");
		double phiB = output.get("B");
		double phiC = output.get("C");
		double phiD = output.get("D");

		assertEquals(phiA, 0.5, 0.001);
		assertEquals(phiB, 0.167, 0.001);
		assertEquals(phiC, 0.167, 0.001);
		assertEquals(phiD, 0.167, 0.001);
	}

	@Test
	//@Ignore
	public void testExampleBelgium() {

		ParlementCalculation parlementCalculation = new ParlementCalculation.ParlementCalculationBuilder()
				.addParty("NVA", 31).addParty("PS", 23).addParty("MR", 20).addParty("CD&V", 18).addParty("openVLD", 14)
				.addParty("PSA", 13).addParty("EcoloGroen", 12).addParty("CDH", 9).addParty("VB", 3).addParty("Defi", 2)
				.addParty("PTB", 2).addParty("VW", 2).addParty("PP", 1).build();

		//System.out.println(FactorialUtil.factorial(13) / 100);

		Map<String, Double> output = null;
		//while (!parlementCalculation.isLastReached()) {
			output = parlementCalculation.calculate(100_000, true);

			double phiNVA = output.get("NVA");
			logger.info("phiNVA= {}", String.format("%.3f", phiNVA));

			double phiPS = output.get("PS");
			logger.info("phiPS= {}", String.format("%.3f", phiPS));

			double phiMR = output.get("MR");
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
			logger.info("phiPP= {}", String.format("%.3f", phiPP));
		//}
	}

}
