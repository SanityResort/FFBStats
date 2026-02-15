package org.butterbrot.ffb.stats.conversion;

import com.fumbbl.ffb.FactoryManager;
import com.fumbbl.ffb.FactoryType;
import com.fumbbl.ffb.factory.IFactorySource;
import com.fumbbl.ffb.factory.ILoggingFacade;
import com.fumbbl.ffb.factory.INamedObjectFactory;

import java.util.Map;

public class EvaluationFactorySource implements IFactorySource {

	private final FactoryManager factoryManager = new FactoryManager();
	@SuppressWarnings("rawtypes")
	private final Map<FactoryType.Factory, INamedObjectFactory> factories;

	public EvaluationFactorySource() {
		this.factories = factoryManager.getFactoriesForContext(FactoryType.FactoryContext.APPLICATION,
			(ILoggingFacade) null);
	}

	@Override
	public FactoryManager getFactoryManager() {
		return factoryManager;
	}

	@Override
	public FactoryType.FactoryContext getContext() {
		return FactoryType.FactoryContext.APPLICATION;
	}

	@Override
	public IFactorySource forContext(FactoryType.FactoryContext factoryContext) {
		return this;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public <T extends INamedObjectFactory> T getFactory(FactoryType.Factory factory) {
		return (T) factories.get(factory);
	}

	@Override
	public void logError(long l, String s) {

	}

	@Override
	public void logDebug(long l, String s) {

	}

	@Override
	public void logWithOutGameId(Throwable throwable) {

	}
}
