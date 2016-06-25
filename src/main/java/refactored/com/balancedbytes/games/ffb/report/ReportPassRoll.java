package refactored.com.balancedbytes.games.ffb.report;


import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ReportPassRoll extends ReportSkillRoll {
    private boolean fFumble;
    private boolean fSafeThrowHold;
    private boolean fHailMaryPass;
    private boolean fBomb;

    public ReportPassRoll() {
        super(ReportId.PASS_ROLL);
    }

    public ReportPassRoll(String pPlayerId, boolean pFumble, int pRoll, boolean pBomb) {
        this(pPlayerId, !pFumble, pRoll, 2, pFumble, false, pBomb);
        this.fHailMaryPass = true;
    }

    public ReportPassRoll(String pPlayerId, boolean pSuccessful, int pRoll, int pMinimumRoll, boolean pFumble, boolean pSafeThrowHold, boolean pBomb) {
        super(ReportId.PASS_ROLL, pPlayerId, pSuccessful, pRoll, pMinimumRoll);
        this.fFumble = pFumble;
        this.fFumble = pFumble;
        this.fSafeThrowHold = pSafeThrowHold;
        this.fBomb = pBomb;
        this.fHailMaryPass = false;
    }

    public static ReportPassRoll regularPass(String pPlayerId, boolean pSuccessful, int pRoll, int pMinimumRoll, boolean pFumble, boolean pSafeThrowHold, boolean pBomb) {
        return new ReportPassRoll(pPlayerId,  pSuccessful,  pRoll,  pMinimumRoll,  pFumble,  pSafeThrowHold, pBomb);
    }

    public static ReportPassRoll hailMaryPass(String pPlayerId, boolean pFumble, int pRoll, boolean pBomb) {
        return new ReportPassRoll(pPlayerId, pFumble, pRoll, pBomb);
    }

    @Override
    public ReportId getId() {
        return ReportId.PASS_ROLL;
    }

    public boolean isFumble() {
        return this.fFumble;
    }

    public boolean isHeldBySafeThrow() {
        return this.fSafeThrowHold;
    }

    public boolean isHailMaryPass() {
        return this.fHailMaryPass;
    }

    public boolean isBomb() {
        return this.fBomb;
    }

    @Override
    public IReport transform() {
        if (this.isHailMaryPass()) {
            return new ReportPassRoll(this.getPlayerId(), this.isFumble(), this.getRoll(), this.isBomb());
        }
        return new ReportPassRoll(this.getPlayerId(), this.isSuccessful(), this.getRoll(), this.getMinimumRoll(), this.isFumble(), this.isHeldBySafeThrow(), this.isBomb());
    }

    @Override
    public ReportPassRoll initFrom(JsonValue pJsonValue) {
        super.initFrom(pJsonValue);
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        this.fFumble = IJsonOption.FUMBLE.getFrom(jsonObject);
        this.fSafeThrowHold = IJsonOption.SAFE_THROW_HOLD.getFrom(jsonObject);
        this.fHailMaryPass = IJsonOption.HAIL_MARY_PASS.getFrom(jsonObject);
        this.fBomb = IJsonOption.BOMB.getFrom(jsonObject);
        return this;
    }
}

