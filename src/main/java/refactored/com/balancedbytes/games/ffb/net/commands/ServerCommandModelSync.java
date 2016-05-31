package refactored.com.balancedbytes.games.ffb.net.commands;

import refactored.com.balancedbytes.games.ffb.json.IJsonOption;
import refactored.com.balancedbytes.games.ffb.json.UtilJson;
import refactored.com.balancedbytes.games.ffb.net.NetCommandId;
import refactored.com.balancedbytes.games.ffb.report.ReportList;
import repackaged.com.eclipsesource.json.JsonObject;
import repackaged.com.eclipsesource.json.JsonValue;

public class ServerCommandModelSync extends ServerCommand {
    private ReportList fReportList = new ReportList();

    public ServerCommandModelSync() {
    }

    private ServerCommandModelSync(ReportList pReportList) {
        this();
        this.fReportList.add(pReportList);
    }

    @Override
    public NetCommandId getId() {
        return NetCommandId.SERVER_MODEL_SYNC;
    }

    public ReportList getReportList() {
        return this.fReportList;
    }

    public ServerCommandModelSync transform() {
        ServerCommandModelSync transformedCommand = new ServerCommandModelSync(this.getReportList().transform());
        transformedCommand.setCommandNr(this.getCommandNr());
        return transformedCommand;
    }

    @Override
    public ServerCommandModelSync initFrom(JsonValue pJsonValue) {
        JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
        UtilNetCommand.validateCommandId(this, (NetCommandId)IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
        this.setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
        this.fReportList = new ReportList();
        JsonObject reportListObject = IJsonOption.REPORT_LIST.getFrom(jsonObject);
        if (reportListObject != null) {
            this.fReportList.initFrom(reportListObject);
        }
        return this;
    }
}

