package org.butterbrot.ffb.stats.evaluation.stats.bb2025;

import com.fumbbl.ffb.SendToBoxReason;
import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.mixed.ReportInjury;
import com.fumbbl.ffb.util.ArrayTool;
import org.butterbrot.ffb.stats.adapter.mixed.ReportPoInjury;
import org.butterbrot.ffb.stats.evaluation.stats.Evaluator;
import org.butterbrot.ffb.stats.evaluation.stats.StatsState;
import org.butterbrot.ffb.stats.model.StatsCollection;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class InjuryEvaluator extends Evaluator<ReportInjury> {

    private final Set<SendToBoxReason> blockReasons = new HashSet<>();
    private final StatsCollection collection;
    private final StatsState<ReportPoInjury> state;

    // BB2025 emits two ReportInjury instances per armour-broken event (one pre-apothecary with
    // armour+injury parts, one post-apothecary with casualty part only). Without deduplication
    // both the caused-injury and the block knock-down would be counted twice.
    // Two separate sets are required because the two code paths have different guards:
    //   - countedInjuries gates the isArmorBroken() block (injuries / injury rolls)
    //   - countedBlockKnockDowns gates the block-knockdown call, which fires for ANY knockdown
    //     (armour broken or not) and would otherwise be swallowed for armour-broken events
    //     if it shared the same set as countedInjuries (key would already be present).
    private final Set<String> countedInjuries = new HashSet<>();
    private final Set<String> countedBlockKnockDowns = new HashSet<>();
    private String lastSeenActivePlayer = null;

    public InjuryEvaluator(StatsCollection collection, StatsState<ReportPoInjury> state) {
        this.collection = collection;
        this.state = state;

        blockReasons.add(SendToBoxReason.BLOCKED);
        blockReasons.add(SendToBoxReason.CHAINSAW);
        blockReasons.add(SendToBoxReason.BALL_AND_CHAIN);
        blockReasons.add(SendToBoxReason.STABBED);
        blockReasons.add(SendToBoxReason.PROJECTILE_VOMIT);
        blockReasons.add(SendToBoxReason.BREATHE_FIRE);
    }

    @Override
    public void evaluate(IReport report) {
        ReportInjury injury = (ReportInjury) report;

        if (!Objects.equals(state.getActivePlayer(), lastSeenActivePlayer)) {
            countedInjuries.clear();
            countedBlockKnockDowns.clear();
            lastSeenActivePlayer = state.getActivePlayer();
        }

        if (ArrayTool.isProvided(injury.getArmorRoll())) {
            if (injury.getSkip() == null || !injury.getSkip().isArmour()) {
                collection.addArmourRoll(injury.getArmorRoll(), injury.getDefenderId());
            }
        }
        if (injury.isArmorBroken()) {
            // Deduplicate: BB2025 emits two ReportInjury instances for the same injury event.
            // Only process the first one to avoid double-counting caused injuries.
            String injuryKey = state.getActivePlayer() + "|" + injury.getDefenderId()
                    + "|" + injury.getInjuryType();
            if (countedInjuries.add(injuryKey)) {
                state.getInjuries().addLast(new ReportPoInjury(injury, state.getPoReport()));
                state.setPoReport(null);
                // if the armour is broken report the injury roll, but only if both injury dice are not 0. this
                // should prevent errors when fanatic armour is broken, as this might be reported with weird data.
                if ((injury.getSkip() == null || !injury.getSkip().isInjury()) && ArrayTool.isProvided(injury.getInjuryRoll()) && injury.getInjuryRoll()[0] * injury.getInjuryRoll()[1] > 0) {
                    collection.addInjuryRoll(injury.getInjuryRoll(), injury.getDefenderId());
                }
            }
        }

        // Block knock-downs are counted for every knockdown (armour broken or not),
        // but only once per event. BB2025 emits two ReportInjury instances for armour-broken
        // events, so a dedicated set deduplicates the second one. For non-broken armour
        // there is only one report, so add() always returns true.
        if (state.getActivePlayer() != null && state.getCurrentBlockRoll() != null
                && blockReasons.contains(injury.getInjuryType().injuryType().sendToBoxReason())) {
            String knockDownKey = state.getActivePlayer() + "|" + injury.getDefenderId()
                    + "|" + injury.getInjuryType();
            if (countedBlockKnockDowns.add(knockDownKey)) {
                collection.addBlockKnockDown(state.getCurrentBlockRoll().getBlockRoll().length, injury.getDefenderId(),
                        state.getCurrentBlockRoll().getChoosingTeamId(), state.getActivePlayer());
            }
        }
    }
}
