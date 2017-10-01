/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.client.state;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;

import java.util.HashMap;
import java.util.Map;

public class ClientStateFactory {
    private FantasyFootballClient fClient;
    private Map<ClientStateId, ClientState> fClientStateById;

    public ClientStateFactory(FantasyFootballClient pClient) {
        this.fClient = pClient;
        this.fClientStateById = new HashMap<ClientStateId, ClientState>();
        this.register(new ClientStateLogin(pClient));
        this.register(new ClientStateStartGame(pClient));
        this.register(new ClientStateSpectate(pClient));
        this.register(new ClientStateSelect(pClient));
        this.register(new ClientStatePass(pClient));
        this.register(new ClientStateHandOver(pClient));
        this.register(new ClientStateMove(pClient));
        this.register(new ClientStateKickoff(pClient));
        this.register(new ClientStateBlock(pClient));
        this.register(new ClientStatePushback(pClient));
        this.register(new ClientStateInterception(pClient));
        this.register(new ClientStateBlitz(pClient));
        this.register(new ClientStateFoul(pClient));
        this.register(new ClientStateSetup(pClient));
        this.register(new ClientStateQuickSnap(pClient));
        this.register(new ClientStateHighKick(pClient));
        this.register(new ClientStateTouchback(pClient));
        this.register(new ClientStateWaitForOpponent(pClient));
        this.register(new ClientStateReplay(pClient));
        this.register(new ClientStateThrowTeamMate(pClient));
        this.register(new ClientStateDumpOff(pClient));
        this.register(new ClientStateWaitForSetup(pClient));
        this.register(new ClientStateGaze(pClient));
        this.register(new ClientStateKickoffReturn(pClient));
        this.register(new ClientStateWizard(pClient));
        this.register(new ClientStatePassBlock(pClient));
        this.register(new ClientStateBomb(pClient));
        this.register(new ClientStateIllegalSubstitution(pClient));
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    public ClientState getStateForId(ClientStateId pClientStateId) {
        return this.fClientStateById.get(pClientStateId);
    }

    private void register(ClientState pClientState) {
        this.fClientStateById.put(pClientState.getId(), pClientState);
    }

    public ClientState getStateForGame() {
        ClientStateId clientStateId = null;
        Game game = this.getClient().getGame();
        ActingPlayer actingPlayer = game.getActingPlayer();
        if (ClientMode.REPLAY == this.getClient().getMode() || this.getClient().getReplayer().isReplaying()) {
            clientStateId = ClientStateId.REPLAY;
        } else if (!StringTool.isProvided(game.getTeamHome().getName())) {
            clientStateId = ClientStateId.LOGIN;
        } else if (ClientMode.SPECTATOR == this.getClient().getMode()) {
            clientStateId = ClientStateId.SPECTATE;
        } else if (game.getFinished() != null) {
            clientStateId = ClientStateId.SPECTATE;
        } else if (game.isHomePlaying() && game.isWaitingForOpponent()) {
            clientStateId = ClientStateId.WAIT_FOR_OPPONENT;
        } else {
            block0 : switch (game.getTurnMode()) {
                case BLITZ: 
                case REGULAR: {
                    if (game.isHomePlaying()) {
                        if (actingPlayer.getPlayer() == null) {
                            clientStateId = ClientStateId.SELECT_PLAYER;
                            break;
                        }
                        if (ArrayTool.isProvided(game.getFieldModel().getPushbackSquares())) {
                            clientStateId = ClientStateId.PUSHBACK;
                            break;
                        }
                        switch (actingPlayer.getPlayerAction()) {
                            case MOVE: 
                            case STAND_UP: 
                            case STAND_UP_BLITZ: {
                                clientStateId = ClientStateId.MOVE;
                                break block0;
                            }
                            case BLITZ_MOVE: {
                                clientStateId = ClientStateId.BLITZ;
                                break block0;
                            }
                            case BLITZ: 
                            case BLOCK: 
                            case MULTIPLE_BLOCK: {
                                clientStateId = ClientStateId.BLOCK;
                                break block0;
                            }
                            case FOUL: 
                            case FOUL_MOVE: {
                                clientStateId = ClientStateId.FOUL;
                                break block0;
                            }
                            case HAND_OVER: 
                            case HAND_OVER_MOVE: {
                                clientStateId = ClientStateId.HAND_OVER;
                                break block0;
                            }
                            case PASS: 
                            case PASS_MOVE: 
                            case HAIL_MARY_PASS: {
                                clientStateId = ClientStateId.PASS;
                                break block0;
                            }
                            case THROW_TEAM_MATE: 
                            case THROW_TEAM_MATE_MOVE: {
                                clientStateId = ClientStateId.THROW_TEAM_MATE;
                                break block0;
                            }
                            case GAZE: {
                                clientStateId = ClientStateId.GAZE;
                                break block0;
                            }
                            case THROW_BOMB: 
                            case HAIL_MARY_BOMB: {
                                clientStateId = ClientStateId.BOMB;
                                break block0;
                            }
                        }
                        break;
                    }
                    clientStateId = this.findPassiveState();
                    break;
                }
                case KICKOFF: {
                    if (game.isHomePlaying()) {
                        clientStateId = ClientStateId.KICKOFF;
                        break;
                    }
                    clientStateId = ClientStateId.WAIT_FOR_OPPONENT;
                    break;
                }
                case KICKOFF_RETURN: {
                    if (game.isHomePlaying()) {
                        clientStateId = ClientStateId.KICKOFF_RETURN;
                        break;
                    }
                    clientStateId = ClientStateId.WAIT_FOR_OPPONENT;
                    break;
                }
                case PASS_BLOCK: {
                    if (game.isHomePlaying()) {
                        clientStateId = ClientStateId.PASS_BLOCK;
                        break;
                    }
                    clientStateId = ClientStateId.WAIT_FOR_OPPONENT;
                    break;
                }
                case START_GAME: {
                    clientStateId = ClientStateId.START_GAME;
                    break;
                }
                case SETUP: 
                case PERFECT_DEFENCE: {
                    if (game.isHomePlaying()) {
                        clientStateId = ClientStateId.SETUP;
                        break;
                    }
                    clientStateId = ClientStateId.WAIT_FOR_SETUP;
                    break;
                }
                case HIGH_KICK: {
                    if (game.isHomePlaying()) {
                        clientStateId = ClientStateId.HIGH_KICK;
                        break;
                    }
                    clientStateId = ClientStateId.WAIT_FOR_OPPONENT;
                    break;
                }
                case QUICK_SNAP: {
                    if (game.isHomePlaying()) {
                        clientStateId = ClientStateId.QUICK_SNAP;
                        break;
                    }
                    clientStateId = ClientStateId.WAIT_FOR_OPPONENT;
                    break;
                }
                case ILLEGAL_SUBSTITUTION: {
                    if (game.isHomePlaying()) {
                        clientStateId = ClientStateId.ILLEGAL_SUBSTITUTION;
                        break;
                    }
                    clientStateId = ClientStateId.WAIT_FOR_OPPONENT;
                    break;
                }
                case TOUCHBACK: {
                    if (!game.isHomePlaying()) {
                        clientStateId = ClientStateId.TOUCHBACK;
                        break;
                    }
                    clientStateId = ClientStateId.WAIT_FOR_OPPONENT;
                    break;
                }
                case INTERCEPTION: {
                    if (!game.isHomePlaying() && game.getThrowerAction() != PlayerAction.DUMP_OFF || game.isHomePlaying() && game.getThrowerAction() == PlayerAction.DUMP_OFF) {
                        clientStateId = ClientStateId.INTERCEPTION;
                        break;
                    }
                    clientStateId = ClientStateId.WAIT_FOR_OPPONENT;
                    break;
                }
                case DUMP_OFF: {
                    if (!game.isHomePlaying()) {
                        clientStateId = ClientStateId.DUMP_OFF;
                        break;
                    }
                    clientStateId = ClientStateId.WAIT_FOR_OPPONENT;
                    break;
                }
                case WIZARD: {
                    if (game.isHomePlaying()) {
                        clientStateId = ClientStateId.WIZARD;
                        break;
                    }
                    clientStateId = ClientStateId.WAIT_FOR_OPPONENT;
                    break;
                }
                case BOMB_HOME: 
                case BOMB_HOME_BLITZ: 
                case BOMB_AWAY: 
                case BOMB_AWAY_BLITZ: {
                    if (game.isHomePlaying()) {
                        clientStateId = ClientStateId.BOMB;
                        break;
                    }
                    clientStateId = ClientStateId.WAIT_FOR_OPPONENT;
                    break;
                }
            }
        }
        return this.getStateForId(clientStateId);
    }

    private ClientStateId findPassiveState() {
        ClientStateId clientStateId = null;
        Game game = this.getClient().getGame();
        clientStateId = ArrayTool.isProvided(game.getFieldModel().getPushbackSquares()) && game.isWaitingForOpponent() ? ClientStateId.PUSHBACK : ClientStateId.WAIT_FOR_OPPONENT;
        return clientStateId;
    }

}

