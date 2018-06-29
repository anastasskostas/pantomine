package com.kostanas.pantomine.infrastructure;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Anastasios on 2/20/2017.
 */

public class PantoApplication extends Application {
    private ArrayList<Team> selectedTeams;
    private ArrayList<String> movies;
    private int roundTime;
    private int totalWins;
    private int currentTeam;
    private boolean isNewGame;
    private int winner;

    @Override
    public void onCreate(){
        super.onCreate();
        roundTime = 60;
        totalWins = 10;
        isNewGame = true;
        selectedTeams = new ArrayList<>();
        movies = new ArrayList<>();
        winner = -1;
    }

    public ArrayList<Team> getSelectedTeams() {
        return selectedTeams;
    }

    public void setSelectedTeams(ArrayList<Team> selectedTeams) {
        this.selectedTeams = selectedTeams;
    }

    public ArrayList<String> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<String> movies) {
        this.movies = movies;
    }

    public int getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(int roundTime) {
        this.roundTime = roundTime;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public int getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(int currentTeam) {
        this.currentTeam = currentTeam;
    }

    public boolean isNewGame() {
        return isNewGame;
    }

    public void setNewGame(boolean newGame) {
        this.isNewGame = newGame;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }
}
