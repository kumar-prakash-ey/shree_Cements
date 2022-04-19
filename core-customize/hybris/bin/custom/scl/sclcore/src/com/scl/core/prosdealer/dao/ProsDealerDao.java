package com.scl.core.prosdealer.dao;

import com.scl.core.model.ProspectiveDealerModel;
import de.hybris.platform.servicelayer.internal.dao.Dao;

public interface ProsDealerDao extends Dao {

    ProspectiveDealerModel findProsDealerByCode(final String dealerCode);
}
