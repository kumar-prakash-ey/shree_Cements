package com.scl.facades.prosdealer;

import org.springframework.web.multipart.MultipartFile;

public interface ProsDealerFacade {

    void uploadDealerDocument(final String dealerCode , final String documentType , final MultipartFile file);
}
