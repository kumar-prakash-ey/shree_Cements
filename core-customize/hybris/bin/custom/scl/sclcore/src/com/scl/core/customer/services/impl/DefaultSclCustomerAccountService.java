package com.scl.core.customer.services.impl;

import com.scl.core.customer.dao.SclCustomerAccountDao;
import com.scl.core.customer.services.SclCustomerAccountService;
import de.hybris.platform.commerceservices.customer.impl.DefaultCustomerAccountService;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.platform.store.BaseStoreModel;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DefaultSclCustomerAccountService extends DefaultCustomerAccountService implements SclCustomerAccountService {


    private SclCustomerAccountDao sclCustomerAccountDao;
    private TimeService timeService;
    private ModelService modelService;

    /**
     * updtes customer average order value for last six month
     * @param customerModel
     * @param baseStoreModel
     * @param status
     */
    @Override
    public void updateCustomerAverageOrderValue(final CustomerModel customerModel, final BaseStoreModel baseStoreModel,final OrderStatus[] status){
        //Add Condition for calculating by taking in account all orders

        if(customerModel.getName().equalsIgnoreCase("ming")){
            final Date fromDate = getSixMonthsBeforeDate();
            final List<OrderModel> orderList = getSclCustomerAccountDao().findLastSixMonthsOrdersByCustomerAndStore(customerModel,baseStoreModel,status,fromDate);
            BigDecimal totalOrderPrice = orderList.stream().map(order -> BigDecimal.valueOf(order.getTotalPrice()))
                              .reduce(BigDecimal.ZERO,BigDecimal::add);
            BigDecimal averageOrderPrice = totalOrderPrice.divide(BigDecimal.valueOf(orderList.size()));

            customerModel.setAverageOrderValue(averageOrderPrice.doubleValue());

            getModelService().save(customerModel);
            getModelService().refresh(customerModel);
            //BigDecimal currentOrderPrice = BigDecimal.valueOf(7879);
            //BigDecimal erronousThreshold = currentOrderPrice.multiply(BigDecimal.valueOf(1.5));
            //int comparisonValue = currentOrderPrice.compareTo(erronousThreshold);
            //if(comparisonValue == 1){
                //Order is erronous
            //}
        }
        //Logic for calculating average with the help of last calculated average and current order
        else{
            //(currentOrderValue.add(lastAverageValue)).divide(BigDecimal.valueOf(2));
        }


    }

    /**
     * gets six months before date
     * @return
     */
    private Date getSixMonthsBeforeDate() {
        Date currentDate = getTimeService().getCurrentTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH,-6);
        return calendar.getTime();
    }

    public SclCustomerAccountDao getSclCustomerAccountDao() {
        return sclCustomerAccountDao;
    }

    public void setSclCustomerAccountDao(SclCustomerAccountDao sclCustomerAccountDao) {
        this.sclCustomerAccountDao = sclCustomerAccountDao;
    }
    @Override
    public TimeService getTimeService() {
        return timeService;
    }

    @Override
    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

    @Override
    public ModelService getModelService() {
        return modelService;
    }

    @Override
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

}
