package com.scl.facades.populators.prosdealer;

import com.scl.core.model.ProspectiveDealerModel;
import com.scl.facades.prosdealer.data.ProsDealerData;
import de.hybris.platform.converters.Populator;

public class ProspectiveDealerBasicPopulator implements Populator<ProspectiveDealerModel, ProsDealerData> {

    @Override
    public void populate(ProspectiveDealerModel source, ProsDealerData target) {
        target.setDealerCode(source.getDealerCode());
        target.setDealerName(source.getDealerName());
        target.setStatusOfApplicant(null!= source.getStatusOfApplicant()?source.getStatusOfApplicant().getCode():null);
    }
}
