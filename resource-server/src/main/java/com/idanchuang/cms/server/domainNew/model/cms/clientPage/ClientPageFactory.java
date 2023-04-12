package com.idanchuang.cms.server.domainNew.model.cms.clientPage;


public class ClientPageFactory {
    public static ClientPage createClientPage(ClientPageForm clientPageForm) {
        return new ClientPage(clientPageForm.getId(),
                clientPageForm.getName(),
                clientPageForm.getPlatform(),
                clientPageForm.getPageCode(),
                clientPageForm.getOperatorId(),
                null,
                null );
    }
}
