package bank.service;

import bank.entity.*;

import java.util.Comparator;

public interface BankComparator extends Comparator<Bank>{
    @Override
    int compare(Bank o1, Bank o2);

}