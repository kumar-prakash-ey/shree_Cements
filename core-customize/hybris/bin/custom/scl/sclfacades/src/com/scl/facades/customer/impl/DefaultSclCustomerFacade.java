package com.scl.facades.customer.impl;

import com.scl.core.customer.services.SclCustomerAccountService;
import com.scl.core.customer.services.SclCustomerService;
import com.scl.core.model.ProspectiveDealerModel;
import com.scl.core.model.SclUserModel;
import com.scl.core.prosdealer.service.ProsDealerService;
import com.scl.facades.customer.SclCustomerFacade;
import com.scl.facades.prosdealer.data.ProsDealerData;
import com.scl.facades.prosdealer.data.ProsDealerListData;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;

import java.util.*;
import java.util.stream.Collectors;


public class DefaultSclCustomerFacade extends DefaultCustomerFacade implements SclCustomerFacade {

    private SclCustomerAccountService sclCustomerAccountService;
    private UserService userService;
    private BaseStoreService baseStoreService;
    private SclCustomerService sclCustomerService;
    private Converter<ProspectiveDealerModel, ProsDealerData> prosDealerListConverter;
    private Converter<ProspectiveDealerModel, ProsDealerData> prosDealerDetailsConverter;
    private ProsDealerService prosDealerService;

    private static final String NOT_SCL_USER_MESSAGE = "Current user is not an SCL user";
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
    public ProsDealerListData getProspectiveDealersForCurrentuser() {

        ProsDealerListData prosDealerListData = new ProsDealerListData();
        List<ProspectiveDealerModel> prospectiveDealersList = new ArrayList<>();
        final UserModel currentUser = getUserService().getCurrentUser();
        if(currentUser instanceof SclUserModel){
            final SclUserModel sclUser = (SclUserModel) currentUser;
            prospectiveDealersList =  getSclCustomerService().getProspectiveDealersList(sclUser);
        }
        else{
            throw new ModelNotFoundException(NOT_SCL_USER_MESSAGE);
        }
        List<ProsDealerData> prosDealerData = Optional.of(prospectiveDealersList.stream()
                .map(prosDealer -> getProsDealerListConverter()
                        .convert(prosDealer)).collect(Collectors.toList())).get();
        prosDealerListData.setProsDealers(prosDealerData);
        return prosDealerListData;
    }

    @Override
    public ProsDealerData getProsDealerDetailsByCode(final String prosDealerCode){
       final  ProspectiveDealerModel prospectiveDealer = getProsDealerService().getProsDealerByCode(prosDealerCode);
        if(null == prospectiveDealer){
            throw new ModelNotFoundException("No Prospective dealer found with code : "+prosDealerCode);
        }
        return  getProsDealerDetailsConverter().convert(prospectiveDealer);
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

    public SclCustomerService getSclCustomerService() {
        return sclCustomerService;
    }

    public void setSclCustomerService(SclCustomerService sclCustomerService) {
        this.sclCustomerService = sclCustomerService;
    }

    public Converter<ProspectiveDealerModel, ProsDealerData> getProsDealerListConverter() {
        return prosDealerListConverter;
    }

    public void setProsDealerListConverter(Converter<ProspectiveDealerModel, ProsDealerData> prosDealerListConverter) {
        this.prosDealerListConverter = prosDealerListConverter;
    }
    public ProsDealerService getProsDealerService() {
        return prosDealerService;
    }

    public void setProsDealerService(ProsDealerService prosDealerService) {
        this.prosDealerService = prosDealerService;
    }
    public Converter<ProspectiveDealerModel, ProsDealerData> getProsDealerDetailsConverter() {
        return prosDealerDetailsConverter;
    }

    public void setProsDealerDetailsConverter(Converter<ProspectiveDealerModel, ProsDealerData> prosDealerDetailsConverter) {
        this.prosDealerDetailsConverter = prosDealerDetailsConverter;
    }

}
