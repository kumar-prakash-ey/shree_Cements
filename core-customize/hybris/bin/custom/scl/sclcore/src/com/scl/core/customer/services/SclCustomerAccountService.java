package com.scl.core.customer.services;

import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.store.BaseStoreModel;

public interface SclCustomerAccountService extends CustomerAccountService {

    /**
     * updtes customer average order value for last six month
     * @param customerModel
     * @param baseStoreModel
     * @param status
     */
    void updateCustomerAverageOrderValue(final CustomerModel customerModel, final BaseStoreModel baseStoreModel, final OrderStatus[] status);
}
