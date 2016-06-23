package refactored.com.balancedbytes.games.ffb;

public enum ReRollSource implements IEnumWithId, IEnumWithName
{
    TEAM_RE_ROLL(1, "Team ReRoll"),
    DODGE(2, Skill.DODGE),
    PRO(3, Skill.PRO),
    SURE_FEET(4, Skill.SURE_FEET),
    SURE_HANDS(5, Skill.SURE_HANDS),
    CATCH(6, Skill.CATCH),
    PASS(7, Skill.PASS),
    WINNINGS(8, "Winnings"),
    LONER(9, Skill.LONER),
    LEADER(10, Skill.LEADER);
    
    private int fId;
    private String fName;
    private Skill fSkill;

    private ReRollSource(int pValue, String pName) {
        this.fId = pValue;
        this.fName = pName;
    }

    private ReRollSource(int pValue, Skill pSkill) {
        this(pValue, pSkill.getName());
        this.fSkill = pSkill;
    }

    @Override
    public int getId() {
        return this.fId;
    }

    @Override
    public String getName() {
        return this.fName;
    }

    public Skill getSkill() {
        return this.fSkill;
    }
}

