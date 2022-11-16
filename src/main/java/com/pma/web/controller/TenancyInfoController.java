package com.pma.web.controller;

import com.pma.web.model.TenancyInfo;
import com.pma.web.service.TenancyInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tenancy_info")
public class TenancyInfoController {

    @Autowired
    private TenancyInfoServiceImpl tenancyInfoService;

    @GetMapping("/all")
    public ResponseEntity<List<TenancyInfo>> getAllTenancies() {
        return ResponseEntity.ok().body(tenancyInfoService.getAllTenancyInfos());
    }

    @GetMapping("/{tenancyInfoID}")
    public ResponseEntity<TenancyInfo> getTenancy(@PathVariable("tenancyInfoID") long tenancyInfoID) {
        return ResponseEntity.ok().body(tenancyInfoService.getTenancyInfo(tenancyInfoID));
    }

    @GetMapping("/tenant/{tenantID}")
    public ResponseEntity<List<TenancyInfo>> getTenanciesByTenant(@PathVariable("tenantID") long tenantID) {
        return ResponseEntity.ok().body(tenancyInfoService.getTenancyInfoByTenant(tenantID));
    }

    @GetMapping("/house/{houseID}")
    public ResponseEntity<List<TenancyInfo>> getTenanciesByHouse(@PathVariable("houseID") long houseID) {
        return ResponseEntity.ok().body(tenancyInfoService.getTenancyInfoByHouse(houseID));
    }

    @GetMapping("/startDate/{start_date}/{end_date}")
    public ResponseEntity<List<TenancyInfo>> getTenanciesByStartDate(@PathVariable("start_date")LocalDate start_date, @PathVariable("end_date")LocalDate end_date) {
        return ResponseEntity.ok().body(tenancyInfoService.getTenancyInfoByStartDate(start_date, end_date));
    }

    @GetMapping("/endDate/{start_date}/{end_date}")
    public ResponseEntity<List<TenancyInfo>> getTenanciesByEndDate(@PathVariable("start_date")LocalDate start_date, @PathVariable("end_date")LocalDate end_date) {
        return ResponseEntity.ok().body(tenancyInfoService.getTenancyInfoByEndDate(start_date, end_date));
    }

    @PostMapping("/add")
    public ResponseEntity<TenancyInfo> addTenancyInfo(@RequestBody TenancyInfo tenancyInfo) {
        return ResponseEntity.ok().body(this.tenancyInfoService.addTenancyInfo(tenancyInfo));
    }

    @PutMapping("/{tenancyInfoID}/update")
    public ResponseEntity<TenancyInfo> updateTenancyInfo(@PathVariable("tenancyInfoID") long tenancyInfoID, @RequestBody TenancyInfo tenancyInfo) {
        return ResponseEntity.ok().body(this.tenancyInfoService.updateTenancyInfo(tenancyInfoID, tenancyInfo));
    }

    @DeleteMapping("/{tenancyInfoID}/delete")
    public HttpStatus removeCar(@PathVariable("tenancyInfoID") long tenancyInfoID) {
        this.tenancyInfoService.removeTenancyInfo(tenancyInfoID);
        return HttpStatus.OK;
    }
}
