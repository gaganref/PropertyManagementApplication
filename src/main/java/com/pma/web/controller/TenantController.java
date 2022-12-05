package com.pma.web.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pma.web.model.Tenant;
import com.pma.web.service.TenantServiceImpl;

import javax.validation.Valid;

@Controller
@RequestMapping("/tenant")
public class TenantController {
	@Autowired
    private TenantServiceImpl tenantService;

	@GetMapping("/showAdd")
	public String showAddHouse(Tenant tenant, Model model) {
		model.addAttribute("tenant", new Tenant());
		return "tenants/add";
	}

	@PostMapping("/add")
	public String addTenant(@Valid Tenant tenant, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "tenants/add";
		}

		tenantService.addTenant(tenant);
		return "redirect:/tenant/getall";
	}

    @GetMapping("/getbyId/{TenantId}")
    public String getTenant(@PathVariable("TenantId") long tenantId, Model model) {
		List<Tenant> tenants = new ArrayList<>();
		tenants.add(tenantService.getTenant(tenantId));
		model.addAttribute("tenants", tenants);
		return "tenants/allTenants";
    }

	@GetMapping("/{tenantId}/edit")
	public String showTenantUpdate(@PathVariable("tenantId") long tenantId, Model model) {
		Tenant tenant = tenantService.getTenant(tenantId);
		model.addAttribute("tenant", tenant);
		return "tenants/update";
	}

	@PostMapping("/{tenantId}/update")
	public String updateTenant(@PathVariable("tenantId") long tenantId, @Valid Tenant tenant,
							  BindingResult result, Model model) {
		if (result.hasErrors()) {
			tenant.setTenantId(tenantId);
			return "tenants/update";
		}

		tenantService.updateTenant(tenantId, tenant);
		return "redirect:/tenant/getall";
	}

	@GetMapping("/{TenantId}/delete")
	public String removeTenant(@PathVariable("TenantId") long tenantId, Model model) {
		this.tenantService.removeTenant(tenantId);
		return "redirect:/tenant/getall";
	}
    
    @GetMapping("/getall")
	public String getAllTenants(Model model) {
		model.addAttribute("tenants", tenantService.getAllTenants());
		return "tenants/allTenants";
	}
}
