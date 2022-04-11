package com.scl.core.customer.services.impl;

import com.scl.core.customer.services.SclCustomerService;
import com.scl.core.jalo.SclUser;
import com.scl.core.model.DealerModel;
import com.scl.core.model.SclUserModel;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.impl.DefaultB2BCustomerService;
import de.hybris.platform.servicelayer.exceptions.ClassMismatchException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultSclCustomerService extends DefaultB2BCustomerService implements SclCustomerService {

    private UserService userService;

    private static final Logger LOG = Logger.getLogger(DefaultSclCustomerService.class);

    @Override
    public DealerModel getDealerForCode(final String dealerCode) {
        DealerModel dealer = null;
        try {
            dealer = getUserService().getUserForUID(dealerCode, DealerModel.class);
        }
        catch (final UnknownIdentifierException | ClassMismatchException e) {
            dealer = null;
            LOG.error("Failed to get dealer with code : "+dealerCode);
        }
        return dealer;

    }

    @Override
    public Set<B2BCustomerModel> getPropectiveDealersList(SclUserModel sclUser) {
        Set<B2BCustomerModel> reportees = sclUser.getReportees();
        if(CollectionUtils.isNotEmpty(reportees)){
            reportees.stream().filter(DealerModel.class::isInstance).collect(Collectors.toList());
        }
        return sclUser.getReportees();
    }


    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
