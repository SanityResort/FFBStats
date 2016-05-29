/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.net;

import com.balancedbytes.games.ffb.IEnumWithId;
import com.balancedbytes.games.ffb.IEnumWithName;
import com.balancedbytes.games.ffb.net.commands.ClientCommandReplay;
import com.balancedbytes.games.ffb.net.commands.ServerCommandGameState;
import com.balancedbytes.games.ffb.net.commands.ServerCommandModelSync;
import com.balancedbytes.games.ffb.net.commands.ServerCommandReplay;

public enum NetCommandId implements IEnumWithId,
        IEnumWithName {
    INTERNAL_SERVER_SOCKET_CLOSED(1, "internalServerSocketClosed"),
    CLIENT_JOIN(2, "clientJoin"),
    CLIENT_TALK(3, "clientTalk"),
    SERVER_GAME_STATE(4, "serverGameState"),
    SERVER_TEAM_LIST(5, "serverTeamList"),
    SERVER_STATUS(6, "serverStatus"),
    SERVER_JOIN(7, "serverJoin"),
    SERVER_LEAVE(8, "serverLeave"),
    SERVER_TALK(9, "serverTalk"),
    CLIENT_SETUP_PLAYER(10, "clientSetupPlayer"),
    CLIENT_START_GAME(11, "clientStartGame"),
    CLIENT_ACTING_PLAYER(12, "clientActingPlayer"),
    CLIENT_MOVE(13, "clientMove"),
    CLIENT_USE_RE_ROLL(14, "clientUseReRoll"),
    SERVER_SOUND(15, "serverSound"),
    CLIENT_COIN_CHOICE(16, "clientCoinChoice"),
    CLIENT_RECEIVE_CHOICE(17, "clientReceiveChoice"),
    CLIENT_END_TURN(18, "clientEndTurn"),
    CLIENT_KICKOFF(19, "clientKickoff"),
    CLIENT_TOUCHBACK(20, "clientTouchback"),
    CLIENT_HAND_OVER(21, "clientHandOver"),
    CLIENT_PASS(22, "clientPass"),
    CLIENT_BLOCK(23, "clientBlock"),
    CLIENT_BLOCK_CHOICE(24, "clientBlockChoice"),
    CLIENT_PUSHBACK(25, "clientPushback"),
    CLIENT_FOLLOWUP_CHOICE(26, "clientFollowupChoice"),
    CLIENT_INTERCEPTOR_CHOICE(27, "clientInterceptorChoice"),
    CLIENT_USE_SKILL(28, "clientUseSkill"),
    SERVER_TEAM_SETUP_LIST(29, "serverTeamSetupList"),
    CLIENT_TEAM_SETUP_LOAD(30, "clientTeamSetupLoad"),
    CLIENT_TEAM_SETUP_SAVE(31, "clientTeamSetupSave"),
    CLIENT_TEAM_SETUP_DELETE(32, "clientTeamSetupDelete"),
    CLIENT_FOUL(33, "clientFoul"),
    CLIENT_USE_APOTHECARY(34, "clientUseApothecary"),
    CLIENT_APOTHECARY_CHOICE(35, "clientApothecaryChoice"),
    CLIENT_PASSWORD_CHALLENGE(36, "clientPasswordChallenge"),
    CLIENT_PING(37, "clientPing"),
    SERVER_PING(38, "serverPing"),
    SERVER_PASSWORD_CHALLENGE(39, "serverPasswordChallenge"),
    SERVER_MODEL_SYNC(40, "serverModelSync"),
    SERVER_VERSION(41, "serverVersion"),
    CLIENT_REQUEST_VERSION(42, "clientRequestVersion"),
    CLIENT_DEBUG_CLIENT_STATE(43, "clientDebugClientState"),
    SERVER_GAME_LIST(44, "serverGameList"),
    CLIENT_USER_SETTINGS(45, "clientUserSettings"),
    SERVER_USER_SETTINGS(46, "serverUserSettings"),
    CLIENT_REPLAY(47, "clientReplay"),
    SERVER_REPLAY(48, "serverReplay"),
    CLIENT_THROW_TEAM_MATE(49, "clientThrowTeamMate"),
    CLIENT_PLAYER_CHOICE(50, "clientPlayerChoice"),
    CLIENT_TIMEOUT_POSSIBLE(52, "clientTimeoutPossible"),
    CLIENT_ILLEGAL_PROCEDURE(53, "clientIllegalProcedure"),
    CLIENT_CONCEDE_GAME(54, "clientConcedeGame"),
    SERVER_ADMIN_MESSAGE(55, "serverAdminMessage"),
    CLIENT_USE_INDUCEMENT(56, "clientUseInducement"),
    CLIENT_BUY_INDUCEMENTS(57, "clientBuyInducements"),
    SERVER_ADD_PLAYER(58, "serverAddPlayer"),
    CLIENT_JOURNEYMEN(59, "clientJourneymen"),
    CLIENT_GAZE(60, "clientGaze"),
    CLIENT_CONFIRM(61, "clientConfirm"),
    CLIENT_SET_MARKER(62, "clientSetMarker"),
    INTERNAL_SERVER_FUMBBL_GAME_CREATED(63, "internalServerFumbblGameCreated"),
    INTERNAL_SERVER_FUMBBL_TEAM_LOADED(64, "internalServerFumbblTeamLoaded"),
    INTERNAL_SERVER_FUMBBL_GAME_CHECKED(65, "internalServerFumbblTeamLoaded"),
    INTERNAL_SERVER_JOIN_APPROVED(66, "internalServerJoinApproved"),
    INTERNAL_SERVER_REPLAY_LOADED(67, "internalServerReplayGameLoaded"),
    CLIENT_PETTY_CASH(68, "clientPettyCash"),
    SERVER_REMOVE_PLAYER(69, "serverRemovePlayer"),
    CLIENT_WIZARD_SPELL(70, "clientWizardSpell"),
    CLIENT_BUY_CARD(71, "clientBuyCard"),
    INTERNAL_SERVER_CLOSE_GAME(72, "internalServerCloseGame"),
    INTERNAL_SERVER_DELETE_GAME(73, "internalServerDeleteGame"),
    INTERNAL_SERVER_UPLOAD_GAME(74, "internalServerUploadGame"),
    INTERNAL_SERVER_SCHEDULE_GAME(75, "internalServerScheduleGame"),
    INTERNAL_SERVER_BACKUP_GAME(76, "internalServerBackupGame"),
    CLIENT_CLOSE_SESSION(77, "clientCloseSession");

    private int fId;
    private String fName;

    private NetCommandId(int pValue, String pName) {
        this.fId = pValue;
        this.fName = pName;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public NetCommand createNetCommand() {
        switch (this) {

            case SERVER_GAME_STATE: {
                return new ServerCommandGameState();
            }
            case SERVER_MODEL_SYNC: {
                return new ServerCommandModelSync();
            }
            case SERVER_REPLAY: {
                return new ServerCommandReplay();
            }
            case CLIENT_REPLAY: {
                return new ClientCommandReplay();
            }

        }
        throw new IllegalStateException("Unhandled netCommandId " + this + ".");
    }

}

