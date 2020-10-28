package com.bms.gateway.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthenticationResponse implements Serializable {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3048190536562001763L;
	private final String jwt;
    private Long id;
	private String username;
    private List<String> roles;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }


}
