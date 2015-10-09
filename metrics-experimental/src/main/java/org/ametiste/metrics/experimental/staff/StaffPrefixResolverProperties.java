package org.ametiste.metrics.experimental.staff;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @since
 */
@ConfigurationProperties("org.ametiste.metrics.experimental.resolver.staff")
public class StaffPrefixResolverProperties {

    private String staffMetricPrefix = "staff";

    private String staffTriggerParameter = "ame_staff_metrics";

    public String getStaffMetricPrefix() {
        return staffMetricPrefix;
    }

    public void setStaffMetricPrefix(String staffMetricPrefix) {
        this.staffMetricPrefix = staffMetricPrefix;
    }

    public String getStaffTriggerParameter() {
        return staffTriggerParameter;
    }

    public void setStaffTriggerParameter(String staffTriggerParameter) {
        this.staffTriggerParameter = staffTriggerParameter;
    }
}
