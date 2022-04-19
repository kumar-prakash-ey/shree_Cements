package com.scl.facades.populators.prosdealer;

import com.scl.core.model.ProspectiveDealerModel;
import com.scl.facades.prosdealer.data.ProsDealerData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class ProspectiveDealerExtPopulator implements Populator<ProspectiveDealerModel, ProsDealerData> {

    @Override
    public void populate(ProspectiveDealerModel source, ProsDealerData target) throws ConversionException {

        target.setGstIN(source.getGstIN());
        target.setPanNo(source.getPanCard());
        //TODO://populate  more attribute here
    }
}
