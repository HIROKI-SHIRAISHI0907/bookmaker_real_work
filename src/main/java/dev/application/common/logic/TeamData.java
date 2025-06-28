package dev.application.common.logic;

/**
 * チームデータ
 * @author shiraishitoshio
 *
 */
public class TeamData {

	/** チーム名*/
    String teamName;

    /** 平均 */
    double avg;

    /** データ */
    String data;

    /** suffix */
    String suffix;

    /**
     * コンストラクタ
     * @param teamName
     * @param avg
     * @param data
     * @param suffix
     */
    public TeamData(String teamName, double avg, String data, String suffix) {
        this.teamName = teamName;
        this.avg = avg;
        this.data = data;
        this.suffix = suffix;
    }
}
