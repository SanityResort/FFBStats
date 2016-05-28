/*
 * Decompiled with CFR 0_114.
 */
package com.balancedbytes.games.ffb.client.ui;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.CardTarget;
import com.balancedbytes.games.ffb.CardType;
import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.ClientStateId;
import com.balancedbytes.games.ffb.ConcedeGameStatus;
import com.balancedbytes.games.ffb.FantasyFootballException;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.Inducement;
import com.balancedbytes.games.ffb.InducementType;
import com.balancedbytes.games.ffb.PlayerType;
import com.balancedbytes.games.ffb.SendToBoxReason;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.client.ActionKey;
import com.balancedbytes.games.ffb.client.ClientData;
import com.balancedbytes.games.ffb.client.ClientReplayer;
import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.client.IconCache;
import com.balancedbytes.games.ffb.client.PlayerIconFactory;
import com.balancedbytes.games.ffb.client.UserInterface;
import com.balancedbytes.games.ffb.client.dialog.DialogAbout;
import com.balancedbytes.games.ffb.client.dialog.DialogChatCommands;
import com.balancedbytes.games.ffb.client.dialog.DialogGameStatistics;
import com.balancedbytes.games.ffb.client.dialog.DialogKeyBindings;
import com.balancedbytes.games.ffb.client.dialog.DialogSoundVolume;
import com.balancedbytes.games.ffb.client.dialog.IDialog;
import com.balancedbytes.games.ffb.client.dialog.IDialogCloseListener;
import com.balancedbytes.games.ffb.client.net.ClientCommunication;
import com.balancedbytes.games.ffb.client.state.ClientState;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.model.FieldModel;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.GameOptions;
import com.balancedbytes.games.ffb.model.GameResult;
import com.balancedbytes.games.ffb.model.InducementSet;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.PlayerResult;
import com.balancedbytes.games.ffb.model.Team;
import com.balancedbytes.games.ffb.model.TurnData;
import com.balancedbytes.games.ffb.option.GameOptionId;
import com.balancedbytes.games.ffb.option.IGameOption;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class GameMenuBar
extends JMenuBar
implements ActionListener,
IDialogCloseListener {
    private static final String[] _SAVED_USER_SETTINGS = new String[]{"setting.sound.mode", "setting.sound.volume", "setting.icons", "setting.chatlog", "setting.automove", "setting.pitch.customization", "setting.pitch.markings", "setting.pitch.teamLogos", "setting.pitch.weather", "setting.rangegrid"};
    private static final String _REPLAY_MODE_ON = "Replay Mode";
    private static final String _REPLAY_MODE_OFF = "Spectator Mode";
    private FantasyFootballClient fClient;
    private JMenu fGameMenu;
    private JMenuItem fGameReplayMenuItem;
    private JMenuItem fGameConcessionMenuItem;
    private JMenuItem fGameStatisticsMenuItem;
    private JMenu fTeamSetupMenu;
    private JMenuItem fLoadSetupMenuItem;
    private JMenuItem fSaveSetupMenuItem;
    private JMenu fUserSettingsMenu;
    private JMenuItem fRestoreDefaultsMenuItem;
    private JMenu fSoundMenu;
    private JMenuItem fSoundVolumeItem;
    private JRadioButtonMenuItem fSoundOnMenuItem;
    private JRadioButtonMenuItem fSoundMuteSpectatorsMenuItem;
    private JRadioButtonMenuItem fSoundOffMenuItem;
    private JMenu fIconsMenu;
    private JRadioButtonMenuItem fIconsAbstract;
    private JRadioButtonMenuItem fIconsRosterOpponent;
    private JRadioButtonMenuItem fIconsRosterBoth;
    private JRadioButtonMenuItem fIconsTeam;
    private JMenu fAutomoveMenu;
    private JRadioButtonMenuItem fAutomoveOnMenuItem;
    private JRadioButtonMenuItem fAutomoveOffMenuItem;
    private JMenu fPitchMenu;
    private JMenu fPitchCustomizationMenu;
    private JRadioButtonMenuItem fCustomPitchMenuItem;
    private JRadioButtonMenuItem fDefaultPitchMenuItem;
    private JRadioButtonMenuItem fBasicPitchMenuItem;
    private JMenu fPitchMarkingsMenu;
    private JRadioButtonMenuItem fPitchMarkingsOnMenuItem;
    private JRadioButtonMenuItem fPitchMarkingsOffMenuItem;
    private JMenu fTeamLogoMenu;
    private JRadioButtonMenuItem fTeamLogoBothMenuItem;
    private JRadioButtonMenuItem fTeamLogoOwnMenuItem;
    private JRadioButtonMenuItem fTeamLogoNoneMenuItem;
    private JMenu fPitchWeatherMenu;
    private JRadioButtonMenuItem fPitchWeatherOnMenuItem;
    private JRadioButtonMenuItem fPitchWeatherOffMenuItem;
    private JMenu fRangeGridMenu;
    private JRadioButtonMenuItem fRangeGridAlwaysOnMenuItem;
    private JRadioButtonMenuItem fRangeGridToggleMenuItem;
    private JMenu fMissingPlayersMenu;
    private JMenu fInducementsMenu;
    private JMenu fInducementsHomeMenu;
    private JMenu fInducementsAwayMenu;
    private JMenu fActiveCardsMenu;
    private JMenu fActiveCardsHomeMenu;
    private JMenu fActiveCardsAwayMenu;
    private JMenu fGameOptionsMenu;
    private JMenu fHelpMenu;
    private JMenuItem fAboutMenuItem;
    private JMenuItem fChatCommandsMenuItem;
    private JMenuItem fKeyBindingsMenuItem;
    private IDialog fDialogShown;
    private int fCurrentInducementTotalHome;
    private int fCurrentUsedCardsHome;
    private int fCurrentInducementTotalAway;
    private int fCurrentUsedCardsAway;
    private Card[] fCurrentActiveCardsHome;
    private Card[] fCurrentActiveCardsAway;

    public GameMenuBar(FantasyFootballClient pClient) {
        this.setFont(new Font("Sans Serif", 0, 12));
        this.fClient = pClient;
        this.fGameMenu = new JMenu("Game");
        this.fGameMenu.setMnemonic(71);
        this.add(this.fGameMenu);
        this.fGameReplayMenuItem = new JMenuItem("Replay Mode", 82);
        String keyMenuReplay = this.getClient().getProperty("key.menu.replay");
        if (StringTool.isProvided(keyMenuReplay)) {
            this.fGameReplayMenuItem.setAccelerator(KeyStroke.getKeyStroke(keyMenuReplay));
        }
        this.fGameReplayMenuItem.addActionListener(this);
        this.fGameMenu.add(this.fGameReplayMenuItem);
        this.fGameConcessionMenuItem = new JMenuItem("Concede Game", 67);
        this.fGameConcessionMenuItem.addActionListener(this);
        this.fGameConcessionMenuItem.setEnabled(false);
        this.fGameMenu.add(this.fGameConcessionMenuItem);
        this.fGameStatisticsMenuItem = new JMenuItem("Game Statistics", 83);
        this.fGameStatisticsMenuItem.addActionListener(this);
        this.fGameStatisticsMenuItem.setEnabled(false);
        this.fGameMenu.add(this.fGameStatisticsMenuItem);
        this.fTeamSetupMenu = new JMenu("Team Setup");
        this.fTeamSetupMenu.setMnemonic(84);
        this.add(this.fTeamSetupMenu);
        this.fLoadSetupMenuItem = new JMenuItem("Load Setup", 76);
        String menuSetupLoad = this.getClient().getProperty("key.menu.setup.load");
        if (StringTool.isProvided(menuSetupLoad)) {
            this.fLoadSetupMenuItem.setAccelerator(KeyStroke.getKeyStroke(menuSetupLoad));
        }
        this.fLoadSetupMenuItem.addActionListener(this);
        this.fTeamSetupMenu.add(this.fLoadSetupMenuItem);
        this.fSaveSetupMenuItem = new JMenuItem("Save Setup", 83);
        String menuSetupSave = this.getClient().getProperty("key.menu.setup.save");
        if (StringTool.isProvided(menuSetupSave)) {
            this.fSaveSetupMenuItem.setAccelerator(KeyStroke.getKeyStroke(menuSetupSave));
        }
        this.fSaveSetupMenuItem.addActionListener(this);
        this.fTeamSetupMenu.add(this.fSaveSetupMenuItem);
        this.fUserSettingsMenu = new JMenu("User Settings");
        this.fUserSettingsMenu.setMnemonic(85);
        this.add(this.fUserSettingsMenu);
        this.fSoundMenu = new JMenu("Sound");
        this.fSoundMenu.setMnemonic(83);
        this.fUserSettingsMenu.add(this.fSoundMenu);
        this.fSoundVolumeItem = new JMenuItem("Sound Volume");
        this.fSoundVolumeItem.setMnemonic(86);
        this.fSoundVolumeItem.addActionListener(this);
        this.fSoundMenu.add(this.fSoundVolumeItem);
        this.fSoundMenu.addSeparator();
        ButtonGroup soundGroup = new ButtonGroup();
        this.fSoundOnMenuItem = new JRadioButtonMenuItem("Sound on");
        this.fSoundOnMenuItem.addActionListener(this);
        soundGroup.add(this.fSoundOnMenuItem);
        this.fSoundMenu.add(this.fSoundOnMenuItem);
        this.fSoundMuteSpectatorsMenuItem = new JRadioButtonMenuItem("Mute spectators");
        this.fSoundMuteSpectatorsMenuItem.addActionListener(this);
        soundGroup.add(this.fSoundMuteSpectatorsMenuItem);
        this.fSoundMenu.add(this.fSoundMuteSpectatorsMenuItem);
        this.fSoundOffMenuItem = new JRadioButtonMenuItem("Sound off");
        this.fSoundOffMenuItem.addActionListener(this);
        soundGroup.add(this.fSoundOffMenuItem);
        this.fSoundMenu.add(this.fSoundOffMenuItem);
        this.fIconsMenu = new JMenu("Icons");
        this.fIconsMenu.setMnemonic(73);
        this.fUserSettingsMenu.add(this.fIconsMenu);
        ButtonGroup iconsGroup = new ButtonGroup();
        this.fIconsTeam = new JRadioButtonMenuItem("Team icons");
        this.fIconsTeam.setMnemonic(84);
        this.fIconsTeam.addActionListener(this);
        iconsGroup.add(this.fIconsTeam);
        this.fIconsMenu.add(this.fIconsTeam);
        this.fIconsRosterOpponent = new JRadioButtonMenuItem("Roster icons (Opponent)");
        this.fIconsRosterOpponent.setMnemonic(79);
        this.fIconsRosterOpponent.addActionListener(this);
        iconsGroup.add(this.fIconsRosterOpponent);
        this.fIconsMenu.add(this.fIconsRosterOpponent);
        this.fIconsRosterBoth = new JRadioButtonMenuItem("Roster icons (Both)");
        this.fIconsRosterBoth.setMnemonic(66);
        this.fIconsRosterBoth.addActionListener(this);
        iconsGroup.add(this.fIconsRosterBoth);
        this.fIconsMenu.add(this.fIconsRosterBoth);
        this.fIconsAbstract = new JRadioButtonMenuItem("Abstract icons");
        this.fIconsAbstract.setMnemonic(65);
        this.fIconsAbstract.addActionListener(this);
        iconsGroup.add(this.fIconsAbstract);
        this.fIconsMenu.add(this.fIconsAbstract);
        this.fAutomoveMenu = new JMenu("Automove");
        this.fAutomoveMenu.setMnemonic(65);
        this.fUserSettingsMenu.add(this.fAutomoveMenu);
        ButtonGroup automoveGroup = new ButtonGroup();
        this.fAutomoveOnMenuItem = new JRadioButtonMenuItem("Automove on");
        this.fAutomoveOnMenuItem.addActionListener(this);
        automoveGroup.add(this.fAutomoveOnMenuItem);
        this.fAutomoveMenu.add(this.fAutomoveOnMenuItem);
        this.fAutomoveOffMenuItem = new JRadioButtonMenuItem("Automove off");
        this.fAutomoveOffMenuItem.addActionListener(this);
        automoveGroup.add(this.fAutomoveOffMenuItem);
        this.fAutomoveMenu.add(this.fAutomoveOffMenuItem);
        this.fPitchMenu = new JMenu("Pitch");
        this.fPitchMenu.setMnemonic(80);
        this.fUserSettingsMenu.add(this.fPitchMenu);
        this.fPitchCustomizationMenu = new JMenu("Pitch Customization");
        this.fPitchCustomizationMenu.setMnemonic(67);
        this.fPitchMenu.add(this.fPitchCustomizationMenu);
        ButtonGroup pitchCustomGroup = new ButtonGroup();
        this.fCustomPitchMenuItem = new JRadioButtonMenuItem("Use Custom Pitch");
        this.fCustomPitchMenuItem.addActionListener(this);
        pitchCustomGroup.add(this.fCustomPitchMenuItem);
        this.fPitchCustomizationMenu.add(this.fCustomPitchMenuItem);
        this.fDefaultPitchMenuItem = new JRadioButtonMenuItem("Use Default Pitch");
        this.fDefaultPitchMenuItem.addActionListener(this);
        pitchCustomGroup.add(this.fDefaultPitchMenuItem);
        this.fPitchCustomizationMenu.add(this.fDefaultPitchMenuItem);
        this.fBasicPitchMenuItem = new JRadioButtonMenuItem("Use Basic Pitch");
        this.fBasicPitchMenuItem.addActionListener(this);
        pitchCustomGroup.add(this.fBasicPitchMenuItem);
        this.fPitchCustomizationMenu.add(this.fBasicPitchMenuItem);
        this.fPitchMarkingsMenu = new JMenu("Pitch Markings");
        this.fPitchMarkingsMenu.setMnemonic(77);
        this.fPitchMenu.add(this.fPitchMarkingsMenu);
        ButtonGroup tdDistanceGroup = new ButtonGroup();
        this.fPitchMarkingsOnMenuItem = new JRadioButtonMenuItem("Pitch Markings on");
        this.fPitchMarkingsOnMenuItem.addActionListener(this);
        tdDistanceGroup.add(this.fPitchMarkingsOnMenuItem);
        this.fPitchMarkingsMenu.add(this.fPitchMarkingsOnMenuItem);
        this.fPitchMarkingsOffMenuItem = new JRadioButtonMenuItem("Pitch Markings off");
        this.fPitchMarkingsOffMenuItem.addActionListener(this);
        tdDistanceGroup.add(this.fPitchMarkingsOffMenuItem);
        this.fPitchMarkingsMenu.add(this.fPitchMarkingsOffMenuItem);
        this.fTeamLogoMenu = new JMenu("Team Logo");
        this.fTeamLogoMenu.setMnemonic(84);
        this.fPitchMenu.add(this.fTeamLogoMenu);
        ButtonGroup teamLogoGroup = new ButtonGroup();
        this.fTeamLogoBothMenuItem = new JRadioButtonMenuItem("Show both Team-Logos");
        this.fTeamLogoBothMenuItem.addActionListener(this);
        teamLogoGroup.add(this.fTeamLogoBothMenuItem);
        this.fTeamLogoMenu.add(this.fTeamLogoBothMenuItem);
        this.fTeamLogoOwnMenuItem = new JRadioButtonMenuItem("Show my Team-Logo only");
        this.fTeamLogoOwnMenuItem.addActionListener(this);
        teamLogoGroup.add(this.fTeamLogoOwnMenuItem);
        this.fTeamLogoMenu.add(this.fTeamLogoOwnMenuItem);
        this.fTeamLogoNoneMenuItem = new JRadioButtonMenuItem("Show no Team-Logos");
        this.fTeamLogoNoneMenuItem.addActionListener(this);
        teamLogoGroup.add(this.fTeamLogoNoneMenuItem);
        this.fTeamLogoMenu.add(this.fTeamLogoNoneMenuItem);
        this.fPitchWeatherMenu = new JMenu("Pitch Weather");
        this.fPitchWeatherMenu.setMnemonic(87);
        this.fPitchMenu.add(this.fPitchWeatherMenu);
        ButtonGroup pitchWeatherGroup = new ButtonGroup();
        this.fPitchWeatherOnMenuItem = new JRadioButtonMenuItem("Change pitch with weather");
        this.fPitchWeatherOnMenuItem.addActionListener(this);
        pitchWeatherGroup.add(this.fPitchWeatherOnMenuItem);
        this.fPitchWeatherMenu.add(this.fPitchWeatherOnMenuItem);
        this.fPitchWeatherOffMenuItem = new JRadioButtonMenuItem("Always show nice weather pitch");
        this.fPitchWeatherOffMenuItem.addActionListener(this);
        pitchWeatherGroup.add(this.fPitchWeatherOffMenuItem);
        this.fPitchWeatherMenu.add(this.fPitchWeatherOffMenuItem);
        this.fRangeGridMenu = new JMenu("Range Grid");
        this.fRangeGridMenu.setMnemonic(82);
        this.fUserSettingsMenu.add(this.fRangeGridMenu);
        ButtonGroup rangeGridGroup = new ButtonGroup();
        this.fRangeGridAlwaysOnMenuItem = new JRadioButtonMenuItem("Range Grid always on");
        this.fRangeGridAlwaysOnMenuItem.addActionListener(this);
        rangeGridGroup.add(this.fRangeGridAlwaysOnMenuItem);
        this.fRangeGridMenu.add(this.fRangeGridAlwaysOnMenuItem);
        this.fRangeGridToggleMenuItem = new JRadioButtonMenuItem("Range Grid toggle");
        this.fRangeGridToggleMenuItem.addActionListener(this);
        rangeGridGroup.add(this.fRangeGridToggleMenuItem);
        this.fRangeGridMenu.add(this.fRangeGridToggleMenuItem);
        this.fUserSettingsMenu.addSeparator();
        this.fRestoreDefaultsMenuItem = new JMenuItem("Restore Defaults");
        this.fRestoreDefaultsMenuItem.addActionListener(this);
        this.fRestoreDefaultsMenuItem.setEnabled(false);
        this.fUserSettingsMenu.add(this.fRestoreDefaultsMenuItem);
        this.fMissingPlayersMenu = new JMenu("Missing Players");
        this.fMissingPlayersMenu.setMnemonic(77);
        this.fMissingPlayersMenu.setEnabled(false);
        this.add(this.fMissingPlayersMenu);
        this.fInducementsMenu = new JMenu("Inducements");
        this.fInducementsMenu.setMnemonic(73);
        this.fInducementsMenu.setEnabled(false);
        this.add(this.fInducementsMenu);
        this.fActiveCardsMenu = new JMenu("Active Cards");
        this.fActiveCardsMenu.setMnemonic(67);
        this.fActiveCardsMenu.setEnabled(false);
        this.add(this.fActiveCardsMenu);
        this.fGameOptionsMenu = new JMenu("Game Options");
        this.fGameOptionsMenu.setMnemonic(79);
        this.fGameOptionsMenu.setEnabled(false);
        this.add(this.fGameOptionsMenu);
        this.fHelpMenu = new JMenu("Help");
        this.fHelpMenu.setMnemonic(72);
        this.add(this.fHelpMenu);
        this.fAboutMenuItem = new JMenuItem("About", 65);
        this.fAboutMenuItem.addActionListener(this);
        this.fHelpMenu.add(this.fAboutMenuItem);
        this.fChatCommandsMenuItem = new JMenuItem("Chat Commands", 67);
        this.fChatCommandsMenuItem.addActionListener(this);
        this.fHelpMenu.add(this.fChatCommandsMenuItem);
        this.fKeyBindingsMenuItem = new JMenuItem("Key Bindings", 75);
        this.fKeyBindingsMenuItem.addActionListener(this);
        this.fHelpMenu.add(this.fKeyBindingsMenuItem);
        this.refresh();
    }

    public void init() {
        this.fCurrentInducementTotalHome = -1;
        this.fCurrentUsedCardsHome = 0;
        this.fCurrentInducementTotalAway = -1;
        this.fCurrentUsedCardsAway = 0;
        this.fCurrentActiveCardsHome = null;
        this.fCurrentActiveCardsAway = null;
        this.refresh();
    }

    public void refresh() {
        Game game = this.getClient().getGame();
        String soundSetting = this.getClient().getProperty("setting.sound.mode");
        this.fSoundOnMenuItem.setSelected(true);
        this.fSoundMuteSpectatorsMenuItem.setSelected("muteSpectators".equals(soundSetting));
        this.fSoundOffMenuItem.setSelected("soundOff".equals(soundSetting));
        String iconsSetting = this.getClient().getProperty("setting.icons");
        this.fIconsTeam.setSelected(true);
        this.fIconsRosterOpponent.setSelected("iconsRosterOpponent".equals(iconsSetting));
        this.fIconsRosterBoth.setSelected("iconsRoster".equals(iconsSetting));
        this.fIconsAbstract.setSelected("iconsAbstract".equals(iconsSetting));
        String automoveSetting = this.getClient().getProperty("setting.automove");
        this.fAutomoveOnMenuItem.setSelected(true);
        this.fAutomoveOffMenuItem.setSelected("automoveOff".equals(automoveSetting));
        String pitchCustomizationSetting = this.getClient().getProperty("setting.pitch.customization");
        this.fCustomPitchMenuItem.setSelected(true);
        this.fDefaultPitchMenuItem.setSelected("pitchDefault".equals(pitchCustomizationSetting));
        this.fBasicPitchMenuItem.setSelected("pitchBasic".equals(pitchCustomizationSetting));
        String pitchMarkingsSetting = this.getClient().getProperty("setting.pitch.markings");
        this.fPitchMarkingsOffMenuItem.setSelected(true);
        this.fPitchMarkingsOnMenuItem.setSelected("pitchMarkingsOn".equals(pitchMarkingsSetting));
        String teamLogosSetting = this.getClient().getProperty("setting.pitch.teamLogos");
        this.fTeamLogoBothMenuItem.setSelected(true);
        this.fTeamLogoOwnMenuItem.setSelected("teamLogosOwn".equals(teamLogosSetting));
        this.fTeamLogoNoneMenuItem.setSelected("teamLogosNone".equals(teamLogosSetting));
        String pitchWeatherSetting = this.getClient().getProperty("setting.pitch.weather");
        this.fPitchWeatherOnMenuItem.setSelected(true);
        this.fPitchWeatherOffMenuItem.setSelected("pitchWeatherOff".equals(pitchWeatherSetting));
        String rangeGridSetting = this.getClient().getProperty("setting.rangegrid");
        this.fRangeGridToggleMenuItem.setSelected(true);
        this.fRangeGridAlwaysOnMenuItem.setSelected("rangegridAlwaysOn".equals(rangeGridSetting));
        boolean gameStarted = game != null && game.getStarted() != null;
        this.fGameStatisticsMenuItem.setEnabled(gameStarted);
        this.fGameConcessionMenuItem.setEnabled(gameStarted && game.isHomePlaying() && ClientMode.PLAYER == this.getClient().getMode() && game.isConcessionPossible());
        this.fGameReplayMenuItem.setEnabled(ClientMode.SPECTATOR == this.getClient().getMode());
        this.updateMissingPlayers();
        this.updateInducements();
        this.updateActiveCards();
        this.updateGameOptions();
    }

    public FantasyFootballClient getClient() {
        return this.fClient;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClientReplayer replayer = this.getClient().getReplayer();
        JMenuItem source = (JMenuItem)e.getSource();
        if (source == this.fLoadSetupMenuItem) {
            this.getClient().getClientState().actionKeyPressed(ActionKey.MENU_SETUP_LOAD);
        }
        if (source == this.fSaveSetupMenuItem) {
            this.getClient().getClientState().actionKeyPressed(ActionKey.MENU_SETUP_SAVE);
        }
        if (source == this.fSoundVolumeItem) {
            this.showDialog(new DialogSoundVolume(this.getClient()));
        }
        if (source == this.fSoundOffMenuItem) {
            this.getClient().setProperty("setting.sound.mode", "soundOff");
            this.saveUserSettings(false);
        }
        if (source == this.fSoundMuteSpectatorsMenuItem) {
            this.getClient().setProperty("setting.sound.mode", "muteSpectators");
            this.saveUserSettings(false);
        }
        if (source == this.fSoundOnMenuItem) {
            this.getClient().setProperty("setting.sound.mode", "soundOn");
            this.saveUserSettings(false);
        }
        if (source == this.fIconsTeam) {
            this.getClient().setProperty("setting.icons", "iconsTeam");
            this.saveUserSettings(true);
        }
        if (source == this.fIconsRosterOpponent) {
            this.getClient().setProperty("setting.icons", "iconsRosterOpponent");
            this.saveUserSettings(true);
        }
        if (source == this.fIconsRosterBoth) {
            this.getClient().setProperty("setting.icons", "iconsRoster");
            this.saveUserSettings(true);
        }
        if (source == this.fIconsAbstract) {
            this.getClient().setProperty("setting.icons", "iconsAbstract");
            this.saveUserSettings(true);
        }
        if (source == this.fAboutMenuItem) {
            this.showDialog(new DialogAbout(this.getClient()));
        }
        if (source == this.fChatCommandsMenuItem) {
            this.showDialog(new DialogChatCommands(this.getClient()));
        }
        if (source == this.fKeyBindingsMenuItem) {
            this.showDialog(new DialogKeyBindings(this.getClient()));
        }
        if (source == this.fGameStatisticsMenuItem) {
            this.showDialog(new DialogGameStatistics(this.getClient()));
        }
        if (source == this.fAutomoveOffMenuItem) {
            this.getClient().setProperty("setting.automove", "automoveOff");
            this.saveUserSettings(false);
        }
        if (source == this.fAutomoveOnMenuItem) {
            this.getClient().setProperty("setting.automove", "automoveOn");
            this.saveUserSettings(false);
        }
        if (source == this.fCustomPitchMenuItem) {
            this.getClient().setProperty("setting.pitch.customization", "pitchCustom");
            this.saveUserSettings(true);
        }
        if (source == this.fDefaultPitchMenuItem) {
            this.getClient().setProperty("setting.pitch.customization", "pitchDefault");
            this.saveUserSettings(true);
        }
        if (source == this.fBasicPitchMenuItem) {
            this.getClient().setProperty("setting.pitch.customization", "pitchBasic");
            this.saveUserSettings(true);
        }
        if (source == this.fPitchMarkingsOffMenuItem) {
            this.getClient().setProperty("setting.pitch.markings", "pitchMarkingsOff");
            this.saveUserSettings(true);
        }
        if (source == this.fPitchMarkingsOnMenuItem) {
            this.getClient().setProperty("setting.pitch.markings", "pitchMarkingsOn");
            this.saveUserSettings(true);
        }
        if (source == this.fTeamLogoBothMenuItem) {
            this.getClient().setProperty("setting.pitch.teamLogos", "teamLogosBoth");
            this.saveUserSettings(true);
        }
        if (source == this.fTeamLogoOwnMenuItem) {
            this.getClient().setProperty("setting.pitch.teamLogos", "teamLogosOwn");
            this.saveUserSettings(true);
        }
        if (source == this.fTeamLogoNoneMenuItem) {
            this.getClient().setProperty("setting.pitch.teamLogos", "teamLogosNone");
            this.saveUserSettings(true);
        }
        if (source == this.fCustomPitchMenuItem) {
            this.getClient().setProperty("setting.pitch.customization", "pitchCustom");
            this.saveUserSettings(true);
        }
        if (source == this.fPitchWeatherOnMenuItem) {
            this.getClient().setProperty("setting.pitch.weather", "pitchWeatherOn");
            this.saveUserSettings(true);
        }
        if (source == this.fPitchWeatherOffMenuItem) {
            this.getClient().setProperty("setting.pitch.weather", "pitchWeatherOff");
            this.saveUserSettings(true);
        }
        if (source == this.fRangeGridAlwaysOnMenuItem) {
            this.getClient().setProperty("setting.rangegrid", "rangegridAlwaysOn");
            this.saveUserSettings(false);
        }
        if (source == this.fRestoreDefaultsMenuItem) {
            try {
                this.getClient().loadProperties();
            }
            catch (IOException pIoE) {
                throw new FantasyFootballException(pIoE);
            }
            this.refresh();
            this.saveUserSettings(true);
        }
        if (source == this.fGameReplayMenuItem) {
            this.fGameReplayMenuItem.setText(replayer.isReplaying() ? "Replay Mode" : "Spectator Mode");
            this.getClient().getClientState().actionKeyPressed(ActionKey.MENU_REPLAY);
        }
        if (source == this.fGameConcessionMenuItem) {
            this.getClient().getCommunication().sendConcedeGame(ConcedeGameStatus.REQUESTED);
        }
    }

    public void changeState(ClientStateId pStateId) {
        Game game = this.getClient().getGame();
        switch (pStateId) {
            case SETUP: {
                boolean setupEnabled = game.getTurnMode() != TurnMode.QUICK_SNAP;
                this.fLoadSetupMenuItem.setEnabled(setupEnabled);
                this.fSaveSetupMenuItem.setEnabled(setupEnabled);
                this.fRestoreDefaultsMenuItem.setEnabled(true);
                break;
            }
            default: {
                this.fLoadSetupMenuItem.setEnabled(false);
                this.fSaveSetupMenuItem.setEnabled(false);
                this.fSoundOnMenuItem.setEnabled(true);
                this.fSoundMuteSpectatorsMenuItem.setEnabled(true);
                this.fSoundOffMenuItem.setEnabled(true);
                this.fRestoreDefaultsMenuItem.setEnabled(true);
            }
        }
    }

    private void saveUserSettings(boolean pUserinterfaceInit) {
        String[] settingValues = new String[_SAVED_USER_SETTINGS.length];
        for (int i = 0; i < _SAVED_USER_SETTINGS.length; ++i) {
            settingValues[i] = this.getClient().getProperty(_SAVED_USER_SETTINGS[i]);
        }
        this.getClient().getCommunication().sendUserSettings(_SAVED_USER_SETTINGS, settingValues);
        this.getClient().getClientState().refreshSettings();
        if (pUserinterfaceInit) {
            this.getClient().getUserInterface().init();
        }
    }

    @Override
    public void dialogClosed(IDialog pDialog) {
        pDialog.hideDialog();
        if (pDialog != null && pDialog.getId() == DialogId.SOUND_VOLUME) {
            DialogSoundVolume volumeDialog = (DialogSoundVolume)pDialog;
            this.getClient().setProperty("setting.sound.volume", Integer.toString(volumeDialog.getVolume()));
            this.saveUserSettings(true);
        }
        this.fDialogShown = null;
    }

    public void showDialog(IDialog pDialog) {
        if (this.fDialogShown != null) {
            this.fDialogShown.hideDialog();
        }
        this.fDialogShown = pDialog;
        this.fDialogShown.showDialog(this);
    }

    public void updateGameOptions() {
        this.fGameOptionsMenu.removeAll();
        IGameOption[] gameOptions = this.getClient().getGame().getOptions().getOptions();
        Arrays.sort(gameOptions, new Comparator<IGameOption>(){

            @Override
            public int compare(IGameOption pO1, IGameOption pO2) {
                return pO1.getId().getName().compareTo(pO2.getId().getName());
            }
        });
        int optionsAdded = 0;
        if (this.getClient().getGame().isTesting()) {
            JMenuItem optionItem = new JMenuItem("* Game is in TEST mode. No results will be uploaded. See help for available test commands.");
            this.fGameOptionsMenu.add(optionItem);
            ++optionsAdded;
        }
        for (IGameOption option : gameOptions) {
            if (!option.isChanged() || option.getId() == GameOptionId.TEST_MODE) continue;
            StringBuilder optionText = new StringBuilder();
            optionText.append("* ").append(option.getDisplayMessage());
            JMenuItem optionItem = new JMenuItem(optionText.toString());
            this.fGameOptionsMenu.add(optionItem);
            ++optionsAdded;
        }
        if (optionsAdded > 0) {
            StringBuilder menuText = new StringBuilder().append(optionsAdded);
            if (optionsAdded > 1) {
                menuText.append(" Game Options");
            } else {
                menuText.append(" Game Option");
            }
            this.fGameOptionsMenu.setText(menuText.toString());
            this.fGameOptionsMenu.setEnabled(true);
        } else {
            this.fGameOptionsMenu.setText("No Game Options");
            this.fGameOptionsMenu.setEnabled(false);
        }
    }

    public void updateInducements() {
        int usedCardsHome;
        int usedCardsAway;
        boolean refreshNecessary = false;
        Game game = this.getClient().getGame();
        InducementSet inducementSetHome = game.getTurnDataHome().getInducementSet();
        int totalInducementHome = inducementSetHome.totalInducements();
        if (this.fCurrentInducementTotalHome < 0 || this.fCurrentInducementTotalHome != totalInducementHome) {
            this.fCurrentInducementTotalHome = totalInducementHome;
            refreshNecessary = true;
        }
        if ((usedCardsHome = inducementSetHome.getDeactivatedCards().length + inducementSetHome.getActiveCards().length) != this.fCurrentUsedCardsHome) {
            this.fCurrentUsedCardsHome = usedCardsHome;
            refreshNecessary = true;
        }
        InducementSet inducementSetAway = game.getTurnDataAway().getInducementSet();
        int totalInducementAway = inducementSetAway.totalInducements();
        if (this.fCurrentInducementTotalAway < 0 || this.fCurrentInducementTotalAway != totalInducementAway) {
            this.fCurrentInducementTotalAway = totalInducementAway;
            refreshNecessary = true;
        }
        if ((usedCardsAway = inducementSetAway.getDeactivatedCards().length + inducementSetAway.getActiveCards().length) != this.fCurrentUsedCardsAway) {
            this.fCurrentUsedCardsAway = usedCardsAway;
            refreshNecessary = true;
        }
        if (refreshNecessary) {
            StringBuilder menuText;
            this.fInducementsHomeMenu = null;
            this.fInducementsAwayMenu = null;
            this.fInducementsMenu.removeAll();
            if (this.fCurrentInducementTotalHome > 0) {
                menuText = new StringBuilder().append(totalInducementHome).append(" Home Team");
                this.fInducementsHomeMenu = new JMenu(menuText.toString());
                this.fInducementsHomeMenu.setForeground(Color.RED);
                this.fInducementsHomeMenu.setMnemonic(72);
                this.fInducementsMenu.add(this.fInducementsHomeMenu);
                this.addInducements(this.fInducementsHomeMenu, inducementSetHome);
            }
            if (this.fCurrentInducementTotalAway > 0) {
                menuText = new StringBuilder().append(totalInducementAway).append(" Away Team");
                this.fInducementsAwayMenu = new JMenu(menuText.toString());
                this.fInducementsAwayMenu.setForeground(Color.BLUE);
                this.fInducementsAwayMenu.setMnemonic(65);
                this.fInducementsMenu.add(this.fInducementsAwayMenu);
                this.addInducements(this.fInducementsAwayMenu, inducementSetAway);
            }
            if (this.fCurrentInducementTotalHome + this.fCurrentInducementTotalAway > 0) {
                menuText = new StringBuilder().append(this.fCurrentInducementTotalHome + this.fCurrentInducementTotalAway);
                if (this.fCurrentInducementTotalHome + this.fCurrentInducementTotalAway > 1) {
                    menuText.append(" Inducements");
                } else {
                    menuText.append(" Inducement");
                }
                this.fInducementsMenu.setText(menuText.toString());
                this.fInducementsMenu.setEnabled(true);
            } else {
                this.fInducementsMenu.setText("No Inducements");
                this.fInducementsMenu.setEnabled(false);
            }
        }
    }

    public void updateActiveCards() {
        boolean refreshNecessary = false;
        Game game = this.getClient().getGame();
        Card[] cardsHome = game.getTurnDataHome().getInducementSet().getActiveCards();
        if (this.fCurrentActiveCardsHome == null || cardsHome.length != this.fCurrentActiveCardsHome.length) {
            this.fCurrentActiveCardsHome = cardsHome;
            refreshNecessary = true;
        }
        Card[] cardsAway = game.getTurnDataAway().getInducementSet().getActiveCards();
        if (this.fCurrentActiveCardsAway == null || cardsAway.length != this.fCurrentActiveCardsAway.length) {
            this.fCurrentActiveCardsAway = cardsAway;
            refreshNecessary = true;
        }
        if (refreshNecessary) {
            int currentActiveCardsAwayLength;
            StringBuilder menuText;
            this.fActiveCardsMenu.removeAll();
            if (ArrayTool.isProvided(this.fCurrentActiveCardsHome)) {
                menuText = new StringBuilder().append(this.fCurrentActiveCardsHome.length).append(" Home Team");
                this.fActiveCardsHomeMenu = new JMenu(menuText.toString());
                this.fActiveCardsHomeMenu.setForeground(Color.RED);
                this.fActiveCardsHomeMenu.setMnemonic(72);
                this.fActiveCardsMenu.add(this.fActiveCardsHomeMenu);
                this.addActiveCards(this.fActiveCardsHomeMenu, this.fCurrentActiveCardsHome);
            }
            if (ArrayTool.isProvided(this.fCurrentActiveCardsAway)) {
                menuText = new StringBuilder().append(this.fCurrentActiveCardsAway.length).append(" Away Team");
                this.fActiveCardsAwayMenu = new JMenu(menuText.toString());
                this.fActiveCardsAwayMenu.setForeground(Color.BLUE);
                this.fActiveCardsAwayMenu.setMnemonic(65);
                this.fActiveCardsMenu.add(this.fActiveCardsAwayMenu);
                this.addActiveCards(this.fActiveCardsAwayMenu, this.fCurrentActiveCardsAway);
            }
            int currentActiveCardsHomeLength = ArrayTool.isProvided(this.fCurrentActiveCardsHome) ? this.fCurrentActiveCardsHome.length : 0;
            int n = currentActiveCardsAwayLength = ArrayTool.isProvided(this.fCurrentActiveCardsAway) ? this.fCurrentActiveCardsAway.length : 0;
            if (currentActiveCardsHomeLength + currentActiveCardsAwayLength > 0) {
                StringBuilder menuText2 = new StringBuilder().append(currentActiveCardsHomeLength + currentActiveCardsAwayLength);
                if (currentActiveCardsHomeLength + currentActiveCardsAwayLength > 1) {
                    menuText2.append(" Active Cards");
                } else {
                    menuText2.append(" Active Card");
                }
                this.fActiveCardsMenu.setText(menuText2.toString());
                this.fActiveCardsMenu.setEnabled(true);
            } else {
                this.fActiveCardsMenu.setText("No Active Cards");
                this.fActiveCardsMenu.setEnabled(false);
            }
        }
    }

    private void addActiveCards(JMenu pCardsMenu, Card[] pCards) {
        Game game = this.getClient().getGame();
        Arrays.sort(pCards, Card.createComparator());
        ImageIcon cardIcon = new ImageIcon(this.getClient().getUserInterface().getIconCache().getIconByProperty("sidebar.overlay.player.card"));
        for (Card card : pCards) {
            Player player = null;
            if (card.getTarget().isPlayedOnPlayer()) {
                player = game.getFieldModel().findPlayer(card);
            }
            StringBuilder cardText = new StringBuilder();
            cardText.append("<html>");
            cardText.append("<b>").append(card.getName()).append("</b>");
            if (player != null) {
                cardText.append("<br>").append("Played on ").append(player.getName());
            }
            cardText.append("<br>").append(card.getHtmlDescription());
            cardText.append("</html>");
            if (player != null) {
                this.addPlayerMenuItem(pCardsMenu, player, cardText.toString());
                continue;
            }
            JMenuItem cardMenuItem = new JMenuItem(cardText.toString(), cardIcon);
            pCardsMenu.add(cardMenuItem);
        }
    }

    private void addInducements(JMenu pInducementMenu, InducementSet pInducementSet) {
        Inducement[] inducements = pInducementSet.getInducements();
        Arrays.sort(inducements, new Comparator<Inducement>(){

            @Override
            public int compare(Inducement pInducement1, Inducement pInducement2) {
                return pInducement1.getType().getId() - pInducement2.getType().getId();
            }
        });
        for (Inducement inducement : inducements) {
            if (inducement.getType() == InducementType.STAR_PLAYERS || inducement.getType() == InducementType.MERCENARIES || inducement.getValue() <= 0) continue;
            StringBuilder inducementText = new StringBuilder();
            inducementText.append(inducement.getValue()).append(" ");
            if (inducement.getValue() > 1) {
                inducementText.append(inducement.getType().getPlural());
            } else {
                inducementText.append(inducement.getType().getSingular());
            }
            JMenuItem inducementItem = new JMenuItem(inducementText.toString());
            pInducementMenu.add(inducementItem);
        }
        Game game = this.getClient().getGame();
        Team team = pInducementSet.getTurnData().isHomeData() ? game.getTeamHome() : game.getTeamAway();
        ArrayList<Player> starPlayers = new ArrayList<Player>();
        for (Player player2 : team.getPlayers()) {
            if (player2.getPlayerType() != PlayerType.STAR) continue;
            starPlayers.add(player2);
        }
        if (starPlayers.size() > 0) {
            StringBuilder starPlayerMenuText = new StringBuilder();
            starPlayerMenuText.append(starPlayers.size());
            if (starPlayers.size() == 1) {
                starPlayerMenuText.append(" Star Player");
            } else {
                starPlayerMenuText.append(" Star Players");
            }
            JMenu starPlayerMenu = new JMenu(starPlayerMenuText.toString());
            pInducementMenu.add(starPlayerMenu);
            for (Player player2 : starPlayers) {
                this.addPlayerMenuItem(starPlayerMenu, player2, player2.getName());
            }
        }
        ArrayList<Player> mercenaries = new ArrayList<Player>();
        for (Player player32 : team.getPlayers()) {
            if (player32.getPlayerType() != PlayerType.MERCENARY) continue;
            mercenaries.add(player32);
        }
        if (mercenaries.size() > 0) {
            StringBuilder mercenaryMenuText = new StringBuilder();
            mercenaryMenuText.append(mercenaries.size());
            if (mercenaries.size() == 1) {
                mercenaryMenuText.append(" Mercenary");
            } else {
                mercenaryMenuText.append(" Mercenaries");
            }
            JMenu mercenaryMenu = new JMenu(mercenaryMenuText.toString());
            pInducementMenu.add(mercenaryMenu);
            for (Player player32 : mercenaries) {
                this.addPlayerMenuItem(mercenaryMenu, player32, player32.getName());
            }
        }
        UserInterface userInterface = this.getClient().getUserInterface();
        Map<CardType, List<Card>> cardMap = this.buildCardMap(pInducementSet);
        for (CardType type : cardMap.keySet()) {
            List<Card> cardList = cardMap.get(type);
            StringBuilder cardTypeText = new StringBuilder();
            cardTypeText.append(cardList.size()).append(" ");
            if (cardList.size() > 1) {
                cardTypeText.append(type.getInducementNameMultiple());
            } else {
                cardTypeText.append(type.getInducementNameSingle());
            }
            int available = 0;
            for (Card card : cardList) {
                if (!pInducementSet.isAvailable(card)) continue;
                ++available;
            }
            cardTypeText.append(" (");
            cardTypeText.append(available > 0 ? Integer.valueOf(available) : "None");
            cardTypeText.append(" available)");
            if (pInducementSet.getTurnData().isHomeData() && this.getClient().getMode() == ClientMode.PLAYER) {
                JMenu cardMenu = new JMenu(cardTypeText.toString());
                pInducementMenu.add(cardMenu);
                ImageIcon cardIcon = new ImageIcon(userInterface.getIconCache().getIconByProperty("sidebar.overlay.player.card"));
                for (Card card2 : cardList) {
                    if (!pInducementSet.isAvailable(card2)) continue;
                    StringBuilder cardText = new StringBuilder();
                    cardText.append("<html>");
                    cardText.append("<b>").append(card2.getName()).append("</b>");
                    cardText.append("<br>").append(card2.getHtmlDescriptionWithPhases());
                    cardText.append("</html>");
                    JMenuItem cardItem = new JMenuItem(cardText.toString(), cardIcon);
                    cardMenu.add(cardItem);
                }
                continue;
            }
            JMenuItem cardItem = new JMenuItem(cardTypeText.toString());
            pInducementMenu.add(cardItem);
        }
    }

    public void updateMissingPlayers() {
        Player player;
        int i;
        Game game = this.getClient().getGame();
        this.fMissingPlayersMenu.removeAll();
        int nrOfEntries = 0;
        for (i = 0; i < 30 && (player = game.getFieldModel().getPlayer(new FieldCoordinate(-7, i))) != null; ++i) {
            this.addMissingPlayerMenuItem(player);
            ++nrOfEntries;
        }
        for (i = 0; i < 30 && (player = game.getFieldModel().getPlayer(new FieldCoordinate(36, i))) != null; ++i) {
            this.addMissingPlayerMenuItem(player);
            ++nrOfEntries;
        }
        StringBuilder menuText = new StringBuilder();
        if (nrOfEntries > 0) {
            menuText.append(nrOfEntries);
            if (nrOfEntries > 1) {
                menuText.append(" Missing Players");
            } else {
                menuText.append(" Missing Player");
            }
            this.fMissingPlayersMenu.setEnabled(true);
        } else {
            menuText.append("No Missing Players");
            this.fMissingPlayersMenu.setEnabled(false);
        }
        this.fMissingPlayersMenu.setText(menuText.toString());
    }

    private void addMissingPlayerMenuItem(Player pPlayer) {
        if (pPlayer == null) {
            return;
        }
        StringBuilder playerText = new StringBuilder();
        playerText.append("<html>").append(pPlayer.getName());
        if (pPlayer.getRecoveringInjury() != null) {
            playerText.append("<br>").append(pPlayer.getRecoveringInjury().getRecovery());
        } else {
            Game game = this.getClient().getGame();
            PlayerResult playerResult = game.getGameResult().getPlayerResult(pPlayer);
            if (playerResult.getSendToBoxReason() != null) {
                playerText.append("<br>").append(playerResult.getSendToBoxReason().getReason());
            }
        }
        playerText.append("</html>");
        this.addPlayerMenuItem(this.fMissingPlayersMenu, pPlayer, playerText.toString());
    }

    private void addPlayerMenuItem(JMenu pPlayersMenu, Player pPlayer, String pText) {
        if (pPlayer == null || !StringTool.isProvided(pText)) {
            return;
        }
        UserInterface userInterface = this.getClient().getUserInterface();
        PlayerIconFactory playerIconFactory = userInterface.getPlayerIconFactory();
        ImageIcon playerIcon = new ImageIcon(playerIconFactory.getIcon(this.getClient(), pPlayer));
        JMenuItem playersMenuItem = new JMenuItem(pText, playerIcon);
        playersMenuItem.addMouseListener(new MenuPlayerMouseListener(pPlayer));
        pPlayersMenu.add(playersMenuItem);
    }

    private Map<CardType, List<Card>> buildCardMap(InducementSet pInducementSet) {
        Card[] allCards = pInducementSet.getAllCards();
        HashMap<CardType, List<Card>> cardMap = new HashMap<CardType, List<Card>>();
        for (CardType type : CardType.values()) {
            ArrayList<Card> cardList = new ArrayList<Card>();
            for (Card card : allCards) {
                if (type != card.getType()) continue;
                cardList.add(card);
            }
            if (cardList.size() <= 0) continue;
            cardMap.put(type, cardList);
        }
        return cardMap;
    }

    private class MenuPlayerMouseListener
    extends MouseAdapter {
        private Player fPlayer;

        public MenuPlayerMouseListener(Player pPlayer) {
            this.fPlayer = pPlayer;
        }

        @Override
        public void mouseEntered(MouseEvent pMouseEvent) {
            ClientData clientData = GameMenuBar.this.getClient().getClientData();
            if (clientData.getSelectedPlayer() != this.fPlayer && clientData.getDragStartPosition() == null) {
                clientData.setSelectedPlayer(this.fPlayer);
                GameMenuBar.this.getClient().getUserInterface().refreshSideBars();
            }
        }
    }

}

