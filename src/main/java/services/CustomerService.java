package services;

import domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class CustomerService {

    // Customer repository -----------------------------------------------------

    @Autowired
    private CustomerRepository customerRepository;

    // Supporting services ----------------------------------------------------

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

    public Customer save(final Customer customer) {

        Assert.notNull(customer);

        Customer result;

        if (customer.getId() == 0) {
            result = this.customerRepository.save(customer);
        } else
            result = this.customerRepository.save(customer);

        return result;
    }

    // Other business methods

    public Customer findByPrincipal() {

        Customer result;
        final UserAccount userAccount = LoginService.getPrincipal();
        Assert.notNull(userAccount);
        result = this.findByCustomerAccountId(userAccount.getId());
        Assert.notNull(result);
        return result;
    }



    public Customer findByCustomerAccountId(final int customerAccountId) {

        Customer result;
        result = this.customerRepository.findByCustomerAccountId(customerAccountId);
        return result;
    }
}
