package com.bms.gateway.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequest implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -639729139745495498L;
	private String username;
    private String password;

 

    public AuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
}