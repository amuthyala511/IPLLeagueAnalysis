package com.blz.iplleague;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

public class IPLLeagueTest {

	private static final String BATTING_FILE_PATH = "C:\\Users\\Muthyala Aishwarya\\Downloads\\IPL2019MostRuns";
	private static final double DELTA = 1e-15;

	private static IPLLeague iplLeagueAnalysis;

	@BeforeClass
	public static void createObj() {
		iplLeagueAnalysis = new IPLLeague();
		System.out.println("Welcome to IPL League Analyser Problem");
	}

	@Test
	public void givenLoadBatingFile_shouldReturnNoOfRecords() throws CensusAnalyserException {
		int numOfEntries = iplLeagueAnalysis.loadCSVData(BATTING_FILE_PATH);
		Assert.assertEquals(101, numOfEntries);
	}

	@Test
	public void givenBatingData_ShouldSortBattingAverage_AndReturnResult() {
		try {
			String sortedIPLBattingData = iplLeagueAnalysis.getTopBattingAverage(BATTING_FILE_PATH);
			BattingAnalysisCSV[] battingData = new Gson().fromJson(sortedIPLBattingData, BattingAnalysisCSV[].class);
			Assert.assertEquals(83.2, battingData[battingData.length - 1].average, DELTA);
		} catch (CensusAnalyserException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void givenBattingData_ShouldSortStrikingRate_AndReturnResult() {
		try {
			String sortedIPLBattingData = iplLeagueAnalysis.getTopStrikingRate(BATTING_FILE_PATH);
			BattingAnalysisCSV[] battingData = new Gson().fromJson(sortedIPLBattingData, BattingAnalysisCSV[].class);
			Assert.assertEquals(333.33, battingData[battingData.length - 1].sr, DELTA);
		} catch (CensusAnalyserException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void givenBattingData_ShouldSortCricketers_WithMax4s_AndReturnResult()
			throws IOException, CensusAnalyserException {
		List<BattingAnalysisCSV> list = new IPLLeague().getCricketerWithMax4s(BATTING_FILE_PATH);
		Assert.assertEquals("Shikhar Dhawan", new IPLLeague().getCricketerWithMax4s(BATTING_FILE_PATH).get(0).player);
	}

	@Test
	public void givenBattingData_ShouldSortCricketers_WithMax6s_AndReturnResult()
			throws IOException, CensusAnalyserException {
		List<BattingAnalysisCSV> list = new IPLLeague().getCricketerWithMax6s(BATTING_FILE_PATH);
		Assert.assertEquals("Andre Russell", new IPLLeague().getCricketerWithMax6s(BATTING_FILE_PATH).get(0).player);
	}

	@Test
	public void givenBattingData_ShouldSortCricketers_WithBestStrikingRate_With4sAnd6s_AndReturnResult()
			throws IOException, CensusAnalyserException {
		Assert.assertEquals("Andre Russell",
				new IPLLeague().getCricketerWithBestStrikingRate4sAnd6s(BATTING_FILE_PATH).get(0).player);
	}

	@Test
	public void givenBattingData_ShouldSortCricketers_WithBestAverages_WithBestStrikingRates_AndRetuenResult()
			throws IOException, CensusAnalyserException {
		Assert.assertEquals("MS Dhoni",
				new IPLLeague().getCricketerWithBestStrikingRatesWithBestAverage(BATTING_FILE_PATH).get(0).player);
	}
}
