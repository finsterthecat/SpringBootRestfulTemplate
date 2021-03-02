/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.navan.alerttracker.repositories;

import io.navan.alerttracker.model.Billionaire;
import org.springframework.data.repository.Repository;

/**
 *
 * @author tonybrouwer
 */
public interface BillionaireRepository extends Repository<Billionaire, Long>{
    public Billionaire findById(Integer id);
    public Iterable<Billionaire> findAll();
}
