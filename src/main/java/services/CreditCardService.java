package services;


import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;


import javax.transaction.Transactional;

import domain.Customer;
import domain.Customer;
import forms.RegisterAdvertisementForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

    // Managed Repositories
    @Autowired
    private CreditCardRepository	creditCardRepository;

    // Supported Services

    @Autowired
    private ActorService			actorService;

    @Autowired
    private CustomerService			customerService;


    // Constructor
    public CreditCardService() {
        super();
    }

    // Simple CRUD Methods

    public CreditCard create() {

        CreditCard result;

        result = new CreditCard();

        return result;
    }

    public CreditCard save(final CreditCard creditCard) {

        Assert.notNull(creditCard);
        //Assert.isTrue(this.actorService.isCustomer());
        //  Assert.isTrue(creditCard.getActor().equals(this.actorService.findByPrincipal()));

        CreditCard result;

        result = this.creditCardRepository.save(creditCard);

        return result;
    }

    public void delete(final CreditCard creditCard) {
        Assert.notNull(creditCard);

        Customer customer = customerService.findByPrincipal();
        Assert.isTrue(customer.getCreditCard().equals(creditCard));

        customer.setCreditCard(null);
        customerService.save(customer);
        this.creditCardRepository.delete(creditCard);
    }

    public CreditCard findOne(final int creditCardId) {
        Assert.isTrue(creditCardId != 0);
        CreditCard result;

        result = this.creditCardRepository.findOne(creditCardId);
        Assert.notNull(result);
        return result;
    }

    public Collection<CreditCard> findAll() {
        Collection<CreditCard> result;

        result = this.creditCardRepository.findAll();
        Assert.notNull(result);

        return result;
    }

    public void flush() {
        creditCardRepository.flush();
    }

    public CreditCard reconstruct(RegisterAdvertisementForm registerAdvertisementForm,  BindingResult binding) {
        CreditCard creditCard = create();

        creditCard.setBrand(registerAdvertisementForm.getBrand());
        creditCard.setCvv(registerAdvertisementForm.getCvv());
        creditCard.setExpirationMonth(registerAdvertisementForm.getExpirationMonth());
        creditCard.setExpirationYear(registerAdvertisementForm.getExpirationYear());
        creditCard.setHolder(registerAdvertisementForm.getHolder());
        creditCard.setNumber(registerAdvertisementForm.getNumber());

        checkMonth(registerAdvertisementForm.getExpirationMonth(),registerAdvertisementForm.getExpirationYear(),binding);

        return creditCard;
    }
    private boolean checkMonth(Integer month, Integer year, BindingResult binding) {
        FieldError error;
        String[] codigos;
        boolean result;
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Integer actualMonth = c.get(Calendar.MONTH)+1;
        Integer actualYear = c.get(Calendar.YEAR);


        if (month!=null)
            result = month.equals(actualMonth) && actualYear.equals(year);
        else
            result = false;

        if (result) {
            codigos = new String[1];
            codigos[0] = "creditCard.month.expired";
            error = new FieldError("registerAdvertisementForm", "expirationMonth", month, false, codigos, null, "must not expire this month");
            binding.addError(error);
        }if (!result && actualYear.equals(year) && month<actualMonth){
            codigos = new String[1];
            codigos[0] = "creditCard.month.invalid";
            error = new FieldError("registerAdvertisementForm", "expirationMonth", month, false, codigos, null, "should not be in the past");
            binding.addError(error);
        }

        return result;
    }
}
