package refactored.com.balancedbytes.games.ffb;

public class SkillFactory implements IEnumWithIdFactory,IEnumWithNameFactory {
    @Override
    public Skill forName(String pName) {
        for (Skill skill : Skill.values()) {
            if (!skill.getName().equalsIgnoreCase(pName)) continue;
            return skill;
        }
        if ("Ball & Chain".equalsIgnoreCase(pName) || "Ball &amp; Chain".equalsIgnoreCase(pName)) {
            return Skill.BALL_AND_CHAIN;
        }
        return null;
    }

    @Override
    public Skill forId(int pId) {
        if (pId > 0) {
            for (Skill skill : Skill.values()) {
                if (pId != skill.getId()) continue;
                return skill;
            }
        }
        return null;
    }
}

