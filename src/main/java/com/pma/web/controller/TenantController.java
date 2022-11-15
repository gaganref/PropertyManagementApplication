package com.pma.web.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pma.web.model.Tenant;
import com.pma.web.service.TenantServiceImpl;

@RestController
@RequestMapping("/tenant")
public class TenantController {
	@Autowired
    private TenantServiceImpl tenantService;

    @PostMapping("/add")
    public ResponseEntity<Tenant> addTenant(@RequestBody Tenant tenant) {
        return ResponseEntity.ok().body(this.tenantService.addTenant(tenant));
    }

    @GetMapping("/{TenantId}")
    public ResponseEntity<Tenant> getTenant(@PathVariable("TenantId") long tenantId) {
        return ResponseEntity.ok().body(tenantService.getTenant(tenantId));
    }
    
    
	@PutMapping("/{TenantId}")
	public ResponseEntity<Tenant> updateTenant(@RequestBody Tenant tenant, @PathVariable("TenantId") long tenantId) {
		return ResponseEntity.ok().body(this.tenantService.updateTenant(tenantId, tenant));
	}
	
    @DeleteMapping("/{TenantId}")
	public HttpStatus removeTenant(@PathVariable("TenantId") long tenantId) {
		this.tenantService.removeTenant(tenantId);
		return HttpStatus.OK;
	}
    
    @GetMapping("/getall")
	public ResponseEntity<List<Tenant>> getAllTenants() {
		return ResponseEntity.ok().body(tenantService.getAllTenants());
	}
}
