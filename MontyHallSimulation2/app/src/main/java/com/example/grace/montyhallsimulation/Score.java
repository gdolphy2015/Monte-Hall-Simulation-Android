package com.example.grace.montyhallsimulation;
import android.content.SharedPreferences;

public class Score {
    MainActivity ma;
    private SharedPreferences sharedPreferences;
    private int totalGames;
    private int switchWins;
    private int switchTotal;
    private int stayWins;
    private int stayTotal;


    public Score(SharedPreferences sharedPreferences, MainActivity ma) {
        this.ma = ma;
        this.sharedPreferences = sharedPreferences;

    }

    public void updateLocalScoreVariables() {
        totalGames = sharedPreferences.getInt("totalGames", 0);
        switchWins = sharedPreferences.getInt("switchWins", 0);
        switchTotal = sharedPreferences.getInt("switchTotal", 0);
        stayWins = sharedPreferences.getInt("stayWins", 0);
        stayTotal = sharedPreferences.getInt("stayTotal", 0);
    }

    public void saveScorePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("totalGames", totalGames);
        editor.putInt("switchWins", switchWins);
        editor.putInt("switchTotal", switchTotal);
        editor.putInt("stayWins", stayWins);
        editor.putInt("stayTotal", stayTotal);

        System.out.println(sharedPreferences.getInt("totalGames", -1) + "  -------------- ");

        editor.apply();
    }

    public void incrementTotalGames() {
        totalGames++;
    }

    public void incrementSwitchWins() {
        switchWins++;
    }

    public void incrementSwitchTotal() {
        switchTotal++;
    }

    public void incrementStayWins() {
        stayWins++;
    }

    public void incrementStayTotal() {
        stayTotal++;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public int getSwitchWins() {
        return switchWins;
    }

    public int getSwitchTotal() {
        return switchTotal;
    }

    public int getStayWins() {
        return stayWins;
    }

    public int getStayTotal() {
        return stayTotal;
    }

    public String getNumericalScore(int wins, int total) {
        return String.format("%.2f", (wins * 1.0)/total*100.0);
    }


}
