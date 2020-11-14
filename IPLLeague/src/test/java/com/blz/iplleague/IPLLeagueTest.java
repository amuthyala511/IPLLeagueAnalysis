package com.blz.iplleague;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

public class IPLLeagueTest {

	private static final String BOWLING_FILE_PATH = "C:\\Users\\Muthyala Aishwarya\\Downloads\\IPL2019FactsheetMostWkts";
	private static final double DELTA = 1e-15;

	private static IPLLeague iplLeagueAnalysis;

	@BeforeClass
	public static void createObj() {
		iplLeagueAnalysis = new IPLLeague();
		System.out.println("Welcome to IPL League Analyser Problem");
	}

	@Test
	public void givenLoadBowlingFile_shouldReturnNoOfRecords() throws CensusAnalyserException {
		int numOfEntries = iplLeagueAnalysis.loadCSVData(BOWLING_FILE_PATH);
		Assert.assertEquals(99, numOfEntries);
	}

	@Test
	public void givenBowlingData_ShouldSortBowlingAverage_AndReturnResult() {
		try {
			String sortedIPLBowlingData = iplLeagueAnalysis.getTopBowlingAverage(BOWLING_FILE_PATH);
			BowlingAnalysisCSV[] bowlingData = new Gson().fromJson(sortedIPLBowlingData, BowlingAnalysisCSV[].class);
			Assert.assertEquals(166, bowlingData[bowlingData.length - 1].average, DELTA);
		} catch (CensusAnalyserException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenBowlingData_ShouldSortStrikingRate_AndReturnResult() {
		try {
			String sortedIPLBowlingData = iplLeagueAnalysis.getBowlersTopStrikingRate(BOWLING_FILE_PATH);
			BowlingAnalysisCSV[] bowlingData = new Gson().fromJson(sortedIPLBowlingData, BowlingAnalysisCSV[].class);
            Assert.assertEquals(120, bowlingData[bowlingData.length - 1].sr, DELTA);
        } catch (CensusAnalyserException e) {
        	e.printStackTrace();
        }
	}
	
	@Test
	public void givenBowlingData_ShouldSortEconomyRate_AndReturnResult() {
		try {
			String sortedIPLBowlingData = iplLeagueAnalysis.getBowlersTopStrikingRate(BOWLING_FILE_PATH);
			BowlingAnalysisCSV[] bowlingData = new Gson().fromJson(sortedIPLBowlingData, BowlingAnalysisCSV[].class);
            Assert.assertEquals(13.11, bowlingData[bowlingData.length - 1].economy, DELTA);
        } catch (CensusAnalyserException e) {
        	e.printStackTrace();
        }
	}
	
	@Test
	public void givenBowlingData_ShouldSortBowlers_WithBestStrikingRate_With4wAnd5w_AndReturnResult() throws IOException, CensusAnalyserException {
		Assert.assertEquals("Lasith Malinga", new IPLLeague().getBowlerWithBestStrikingRateWith4wAnd5w(BOWLING_FILE_PATH).get(0).player);
	}
}
