package org.butterbrot.ffb.stats.evaluation.stats.bb2025;

import com.fumbbl.ffb.SendToBoxReason;
import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportBlockRoll;
import com.fumbbl.ffb.report.mixed.ReportInjury;
import com.fumbbl.ffb.util.ArrayTool;
import org.butterbrot.ffb.stats.adapter.mixed.ReportPoInjury;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.model.StatsCollection;

import java.util.HashSet;
import java.util.Set;

public class InjuryEvaluator extends Evaluator<ReportInjury> {

    private final Set<SendToBoxReason> nonBlockReasons = new HashSet<>();
    private final StatsCollection collection;
    private final StatsState<ReportPoInjury> state;

    // Tracks (attacker|defender|sendToBoxReason) combinations already counted via addBlockKnockDown
    // within the current player activation. BB2025 emits two ReportInjury instances per injury event
    // (one pre-apothecary with armour+injury parts, one post-apothecary with casualty part only),
    // so without deduplication each block knock-down would be counted twice.
    private final Set<String> countedBlockKnockDowns = new HashSet<>();
    private ReportBlockRoll lastSeenBlockRoll = null;

    public InjuryEvaluator(StatsCollection collection, StatsState<ReportPoInjury> state) {
        this.collection = collection;
        this.state = state;
        nonBlockReasons.add(SendToBoxReason.BITTEN);
        nonBlockReasons.add(SendToBoxReason.CROWD_PUSHED);
        nonBlockReasons.add(SendToBoxReason.FOULED);
        nonBlockReasons.add(SendToBoxReason.DODGE_FAIL);
        nonBlockReasons.add(SendToBoxReason.JUMP_FAIL);
        nonBlockReasons.add(SendToBoxReason.TRAP_DOOR_FALL);
    }

    @Override
    public void evaluate(IReport report) {
        ReportInjury injury = (ReportInjury) report;
        if (ArrayTool.isProvided(injury.getArmorRoll())) {
            if (injury.getSkip() == null || !injury.getSkip().isArmour()) {
                collection.addArmourRoll(injury.getArmorRoll(), injury.getDefenderId());
            }
        }
        if (injury.isArmorBroken()) {
            state.getInjuries().addLast(new ReportPoInjury(injury, state.getPoReport()));
            state.setPoReport(null);
            // if the armour is broken report the injury roll, but only if both injury dice are not 0. this
            // should prevent errors when fanatic armour is broken, as this might be reported with weird data.
            if ((injury.getSkip() == null || !injury.getSkip().isInjury()) && ArrayTool.isProvided(injury.getInjuryRoll()) && injury.getInjuryRoll()[0] * injury.getInjuryRoll()[1] > 0) {
                collection.addInjuryRoll(injury.getInjuryRoll(), injury.getDefenderId());
            }
        }

        // Reset the dedup-set whenever the active block roll changes (new activation or block cleared).
        if (state.getCurrentBlockRoll() != lastSeenBlockRoll) {
            countedBlockKnockDowns.clear();
            lastSeenBlockRoll = state.getCurrentBlockRoll();
        }

        if (state.getActivePlayer() != null && state.getCurrentBlockRoll() != null
                && !nonBlockReasons.contains(injury.getInjuryType().injuryType().sendToBoxReason())) {
            String knockDownKey = state.getActivePlayer() + "|" + injury.getDefenderId()
                    + "|" + injury.getInjuryType().injuryType().sendToBoxReason();
            if (countedBlockKnockDowns.add(knockDownKey)) {
                collection.addBlockKnockDown(state.getCurrentBlockRoll().getBlockRoll().length, injury.getDefenderId(),
                        state.getCurrentBlockRoll().getChoosingTeamId(), state.getActivePlayer());
            }
        }
    }
}
