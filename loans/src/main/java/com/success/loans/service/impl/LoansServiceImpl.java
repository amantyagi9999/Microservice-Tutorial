package com.success.loans.service.impl;

import com.success.loans.constants.LoansConstants;
import com.success.loans.dto.LoansDto;
import com.success.loans.entity.Loans;
import com.success.loans.exception.LoanAlreadyExistsException;
import com.success.loans.exception.ResourceNotFoundException;
import com.success.loans.mapper.LoansMapper;
import com.success.loans.repository.LoansRepository;
import com.success.loans.service.LoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class LoansServiceImpl implements LoansService {

    @Autowired
    LoansRepository loansRepository;

    @Override
    public void createLoan(LoansDto loansDto) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(loansDto.getMobileNumber());
        if (optionalLoans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + loansDto.getMobileNumber());
        }
        Loans newLoan = LoansMapper.mapToLoans(loansDto, new Loans());
        loansRepository.save(newLoan);
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
