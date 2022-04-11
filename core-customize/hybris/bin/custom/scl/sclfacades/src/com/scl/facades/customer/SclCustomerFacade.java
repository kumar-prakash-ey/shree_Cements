package com.scl.facades.customer;

import de.hybris.platform.commercefacades.customer.CustomerFacade;
import org.springframework.web.multipart.MultipartFile;

public interface SclCustomerFacade extends CustomerFacade {

    /**
     * calculates and saves customer's last six month avaerage order value
     */
    void calculateLastSixMonthsAverageOrderValue();



    void uploadDealerDocument(final String dealerCode , final String documentType , final MultipartFile file);
}
