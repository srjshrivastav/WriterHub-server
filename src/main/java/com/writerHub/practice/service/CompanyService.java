package com.writerHub.practice.service;


import com.writerHub.practice.models.Author;
import com.writerHub.practice.models.Company;
import com.writerHub.practice.repo.AuthorRepository;
import com.writerHub.practice.repo.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;


    public List<Company> getAllCompanies(){
        return  companyRepository.findAll();
    }

    public Company saveCompany(Company company){
        return companyRepository.save(company);
    }

    public Company getCompany(Long id){
        Optional<Company> company = companyRepository.findById(id);
        if(company != null)
            return company.get();
        else
            return  null;
    }

    public void deleteCompany(Long id){
        companyRepository.deleteById(id);
    }

}
