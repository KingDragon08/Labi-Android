package cn.wildfire.chat.app.model;

public class GameInfoGame {
    private String created_time;
    private String groupId;
    private String bankerName;
    private int id;
    private String bonusId;
    private String banker;
    private String status;
    private String bankerBonus;
    private String bankerWin;

    public String getBankerWin() {
        return String.valueOf(Float.valueOf(bankerWin) / 100);
    }

    public void setBankerWin(String bankerWin) {
        this.bankerWin = bankerWin;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getBankerName() {
        return bankerName;
    }

    public void setBankerName(String bankerName) {
        this.bankerName = bankerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBonusId() {
        return bonusId;
    }

    public void setBonusId(String bonusId) {
        this.bonusId = bonusId;
    }

    public String getBanker() {
        return banker;
    }

    public void setBanker(String banker) {
        this.banker = banker;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBankerBonus() {
        if (bankerBonus == null) {
            return "-";
        }
        if (bankerBonus != null && Integer.valueOf(bankerBonus) == -1) {
            return "头包";
        }
        return String.valueOf(Float.valueOf(bankerBonus) / 100);
    }

    public void setBankerBonus(String bankerBonus) {
        this.bankerBonus = bankerBonus;
    }
}
