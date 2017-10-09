package org.butterbrot.ffb.stats.evaluation.turnover;

import com.balancedbytes.games.ffb.report.ReportBlockRoll;
import com.balancedbytes.games.ffb.report.ReportReRoll;
import com.balancedbytes.games.ffb.report.ReportSkillRoll;

public class TurnOverState {
    private ReportReRoll reportReRoll = null;
    private ReportSkillRoll reportSkillRoll = null;
    private ReportBlockRoll reportBlockRoll = null;
    private boolean blockingPlayerWasInjured = false;
    private boolean ballScattered = false;
    private boolean successfulPass = false;
    private boolean sentOff = false;
    private boolean landingFailed = false;

    public ReportReRoll getReportReRoll() {
        return reportReRoll;
    }

    public void setReportReRoll(ReportReRoll reportReRoll) {
        this.reportReRoll = reportReRoll;
    }

    public ReportSkillRoll getReportSkillRoll() {
        return reportSkillRoll;
    }

    public void setReportSkillRoll(ReportSkillRoll reportSkillRoll) {
        this.reportSkillRoll = reportSkillRoll;
    }

    public ReportBlockRoll getReportBlockRoll() {
        return reportBlockRoll;
    }

    public void setReportBlockRoll(ReportBlockRoll reportBlockRoll) {
        this.reportBlockRoll = reportBlockRoll;
    }

    public boolean isBlockingPlayerWasInjured() {
        return blockingPlayerWasInjured;
    }

    public void setBlockingPlayerWasInjured(boolean blockingPlayerWasInjured) {
        this.blockingPlayerWasInjured = blockingPlayerWasInjured;
    }

    public boolean isBallScattered() {
        return ballScattered;
    }

    public void setBallScattered(boolean ballScattered) {
        this.ballScattered = ballScattered;
    }

    public boolean isSuccessfulPass() {
        return successfulPass;
    }

    public void setSuccessfulPass(boolean successfulPass) {
        this.successfulPass = successfulPass;
    }

    public boolean isSentOff() {
        return sentOff;
    }

    public void setSentOff(boolean sentOff) {
        this.sentOff = sentOff;
    }

    public boolean isLandingFailed() {
        return landingFailed;
    }

    public void setLandingFailed(boolean landingFailed) {
        this.landingFailed = landingFailed;
    }
}
