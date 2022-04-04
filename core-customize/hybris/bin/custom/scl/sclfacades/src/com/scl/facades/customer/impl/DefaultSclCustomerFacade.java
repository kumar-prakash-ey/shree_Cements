package com.scl.facades.customer.impl;

import com.scl.core.customer.services.SclCustomerAccountService;
import com.scl.facades.customer.SclCustomerFacade;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;


public class DefaultSclCustomerFacade extends DefaultCustomerFacade implements SclCustomerFacade {

    private SclCustomerAccountService sclCustomerAccountService;
    private UserService userService;
    private BaseStoreService baseStoreService;

    /**
     * calculates and saves customer's last six month avaerage order value
     */
    @Override
    public void calculateLastSixMonthsAverageOrderValue() {

        OrderStatus[] statuses = {OrderStatus.COMPLETED,OrderStatus.CREATED};
        BaseStoreModel baseStoreModel = getBaseStoreService().getCurrentBaseStore();
        CustomerModel customerModel = (CustomerModel) getUserService().getCurrentUser();
        getSclCustomerAccountService().updateCustomerAverageOrderValue(customerModel,baseStoreModel,statuses);
    }

    public SclCustomerAccountService getSclCustomerAccountService() {
        return sclCustomerAccountService;
    }

    public void setSclCustomerAccountService(SclCustomerAccountService sclCustomerAccountService) {
        this.sclCustomerAccountService = sclCustomerAccountService;
    }

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public BaseStoreService getBaseStoreService() {
        return baseStoreService;
    }

    @Override
    public void setBaseStoreService(BaseStoreService baseStoreService) {
        this.baseStoreService = baseStoreService;
    }


}
