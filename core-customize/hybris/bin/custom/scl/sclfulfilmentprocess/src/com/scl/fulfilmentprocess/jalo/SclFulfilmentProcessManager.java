/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.scl.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.scl.fulfilmentprocess.constants.SclFulfilmentProcessConstants;

public class SclFulfilmentProcessManager extends GeneratedSclFulfilmentProcessManager
{
	public static final SclFulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (SclFulfilmentProcessManager) em.getExtension(SclFulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}
