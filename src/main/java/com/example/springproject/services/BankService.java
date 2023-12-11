package com.example.springproject.services;

import com.example.springproject.entities.BankAccount;
import com.example.springproject.entities.CurrentAccount;
import com.example.springproject.entities.SavingAccount;
import com.example.springproject.reposiitories.BankAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    public void consulter(){
        // Pour consulter un compte:
        BankAccount bankAccount=bankAccountRepository.findById("24d8d982-00e0-4d12-95a0-083b0f0be84c").orElse(null);

        if(bankAccount!=null){
            System.out.println("***********************");
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getSimpleName());// pour afficher la classe : courant / saving
            if(bankAccount instanceof CurrentAccount){
                System.out.println( "over Dradft :" +((CurrentAccount) bankAccount).getOverDraft());;
            }
            else if(bankAccount instanceof SavingAccount){
                System.out.println( "Rate :"+((SavingAccount) bankAccount).getInterestRate());;
            }
            // pour afficher les operation de ce compte :
            bankAccount.getAccountOperations().forEach(opreation->{
                System.out.println("-----------------------");
                System.out.println(opreation.getType() +"\t" +opreation.getOperationDate()+"\t"+opreation.getAmount());
            });
        }
    }
}
