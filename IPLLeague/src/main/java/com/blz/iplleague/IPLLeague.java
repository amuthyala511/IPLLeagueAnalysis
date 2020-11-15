package com.blz.iplleague;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class IPLLeague {
	public static List<BattingAnalysisCSV> battingList;
	public static List<BowlingAnalysisCSV> bowlingList;
	public static Map<BattingAnalysisCSV, BowlingAnalysisCSV> iplPlayersCSV;

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
		if (bowlingList == null || bowlingList.size() == 0) {
			throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_SUCH_FILE);
		}
		Comparator<BowlingAnalysisCSV> censusComparator = Comparator.comparing(census -> census.sr);
		this.sort(censusComparator);
		String sortedCensusJson = new Gson().toJson(this.bowlingList);
		return sortedCensusJson;
	}

	public List<BowlingAnalysisCSV> getBowlerWithBestEconomyRate(String csvFilePath) throws CensusAnalyserException {
		loadCSVData(csvFilePath);
		List<BowlingAnalysisCSV> bowlerWithBestEconomy = bowlingList.stream()
				.sorted(Comparator.comparingDouble(BowlingAnalysisCSV::getEcon)).collect(Collectors.toList());
		Collections.reverse(bowlerWithBestEconomy);
		return bowlerWithBestEconomy;
	}

	public List<BowlingAnalysisCSV> getBowlerWithBestStrikingRateWith4wAnd5w(String csvFilePath)
			throws IOException, CensusAnalyserException {
		loadCSVData(csvFilePath);
		Integer bowlerWith4wAnd5w = bowlingList.stream().map(player -> (player.get4w() + player.get5w()))
				.max(Double::compare).get();
		List<BowlingAnalysisCSV> bowlerWithBestStrikingRate4wAnd5w = bowlingList.stream()
				.filter(player -> player.get4w() + player.get5w() == bowlerWith4wAnd5w).collect(Collectors.toList());
		double bowlerWithBestStrikeRate = bowlerWithBestStrikingRate4wAnd5w.stream().map(BowlingAnalysisCSV::getSR)
				.max(Double::compare).get();
		return bowlerWithBestStrikingRate4wAnd5w.stream().filter(player -> player.getSR() == bowlerWithBestStrikeRate)
				.collect(Collectors.toList());
	}

	public List<BowlingAnalysisCSV> getBowlerWithBestStrikingRateWithBestAverage(String csvFilePath)
			throws IOException, CensusAnalyserException {
		loadCSVData(csvFilePath);
		double bestAverages = bowlingList.stream().map(BowlingAnalysisCSV::getAvg).max(Double::compare).get();
		List<BowlingAnalysisCSV> bowlerWithBestAverage = bowlingList.stream()
				.filter(player -> player.getAvg() == bestAverages).collect(Collectors.toList());
		double bowlerWithBestStrikeRate = bowlerWithBestAverage.stream().map(BowlingAnalysisCSV::getSR)
				.max(Double::compare).get();
		return bowlerWithBestAverage.stream().filter(player -> player.getSR() == bowlerWithBestStrikeRate)
				.collect(Collectors.toList());
	}

	public List<BowlingAnalysisCSV> getBowlersWhoTookMaxWicketsWithBestAverage(String csvFilePath)
			throws IOException, CensusAnalyserException {
		loadCSVData(csvFilePath);
		int maxWickets = bowlingList.stream().map(BowlingAnalysisCSV::getWkts).max(Integer::compare).get();
		List<BowlingAnalysisCSV> bowlerWithMaxWickets = bowlingList.stream()
				.filter(player -> player.getWkts() == maxWickets).collect(Collectors.toList());
		double bowlerWithBestAverage = bowlerWithMaxWickets.stream().map(BowlingAnalysisCSV::getAvg)
				.max(Double::compare).get();
		return bowlerWithMaxWickets.stream().filter(player -> player.getAvg() == bowlerWithBestAverage)
				.collect(Collectors.toList());
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

	public static List<String> CricketerWithBestBattingAndBowlingAverage() throws IOException, CensusAnalyserException {
		List<String> batbowlAvgList = new ArrayList<>();
		List<BattingAnalysisCSV> playerWithBestBattingAvg = battingList.stream()
				.sorted(Comparator.comparingDouble(player -> player.average)).collect(Collectors.toList());
		Collections.reverse(playerWithBestBattingAvg);
		List<BowlingAnalysisCSV> playerWithBestBowlingAvg = bowlingList.stream()
				.sorted(Comparator.comparingDouble(player -> player.average)).collect(Collectors.toList());
		for (BattingAnalysisCSV battingList : playerWithBestBattingAvg) {
			for (BowlingAnalysisCSV bowlingList : playerWithBestBowlingAvg) {
				if (BattingAnalysisCSV.player.equals(BattingAnalysisCSV.player)) {
					batbowlAvgList.add(BattingAnalysisCSV.player);
				}
			}
		}
		return batbowlAvgList;
	}
}
