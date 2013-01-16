package com.gordondickens.orm.eclipselink.auditing;

import org.springframework.data.domain.AuditorAware;


public class AuditorAwareImpl implements AuditorAware<AuditableUser> {
    private AuditableUser auditor;

    /**
     * @param auditor the auditor to set
     */
    public void setAuditor(AuditableUser auditor) {
        this.auditor = auditor;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.domain.AuditorAware#getCurrentAuditor()
     */
    @Override
    public AuditableUser getCurrentAuditor() {
        return auditor;
    }

}
