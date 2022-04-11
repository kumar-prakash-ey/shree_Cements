package com.scl.core.customer.dao.impl;

import com.scl.core.customer.dao.SclCustomerAccountDao;
import de.hybris.platform.commerceservices.customer.dao.impl.DefaultCustomerAccountDao;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.store.BaseStoreModel;

import java.util.*;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

public class DefaultSclCustomerAccountDao extends DefaultCustomerAccountDao implements SclCustomerAccountDao {

    private static final String FIND_ORDERS_BY_CUSTOMER_STORE_QUERY = "SELECT {" + OrderModel.PK + "}, {"
            + OrderModel.CREATIONTIME + "}, {" + OrderModel.CODE + "} FROM {" + OrderModel._TYPECODE + "} WHERE {" + OrderModel.USER
            + "} = ?customer AND {" + OrderModel.VERSIONID + "} IS NULL AND {" + OrderModel.STORE + "} = ?store";

    private static final String FIND_ORDERS_BY_CUSTOMER_STORE_QUERY_AND_STATUS = FIND_ORDERS_BY_CUSTOMER_STORE_QUERY + " AND {"
            + OrderModel.STATUS + "} IN (?statusList)";

    private static final String FIND_ORDERS_BY_CUSTOMER_STORE_QUERY_AND_STATUS_AND_TIME = FIND_ORDERS_BY_CUSTOMER_STORE_QUERY_AND_STATUS + " AND {"
            + OrderModel.CREATIONTIME + "} >= ?fromDate";

    private FlexibleSearchService flexibleSearchService;

    /**
     * dao method to find last six month order for customer and basestore
     * @param customerModel
     * @param store
     * @param status
     * @param fromDate
     * @return
     */
    @Override
    public List<OrderModel> findLastSixMonthsOrdersByCustomerAndStore(final CustomerModel customerModel, final BaseStoreModel store,
                                                              final OrderStatus[] status, final Date fromDate){

        validateParameterNotNull(customerModel, "Customer must not be null");
        validateParameterNotNull(store, "Store must not be null");

        final Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("customer", customerModel);
        queryParams.put("store", store);
        queryParams.put("fromDate", fromDate);
        queryParams.put("statusList", Arrays.asList(status));

        String query = FIND_ORDERS_BY_CUSTOMER_STORE_QUERY_AND_STATUS_AND_TIME;

        final SearchResult<OrderModel> result = getFlexibleSearchService().search(query, queryParams);
        return result.getResult();
    }

    @Override
    public FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    @Override
    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
}
