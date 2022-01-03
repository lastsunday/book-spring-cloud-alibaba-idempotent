package com.github.lastsunday.cloud.service;

import org.springframework.stereotype.Service;

@Service
public class TenantServiceImpl implements TenantService{

    private ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    @Override
    public void loginTenant(Long tenantId) {
        threadLocal.set(tenantId);
    }

    @Override
    public Long getTenant() {
        return threadLocal.get();
    }

}
