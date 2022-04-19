package com.scl.core.customer.services.impl;

import com.scl.core.customer.services.SclCustomerService;
import com.scl.core.model.DealerModel;
import com.scl.core.model.ProspectiveDealerModel;
import com.scl.core.model.SclUserModel;
import com.scl.core.prosdealer.dao.ProsDealerDao;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.services.impl.DefaultB2BCustomerService;
import de.hybris.platform.servicelayer.exceptions.ClassMismatchException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.user.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.*;
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
    public List<ProspectiveDealerModel> getProspectiveDealersList(SclUserModel sclUser) {
        Set<B2BCustomerModel> reportees = sclUser.getReportees();
        List<ProspectiveDealerModel> reporteesRet = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(reportees)){
            reporteesRet = reportees.stream()
                    .filter(ProspectiveDealerModel.class::isInstance)
                    .map(ProspectiveDealerModel.class::cast)
                    .collect(Collectors.toList());
        }
        return reporteesRet;
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
