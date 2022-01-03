package com.github.lastsunday.cloud.service;

public interface TenantService {

    void loginTenant(Long tenantId);

    Long getTenant();
}
