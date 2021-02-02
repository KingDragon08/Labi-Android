package cn.wildfire.chat.app.model;

import java.util.ArrayList;

public class GameInfo {
    private GameInfoGame game;
    private ArrayList<GameInfoBet> bets;
    private String banker;

    public GameInfoGame getGame() {
        return game;
    }

    public void setGame(GameInfoGame game) {
        this.game = game;
    }

    public ArrayList<GameInfoBet> getBets() {
        return bets;
    }

    public void setBets(ArrayList<GameInfoBet> bets) {
        this.bets = bets;
    }

    public String getBanker() {
        return banker;
    }

    public void setBanker(String banker) {
        this.banker = banker;
    }
}
