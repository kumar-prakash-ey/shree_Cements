package com.scl.facades.customer;

import de.hybris.platform.commercefacades.customer.CustomerFacade;

public interface SclCustomerFacade extends CustomerFacade {

    /**
     * calculates and saves customer's last six month avaerage order value
     */
    void calculateLastSixMonthsAverageOrderValue();
}
