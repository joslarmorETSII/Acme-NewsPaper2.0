package services;

import domain.*;
import forms.SubscribeForm;
import forms.UserForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class CustomerService {

    // Customer repository -----------------------------------------------------

    @Autowired
    private CustomerRepository customerRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private ActorService actorService;

    // Constructors -----------------------------------------------------------

    public CustomerService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Customer create() {
        Customer result;

        result = new Customer();
        result.setNewsPapers(new ArrayList<NewsPaper>());
        result.setUserAccount(userAccountService.create("CUSTOMER"));
        result.setFolders(new ArrayList<Folder>());
        result.setVolumes(new ArrayList<Volume>());

        return result;
    }

    public Customer findOne(final int customerId) {

        Customer result;
        result = this.customerRepository.findOne(customerId);
        return result;
    }

    public Collection<Customer> findAll() {

        Collection<Customer> result;
        result = this.customerRepository.findAll();
        return result;
    }

    public Customer save(Customer customer) {

        Assert.notNull(customer);
        Customer result;
        Collection<Folder> folders;


        if (customer.getId() == 0) {
            result = this.customerRepository.save(customer);
            folders = actorService.generateFolders(result);
            customer.setFolders(folders);
            result = this.customerRepository.save(result);

        } else
            result = this.customerRepository.save(customer);

        return result;
    }

    // Other business methods

    public Customer findByPrincipal() {

        Customer result;
        final UserAccount customerAccount = LoginService.getPrincipal();
        Assert.notNull(customerAccount);
        result = this.findByCustomerAccountId(customerAccount.getId());
        Assert.notNull(result);
        return result;
    }

    public Customer findByCustomerAccountId(final int customerAccountId) {

        Customer result;
        result = this.customerRepository.findByCustomerAccountId(customerAccountId);
        return result;
    }


    public CreditCard reconstructSubscribe(SubscribeForm subscribeForm, final BindingResult binding) {
        CreditCard creditCard = creditCardService.create();

        creditCard.setBrand(subscribeForm.getBrand());
        creditCard.setCvv(subscribeForm.getCvv());
        creditCard.setExpirationMonth(subscribeForm.getExpirationMonth());
        creditCard.setExpirationYear(subscribeForm.getExpirationYear());
        creditCard.setHolder(subscribeForm.getHolder());
        creditCard.setNumber(subscribeForm.getNumber());

        return creditCard;
    }

    public Customer reconstruct(UserForm userForm, final BindingResult binding) {

        Customer result;

        result = this.create();
        result.getUserAccount().setUsername(userForm.getUsername());
        result.setName(userForm.getName());
        result.setSurname(userForm.getSurname());
        result.setPhone(userForm.getPhone());
        result.setEmail(userForm.getEmail());
        result.setPostalAddresses(userForm.getPostalAddresses());
        result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(userForm.getPassword(), null));

        this.comprobarContrasena(userForm.getPassword(), userForm.getRepeatPassword(), binding);

        return result;
    }

    private boolean comprobarContrasena(final String password, final String passwordRepeat, final BindingResult binding) {
        FieldError error;
        String[] codigos;
        boolean result;

        if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(passwordRepeat))
            result = password.equals(passwordRepeat);
        else
            result = false;

        if (!result) {
            codigos = new String[1];
            codigos[0] = "customer.password.mismatch";
            error = new FieldError("userForm", "password", password, false, codigos, null, "");
            binding.addError(error);
        }

        return result;
    }

    public void flush() {
        customerRepository.flush();
    }
}
