package com.blz.iplleague;

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
        int numOfEntries=iplLeagueAnalysis.loadCSVData(BATTING_FILE_PATH);
        Assert.assertEquals(101,numOfEntries);
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
	
	public void givenBattingData_ShouldSortStrikingRate_AndReturnResult() {
		try {
			String sortedIPLBattingData = iplLeagueAnalysis.getTopStrikingRate(BATTING_FILE_PATH);
			BattingAnalysisCSV[] battingData = new Gson().fromJson(sortedIPLBattingData, BattingAnalysisCSV[].class);
            Assert.assertEquals(333.33, battingData[battingData.length - 1].sr, DELTA);
        } catch (CensusAnalyserException e) {
        	e.printStackTrace();
        }
	}
}
