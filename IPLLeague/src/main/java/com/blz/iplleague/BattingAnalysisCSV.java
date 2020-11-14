package com.blz.iplleague;

import com.opencsv.bean.CsvBindByName;

public class BattingAnalysisCSV {

	@CsvBindByName(column = "POS")
	public int pos;

	@CsvBindByName(column = "PLAYER")
	public String player;

	@CsvBindByName(column = "Mat")
	public int mat;

	@CsvBindByName(column = "Inns")
	public int inns;

	@CsvBindByName(column = "NO")
	public int no;

	@CsvBindByName(column = "Runs")
	public int runs;

	@CsvBindByName(column = "HS")
	public int hs;

	@CsvBindByName(column = "Avg")
	public double average;

	@CsvBindByName(column = "BF")
	public int bf;

	@CsvBindByName(column = "SR")
	public double sr;

	@CsvBindByName(column = "100")
	int century;

	@CsvBindByName(column = "50")
	public int halfcentury;

	@CsvBindByName(column = "4s")
	public int fours;

	@CsvBindByName(column = "6s")
	public int sixes;

	public int getFours() {
		return fours;
	}

	public int getSixes() {
		return sixes;
	}

	public double getSR() {
		return sr;
	}
	
	public double getAvg() {
		return average;
	}

	@Override
	public String toString() {
		return "BattingAnalysisCSV [ pos= " + pos + ", Player= " + player + ", Mat= " + mat + ", Inns= " + inns
				+ ", No= " + no + ", Runs= " + runs + ", Hs= " + hs + ", Avg= " + average + ", Bf= " + bf + ", Sr= "
				+ sr + ", Century= " + century + ", Half-Century= " + halfcentury + ", Fours= " + fours + ", Sixes+ "
				+ sixes + " ]";
	}

}
