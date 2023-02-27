package com.pma.web.controller;

import com.pma.web.model.TenancyInfo;
import com.pma.web.service.HouseServiceImpl;
import com.pma.web.service.TenancyInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tenancies")
public class TenancyInfoController {

    @Autowired
    private TenancyInfoServiceImpl tenancyInfoService;

    @Autowired
    private HouseServiceImpl houseService;

    @GetMapping("/all")
    public String getAllTenancies(Model model) {
        model.addAttribute("tenancies", tenancyInfoService.getAllTenancyInfos());
        return "tenancies/allTenancies";
    }

    @GetMapping("/{tenancyInfoID}")
    public String getTenancy(@PathVariable("tenancyInfoID") long tenancyInfoID, Model model) {
        List<TenancyInfo> tenancies  = new ArrayList<>();
        tenancies.add(tenancyInfoService.getTenancyInfo(tenancyInfoID));
        model.addAttribute("tenancies", tenancies);
        return "tenancies/allTenancies";
    }

    @GetMapping("/showAdd")
    public String showAddTenancyInfo(TenancyInfo tenancyInfo, Model model) {
        model.addAttribute("tenancy", new TenancyInfo());
        model.addAttribute("houses", houseService.getAllHouses());
        return "tenancies/add";
    }

    @PostMapping("/add")
    public String addTenancyInfo(@Valid @ModelAttribute("tenancy") TenancyInfo tenancyInfo, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("houses", houseService.getAllHouses());
            return "tenancies/add";
        }

        tenancyInfoService.addTenancyInfo(tenancyInfo);
        return "redirect:/tenancies/all";
    }

    @GetMapping("/{tenancyInfoID}/edit")
    public String showUpdateTenancyInfo(@PathVariable("tenancyInfoID") long tenancyInfoID, Model model) {
        TenancyInfo tenancyInfo = tenancyInfoService.getTenancyInfo(tenancyInfoID);
        model.addAttribute("tenancy", tenancyInfo);
        model.addAttribute("houses", houseService.getAllHouses());
        return "tenancies/update";
    }

    @PostMapping("/{tenancyInfoID}/update")
    public String updateTenancyInfo(@PathVariable("tenancyInfoID") long tenancyInfoID, @Valid @ModelAttribute("tenancy") TenancyInfo tenancyInfo,
                                                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            tenancyInfo.setTenancyInfoID(tenancyInfoID);
            model.addAttribute("houses", houseService.getAllHouses());
            return "tenancies/update";
        }

        tenancyInfoService.updateTenancyInfo(tenancyInfoID, tenancyInfo);
        return "redirect:/tenancies/all";
    }

    @GetMapping("/{tenancyInfoID}/delete")
    public String removeTenancy(@PathVariable("tenancyInfoID") long tenancyInfoID) {
        this.tenancyInfoService.removeTenancyInfo(tenancyInfoID);
        return "redirect:/tenancies/all";
    }
}
