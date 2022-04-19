package com.scl.core.prosdealer.dao.impl;

import com.scl.core.model.ProspectiveDealerModel;
import com.scl.core.prosdealer.dao.ProsDealerDao;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;

import java.util.Collections;
import java.util.List;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

public class DefaultProsDealerDao extends DefaultGenericDao<ProspectiveDealerModel>  implements ProsDealerDao {

    public DefaultProsDealerDao() {
        super(ProspectiveDealerModel._TYPECODE);
    }

    @Override
    public ProspectiveDealerModel findProsDealerByCode(final String prosDealerCode){
        validateParameterNotNullStandardMessage("dealerCode", prosDealerCode);
        final List<ProspectiveDealerModel> prosDealerList = this.find(Collections.singletonMap(ProspectiveDealerModel.DEALERCODE, prosDealerCode));
        if (prosDealerList.size() > 1)
        {
            throw new AmbiguousIdentifierException(
                    String.format("Found %d prospective dealers with the dealerCode value: '%s', which should be unique", prosDealerList.size(),
                            prosDealerCode));
        }
        else
        {
            return prosDealerList.isEmpty() ? null : prosDealerList.get(0);
        }
    }
}
