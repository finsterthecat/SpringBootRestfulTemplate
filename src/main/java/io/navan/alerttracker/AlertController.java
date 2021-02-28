/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.navan.alerttracker;

import io.navan.alerttracker.model.Alert;
import java.util.ArrayList;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tonybrouwer
 */
@RestController
@RequestMapping("alerts")
public class AlertController {
    
    private static final Logger LOG = LoggerFactory.getLogger(AlertController.class);
    
    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Iterable<Alert> allAlerts() {
        LOG.debug("Start AllALerts");
        var alerts = new ArrayList<Alert>();
        for (long i = 0; i<5; i++) {
            alerts.add(new Alert(i, String.format("Alert #%s", i)));
        }
        return alerts;
    }
    
    @GetMapping(value="/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Alert SingleAlert(@PathVariable Long id) {
        LOG.debug("Start SingleAlert: {}", id);
        if (id == 22) {
            throw new EntityNotFoundException("Can't find 22, where is it?");
        }
        return new Alert(id, String.format("Single Alert %d", id));
    }
    
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Alert createAlert(@RequestBody Alert alert,
            HttpServletRequest request, HttpServletResponse response) {
        LOG.debug("Start create alert {}", alert.getName());
        return alert;
    }
}
