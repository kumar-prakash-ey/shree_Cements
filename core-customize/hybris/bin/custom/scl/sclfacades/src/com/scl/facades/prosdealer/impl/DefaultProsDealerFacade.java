package com.scl.facades.prosdealer.impl;

import com.scl.core.model.ProspectiveDealerModel;
import com.scl.core.prosdealer.service.ProsDealerService;
import com.scl.facades.prosdealer.ProsDealerFacade;
import org.springframework.web.multipart.MultipartFile;

public class DefaultProsDealerFacade implements ProsDealerFacade {

    private ProsDealerService prosDealerService;


    @Override
    public void uploadDealerDocument(String prosDealerCode, String documentType, MultipartFile file) {
        ProspectiveDealerModel prospectiveDealer = getProsDealerService().getProsDealerByCode(prosDealerCode);
        getProsDealerService().uploadDealerDocument(prospectiveDealer,documentType,file);
    }

    public ProsDealerService getProsDealerService() {
        return prosDealerService;
    }

    public void setProsDealerService(ProsDealerService prosDealerService) {
        this.prosDealerService = prosDealerService;
    }
}
