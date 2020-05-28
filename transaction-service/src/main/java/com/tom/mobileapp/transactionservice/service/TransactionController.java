package com.tom.mobileapp.transactionservice.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tom.mobileapp.transactionservice.model.TransactionBody;

@RestController
public class TransactionController {

	@PostMapping("/user/p2p")
	public String doP2PTransaction(@RequestBody TransactionBody tBody) {
		return "P2P success";
	}

	@PostMapping("/user/p2m")
	public String doP2MTransaction(@RequestBody TransactionBody tBody) {
		return "P2M success";
	}

	@PostMapping("/admin/p2p")
	public String doP2PAdminTransaction(@RequestBody TransactionBody tBody) {
		return "P2P admin success";
	}

	@PostMapping("/admin/p2m")
	public String doP2MAdminTransaction(@RequestBody TransactionBody tBody) {
		return "P2M admin success";
	}

}
