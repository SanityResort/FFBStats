package org.butterbrot.ffb.stats.adapter;

import com.fumbbl.ffb.PlayerAction;
import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlayerActionMapping {

    private static Map<PlayerAction, Set<PlayerAction>> actionAliasMap = new HashMap<>();

    static {
        actionAliasMap.put(PlayerAction.MOVE, Sets.newHashSet(PlayerAction.MOVE, PlayerAction.STAND_UP));
        actionAliasMap.put(PlayerAction.BLOCK, Sets.newHashSet(PlayerAction.BLOCK));
        actionAliasMap.put(PlayerAction.BLITZ, Sets.newHashSet(PlayerAction.BLITZ, PlayerAction.BLITZ_MOVE, PlayerAction.STAND_UP_BLITZ));
        actionAliasMap.put(PlayerAction.THROW_TEAM_MATE, Sets.newHashSet(PlayerAction.THROW_TEAM_MATE, PlayerAction.THROW_TEAM_MATE_MOVE));
        actionAliasMap.put(PlayerAction.THROW_BOMB, Sets.newHashSet(PlayerAction.THROW_BOMB, PlayerAction.HAIL_MARY_BOMB));
        actionAliasMap.put(PlayerAction.PASS, Sets.newHashSet(PlayerAction.PASS, PlayerAction.PASS_MOVE, PlayerAction.HAIL_MARY_PASS));
        actionAliasMap.put(PlayerAction.HAND_OVER, Sets.newHashSet(PlayerAction.HAND_OVER, PlayerAction.HAND_OVER_MOVE));
        actionAliasMap.put(PlayerAction.FOUL, Sets.newHashSet(PlayerAction.FOUL, PlayerAction.FOUL_MOVE));
        actionAliasMap.put(PlayerAction.DUMP_OFF, Sets.newHashSet(PlayerAction.DUMP_OFF));
        actionAliasMap.put(PlayerAction.MULTIPLE_BLOCK, Sets.newHashSet(PlayerAction.MULTIPLE_BLOCK));
        actionAliasMap.put(PlayerAction.GAZE, Sets.newHashSet(PlayerAction.GAZE));
        actionAliasMap.put(PlayerAction.REMOVE_CONFUSION, Sets.newHashSet(PlayerAction.REMOVE_CONFUSION));
    }


    public static PlayerAction get(PlayerAction action) {
        for (Map.Entry<PlayerAction, Set<PlayerAction>> entry : actionAliasMap.entrySet()) {
            if (entry.getValue().contains(action)) {
                return entry.getKey();
            }
        }
        return action;
    }

}
