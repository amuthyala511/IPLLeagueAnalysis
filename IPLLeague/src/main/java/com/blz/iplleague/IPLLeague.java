package com.blz.iplleague;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class IPLLeague {
	public static List<BowlingAnalysisCSV> bowlingList;

	public int loadCSVData(String indiaCensusCSVFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCSVFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			bowlingList = csvBuilder.getCSVFileList(reader, BowlingAnalysisCSV.class);
			return bowlingList.size();
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}
	}

	public String getTopBowlingAverage(String csvFilePath) throws CensusAnalyserException {
		loadCSVData(csvFilePath);
		if (bowlingList == null || bowlingList.size() == 0) {
			throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}
		Comparator<BowlingAnalysisCSV> censusComparator = Comparator.comparing(census -> census.average);
		this.sort(censusComparator);
		String sortedCensusJson = new Gson().toJson(this.bowlingList);
		return sortedCensusJson;
	}
	
	public String getBowlersTopStrikingRate(String csvFilePath) throws CensusAnalyserException {
		loadCSVData(csvFilePath);
		if(bowlingList == null || bowlingList.size() == 0) {
			throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}
		Comparator<BowlingAnalysisCSV> censusComparator = Comparator.comparing(census -> census.sr);
		this.sort(censusComparator);
		String sortedCensusJson = new Gson().toJson(this.bowlingList);
		return sortedCensusJson;
	}
	
	public String getBowlerWithBestEconomyRate(String csvFilePath) throws CensusAnalyserException {
		loadCSVData(csvFilePath);
		if(bowlingList == null || bowlingList.size() == 0) {
			throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}
		Comparator<BowlingAnalysisCSV> censusComparator = Comparator.comparing(census -> census.economy);
		this.sort(censusComparator);
		String sortedCensusJson = new Gson().toJson(this.bowlingList);
		return sortedCensusJson;
	}

	public void sort(Comparator<BowlingAnalysisCSV> censusComparator) {
		for (int i = 0; i < bowlingList.size(); i++) {
			for (int j = 0; j < bowlingList.size() - i - 1; j++) {
				BowlingAnalysisCSV census1 = bowlingList.get(j);
				BowlingAnalysisCSV census2 = bowlingList.get(j + 1);
				if (censusComparator.compare(census1, census2) > 0) {
					bowlingList.set(j, census2);
					bowlingList.set(j + 1, census1);
				}
			}
		}
	}
}
