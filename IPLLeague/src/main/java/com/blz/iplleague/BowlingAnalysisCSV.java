package com.blz.iplleague;

import com.opencsv.bean.CsvBindByName;

public class BowlingAnalysisCSV {
	
	@CsvBindByName(column = "POS")
	public int pos;
	
	@CsvBindByName(column = "PLAYER")
	public String player;
	
	@CsvBindByName(column = "Mat")
	public int mat;
	
	@CsvBindByName(column = "Inns")
	public int inns;
	
	@CsvBindByName(column = "Ov")
	public int ov;
	
	@CsvBindByName(column = "Runs")
	public int runs;
	
	@CsvBindByName(column = "Wkts")
	public int wkts;
	
	@CsvBindByName(column = "BBI")
	public int bbi;
	
	@CsvBindByName(column = "Avg")
	public double average;
	
	@CsvBindByName(column = "Econ")
	public int economy;
	
	@CsvBindByName(column = "SR")
	public double sr;
	
	@CsvBindByName(column = "4w")
	public int fourwkts;
	
	@CsvBindByName(column = "5w")
	public int fivewkts;
	
	public double getAvg() {
		return average;
	}
	
	public double getSR() {
		return sr;
	}
	
	public int getEcon() {
		return economy;
	}
	
	@Override
	public String toString() {
		return "BowlingAnalysisCSV [ pos= "+ pos +", Player= "+ player +", Mat= "+ mat +", Inns= "+ inns
				+", Overs= "+ ov +", Runs= "+ runs +", Wickets= "+ wkts +", BBI= "+ bbi
				+", Avg= "+ average +", Economy= "+ economy +", SR= "+ sr
				+", 4w= "+ fourwkts +", 5w= "+ fivewkts +" ]";
	}
}
