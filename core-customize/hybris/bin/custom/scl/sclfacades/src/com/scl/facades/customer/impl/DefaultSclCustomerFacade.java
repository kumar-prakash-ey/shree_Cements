package com.scl.facades.customer.impl;

import com.scl.core.customer.services.SclCustomerAccountService;
import com.scl.core.customer.services.SclDealerDocumentsService;
import com.scl.facades.customer.SclCustomerFacade;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import org.springframework.web.multipart.MultipartFile;


public class DefaultSclCustomerFacade extends DefaultCustomerFacade implements SclCustomerFacade {

    private SclCustomerAccountService sclCustomerAccountService;
    private UserService userService;
    private BaseStoreService baseStoreService;

    private SclDealerDocumentsService sclDealerDocumentsService;

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

    @Override
    public void uploadDealerDocument(String dealerCode, String documentType, MultipartFile file) {
        getSclDealerDocumentsService().uploadDealerDocument(dealerCode,documentType,file);
    }

    /**
     * method to upload dealers document
     * @param
     *//*
    @Override
    public void uploadDealerDocument(final DealerDocumentUploadData documentUploadData){
        getSclDealerDocumentsService().uploadDealerDocument(documentUploadData);
    }*/


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

    public SclDealerDocumentsService getSclDealerDocumentsService() {
        return sclDealerDocumentsService;
    }

    public void setSclDealerDocumentsService(SclDealerDocumentsService sclDealerDocumentsService) {
        this.sclDealerDocumentsService = sclDealerDocumentsService;
    }

}
