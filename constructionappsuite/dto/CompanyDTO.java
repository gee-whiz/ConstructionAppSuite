/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.codetribe1.constructionappsuite.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author aubreyM
 */
public class CompanyDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer companyID, countryID;
    private String companyName;
    private String address, vatNumber, taxNumber;

    private List<ProjectDTO> projectList = new ArrayList<>();
    private List<ProjectStatusTypeDTO> projectStatusTypeList = new ArrayList<>();
    private List<CompanyStaffDTO> companyStaffList = new ArrayList<>();
    private List<TaskStatusDTO> taskStatusList = new ArrayList<>();
    private List<TaskDTO> taskList = new ArrayList<>();
    private List<ClientDTO> clientList = new ArrayList<>();
    private List<InvoiceDTO> invoiceList = new ArrayList<>();
    private List<BankDetailDTO> bankDetailList = new ArrayList<>();
    private List<GcmDeviceDTO> gcmDeviceList = new ArrayList<>();

    private List<EngineerDTO> engineerList = new ArrayList<EngineerDTO>();


    public CompanyDTO() {
    }

    public List<EngineerDTO> getEngineerList() {
        return engineerList;
    }

    public void setEngineerList(List<EngineerDTO> engineerList) {
        this.engineerList = engineerList;
    }

    public Integer getCountryID() {
        return countryID;
    }

    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public List<BankDetailDTO> getBankDetailList() {
        return bankDetailList;
    }

    public void setBankDetailList(List<BankDetailDTO> bankDetailList) {
        this.bankDetailList = bankDetailList;
    }


    public List<TaskDTO> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TaskDTO> taskList) {
        this.taskList = taskList;
    }

    public List<ClientDTO> getClientList() {
        return clientList;
    }

    public void setClientList(List<ClientDTO> clientList) {
        this.clientList = clientList;
    }


    public List<InvoiceDTO> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<InvoiceDTO> invoiceList) {
        this.invoiceList = invoiceList;
    }


    public List<GcmDeviceDTO> getGcmDeviceList() {
        return gcmDeviceList;
    }

    public void setGcmDeviceList(List<GcmDeviceDTO> gcmDeviceList) {
        this.gcmDeviceList = gcmDeviceList;
    }


    public List<TaskStatusDTO> getTaskStatusList() {
        return taskStatusList;
    }

    public void setTaskStatusList(List<TaskStatusDTO> taskStatusList) {
        this.taskStatusList = taskStatusList;
    }


    public Integer getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Integer companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<ProjectDTO> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectDTO> projectList) {
        this.projectList = projectList;
    }

    public List<ProjectStatusTypeDTO> getProjectStatusTypeList() {
        return projectStatusTypeList;
    }

    public void setProjectStatusTypeList(List<ProjectStatusTypeDTO> projectStatusTypeList) {
        this.projectStatusTypeList = projectStatusTypeList;
    }

    public List<CompanyStaffDTO> getCompanyStaffList() {
        return companyStaffList;
    }

    public void setCompanyStaffList(List<CompanyStaffDTO> companyStaffList) {
        this.companyStaffList = companyStaffList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companyID != null ? companyID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompanyDTO)) {
            return false;
        }
        CompanyDTO other = (CompanyDTO) object;
        if ((this.companyID == null && other.companyID != null) || (this.companyID != null && !this.companyID.equals(other.companyID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.boha.monitor.data.Company[ companyID=" + companyID + " ]";
    }

}
