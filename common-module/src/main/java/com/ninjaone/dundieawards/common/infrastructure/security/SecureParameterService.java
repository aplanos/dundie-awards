package com.ninjaone.dundieawards.common.infrastructure.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SecureParameterService {

    /**
     * Retrieves the value of a secure parameter based on the provided key.
     *
     * Currently, this method returns a mock value for the "jwt.secretKey" key.
     * This is a placeholder implementation. In the future, it will fetch the secret key
     * from a secure location like AWS Secrets Manager, HashiCorp Vault, or other secure storage services.
     *
     * @param key The key of the parameter to retrieve.
     * @return The value associated with the provided key, or {@code null} if the key is not found.
     */
    public String getParameter(String key) {

        if (key.equals("jwt.secretKey")) {
            // Mock value: This should eventually be replaced by a secure retrieval process
            return "aXJybUkwQlR5dWZCOTdZRGRndzllZGJzNExaMlBOUmtSQk5jbG5LVVpwZmpIb1BwV1RNN1lPRi8rRkk=";
        }

        return null;
    }
}

