package com.scl.facades.customer;

import com.scl.facades.prosdealer.data.ProsDealerData;
import com.scl.facades.prosdealer.data.ProsDealerListData;
import de.hybris.platform.commercefacades.customer.CustomerFacade;

public interface SclCustomerFacade extends CustomerFacade {

    /**
     * calculates and saves customer's last six month avaerage order value
     */
    void calculateLastSixMonthsAverageOrderValue();

    ProsDealerListData getProspectiveDealersForCurrentuser();

    ProsDealerData getProsDealerDetailsByCode(final String prosDealerCode);

}
