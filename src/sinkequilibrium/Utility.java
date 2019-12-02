package sinkequilibrium;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class Utility {
	private int numPlayers;
	private int numMoves;
	private int numCurves;
	private class Individual {
		public ArrayList<ArrayList<Double> > periods;
		public ArrayList<Double> offsets;
		public ArrayList<Double> magnitudes;
		public ArrayList<Boolean> sinOrCos; }
	private Individual[] players;
	
	public Utility() {
		Random r=new Random();
		numPlayers=20;
		numMoves=100;
		numCurves=10;
		players=new Individual[numPlayers];
		
		for (int p=0; p<numPlayers; p++) {
			players[p]=new Individual();
			players[p].periods=new ArrayList<ArrayList<Double>>();
			players[p].offsets=new ArrayList<Double>();
			players[p].magnitudes=new ArrayList<Double>();
			players[p].sinOrCos=new ArrayList<Boolean>();
			
			for (int i=0; i<numCurves; i++) {
				ArrayList<Double> currentPeriod=new ArrayList<Double>();
				for (int j=0; j<numPlayers; j++)
					currentPeriod.add(r.nextDouble()*40.0);
				players[p].periods.add(currentPeriod);
				players[p].offsets.add(r.nextDouble()*100.0);
				players[p].magnitudes.add(r.nextDouble()*10.0+20.0);
				players[p].sinOrCos.add(r.nextBoolean());
			}
		}
	}
	
	public int getNumPlayers() { return numPlayers; }
	public int getNumMoves() { return numMoves; }
	
	public void exportUtility() throws IOException {
		FileWriter fw=new FileWriter("utility.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		
		bw.write(""+numPlayers+"\n");
		bw.write(""+numMoves+"\n");
		bw.write(""+numCurves+"\n");
		for (int p=0; p<numPlayers; p++) {
			for (int i=0; i<numCurves; i++) {
				for (int j=0; j<numPlayers; j++)
					bw.write(""+players[p].periods.get(i).get(j)+"\n");
				bw.write(""+players[p].offsets.get(i)+"\n");
				bw.write(""+players[p].magnitudes.get(i)+"\n");
				bw.write(""+(players[p].sinOrCos.get(i).booleanValue()?"0":"1")+"\n");
			}
		}
		bw.close();
		fw.close();
	}
	
	public void importUtility() throws IOException {
		FileReader fr=new FileReader("utility.txt");
		BufferedReader br=new BufferedReader(fr);
		
		String line=br.readLine();
		numPlayers=Integer.parseInt(line);
		line=br.readLine();
		numMoves=Integer.parseInt(line);
		line=br.readLine();
		numCurves=Integer.parseInt(line);

		players=new Individual[numPlayers];
		
		for (int p=0; p<numPlayers; p++) {
			players[p]=new Individual();
			players[p].periods=new ArrayList<ArrayList<Double>>();
			players[p].offsets=new ArrayList<Double>();
			players[p].magnitudes=new ArrayList<Double>();
			players[p].sinOrCos=new ArrayList<Boolean>();
			
			for (int i=0; i<numCurves; i++) {
				ArrayList<Double> currentPeriod=new ArrayList<Double>();
				for (int j=0; j<numPlayers; j++) {
					line=br.readLine();
					double value=Double.parseDouble(line);
					currentPeriod.add(value); }
				players[p].periods.add(currentPeriod);
				line=br.readLine();
				double value=Double.parseDouble(line);
				players[p].offsets.add(value);
				line=br.readLine();
				value=Double.parseDouble(line);
				players[p].magnitudes.add(value);
				line=br.readLine();
				int x=Integer.parseInt(line);
				if (x==0) players[p].sinOrCos.add(true);
				else players[p].sinOrCos.add(false);
			}
		}
		
		br.close();
		fr.close();
	}
	
	public ArrayList<Integer> evaluate(ArrayList<Integer> moves) {
		for (int i=0; i<moves.size(); i++)
			if ((moves.get(i).intValue()>=numMoves)||(moves.get(i).intValue()<0))
				throw new RuntimeException("Illegal move by player "+i);
		ArrayList<Integer> result=new ArrayList<Integer>();
		for (int p=0; p<numPlayers; p++) {
			double totalResult=0.0;
			for (int i=0; i<numCurves; i++) {
				double currentResult=0.0;
				double operand=players[p].offsets.get(i).doubleValue();
				for (int j=0; j<players[p].periods.get(i).size(); j++)
					operand+=((double)moves.get(j).intValue())/players[p].periods.get(i).get(j).doubleValue();
				if (players[p].sinOrCos.get(i).booleanValue())
					currentResult=Math.sin(operand); else currentResult=Math.cos(operand);
				currentResult=players[p].magnitudes.get(i).doubleValue()*currentResult;
				totalResult+=currentResult;
			}
			result.add((int)Math.round(totalResult));
		}
		return result;
	}
}
