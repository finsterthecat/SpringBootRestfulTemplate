/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.navan.alerttracker;

import io.navan.alerttracker.model.Alert;
import io.navan.alerttracker.model.Billionaire;
import io.navan.alerttracker.repositories.BillionaireRepository;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tonybrouwer
 */
@RestController
@RequestMapping("billionaires")
public class BillionaireController {
    private static final Logger LOG = LoggerFactory.getLogger(BillionaireController.class);

    @Autowired
    private BillionaireRepository billionaireRepository;
    
    @GetMapping(value="/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Billionaire SingleBillionaire(@PathVariable Integer id) {
        LOG.debug("Start SingleBillionaire: {}", id);
        var billionaire = billionaireRepository.findById(id);
        if (billionaire == null) {
            throw new EntityNotFoundException(Billionaire.class.getName());
        }
        return billionaire;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Iterable<Billionaire> AllBillionaire() {
        LOG.debug("Start AllBillionaiore");
        return billionaireRepository.findAll();
    }

}
