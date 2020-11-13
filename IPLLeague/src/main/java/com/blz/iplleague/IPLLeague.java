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
	public static List<BattingAnalysisCSV> battingList;

	public int loadCSVData(String indiaCensusCSVFilePath) throws CensusAnalyserException {
		try (Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCSVFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			battingList = csvBuilder.getCSVFileList(reader, BattingAnalysisCSV.class);
			return battingList.size();
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}
	}

	public String getTopBattingAverage(String csvFilePath) throws CensusAnalyserException {
		loadCSVData(csvFilePath);
		if (battingList == null || battingList.size() == 0) {
			throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}
		Comparator<BattingAnalysisCSV> censusComparator = Comparator.comparing(census -> census.average);
		this.sort(censusComparator);
		String sortedCensusJson = new Gson().toJson(this.battingList);
		return sortedCensusJson;
	}

	public String getTopStrikingRate(String csvFilePath) throws CensusAnalyserException {
		loadCSVData(csvFilePath);
		if (battingList == null || battingList.size() == 0) {
			throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}
		Comparator<BattingAnalysisCSV> censusComparator = Comparator.comparing(census -> census.sr);
		this.sort(censusComparator);
		String sortedCensusJson = new Gson().toJson(this.battingList);
		return sortedCensusJson;
	}

	public List<BattingAnalysisCSV> getCricketerWithMax4s(String csvFilePath) throws IOException, CensusAnalyserException {
		loadCSVData(csvFilePath);
		List<BattingAnalysisCSV> max4s = battingList.stream()
					.sorted(player1, player2) -> Double.compare(player1.getFours(), player2.getFours())
					.collect(Collectors.toList());
		Collections.reverse(max4s);
		return max4s;
	}

	public List<BattingAnalysisCSV> getCricketerWithMax6s(String csvFilePath) throws IOException, CensusAnalyserException {
		loadCSVData(csvFilePath);
		List<BattingAnalysisCSV> max6s = battingList.stream()
					.sorted(player1, player2) -> Double.compare(player1.getSixes(), player2.getSixes())
					.collect(Collectors.toList());
		Collections.reverse(max6s);
		return max6s;
	}

	public void sort(Comparator<BattingAnalysisCSV> censusComparator) {
		for (int i = 0; i < battingList.size(); i++) {
			for (int j = 0; j < battingList.size() - i - 1; j++) {
				BattingAnalysisCSV census1 = battingList.get(j);
				BattingAnalysisCSV census2 = battingList.get(j + 1);
				if (censusComparator.compare(census1, census2) > 0) {
					battingList.set(j, census2);
					battingList.set(j + 1, census1);
				}
			}
		}
	}
}
