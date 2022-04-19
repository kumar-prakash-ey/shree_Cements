package com.scl.core.prosdealer.service;

import com.scl.core.model.ProspectiveDealerModel;
import org.springframework.web.multipart.MultipartFile;

public interface ProsDealerService {

    ProspectiveDealerModel getProsDealerByCode(final String dealerCode);

    void uploadDealerDocument(final ProspectiveDealerModel prosDealer , final String documentType , final MultipartFile file);
}
