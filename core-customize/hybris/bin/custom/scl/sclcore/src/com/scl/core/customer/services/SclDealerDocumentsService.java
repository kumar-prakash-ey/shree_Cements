package com.scl.core.customer.services;

import org.springframework.web.multipart.MultipartFile;

public interface SclDealerDocumentsService {


    /**
     * Service method to save media to the dealer
     *
     */

    void uploadDealerDocument(final String dealerCode , final String documentType, final MultipartFile file);
}
