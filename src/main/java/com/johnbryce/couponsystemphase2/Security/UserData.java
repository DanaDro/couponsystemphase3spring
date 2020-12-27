package com.johnbryce.couponsystemphase2.Security;

import com.johnbryce.couponsystemphase2.Service.ClientService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
	
	private ClientService clientService;
	private long timestamp;
	
}
