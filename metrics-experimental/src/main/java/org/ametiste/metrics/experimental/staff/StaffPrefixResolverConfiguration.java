package org.ametiste.metrics.experimental.staff;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @since
 */
@Configuration
@EnableConfigurationProperties(StaffPrefixResolverProperties.class)
public class StaffPrefixResolverConfiguration {

    // TBD: assumed to be implemented with the IdentifierTemplateResolver, as extension by variable type
    //
    // to implement this I need to have VariableBind implemention that would
    // be triggered within the request scope and resolve {staffVariable} to the given value,
    // if the specified request parameter is present.

}
