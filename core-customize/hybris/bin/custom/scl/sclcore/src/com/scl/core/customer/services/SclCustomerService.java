package com.scl.core.customer.services;

import com.scl.core.model.DealerModel;
import com.scl.core.model.ProspectiveDealerModel;
import com.scl.core.model.SclUserModel;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BCustomerService;

import java.util.List;

public interface SclCustomerService extends B2BCustomerService<B2BCustomerModel, B2BUnitModel> {

    /**
     * Get dealer from dealer code
     * @param dealerCode
     * @return
     */
    DealerModel getDealerForCode(final String dealerCode);

    List<ProspectiveDealerModel> getProspectiveDealersList(final SclUserModel sclUser);
}
