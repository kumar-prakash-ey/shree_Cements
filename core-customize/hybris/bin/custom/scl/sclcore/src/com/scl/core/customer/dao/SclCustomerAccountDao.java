package com.scl.core.customer.dao;

import de.hybris.platform.commerceservices.customer.dao.CustomerAccountDao;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.store.BaseStoreModel;

import java.util.Date;
import java.util.List;

public interface SclCustomerAccountDao extends CustomerAccountDao {

     /**
      * dao method to find last six month order for customer and basestore
      * @param customerModel
      * @param store
      * @param status
      * @param fromDate
      * @return
      */
     List<OrderModel> findLastSixMonthsOrdersByCustomerAndStore(final CustomerModel customerModel, final BaseStoreModel store,
                                                                     final OrderStatus[] status, final Date fromDate);
}
