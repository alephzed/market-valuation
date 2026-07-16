package com.marketvaluation.identity;

import org.springframework.stereotype.Component;

/**
 * Placeholder marking the Identity bounded context (ADR-0001). Slice 1
 * exists only to prove the module boundary and the Gradle wiring; Users,
 * Watchlists, Alert configuration, and RBAC tiers are filled in by later
 * slices.
 */
@Component
public class IdentityModule {

    public String describe() {
        return "Identity module placeholder";
    }
}
