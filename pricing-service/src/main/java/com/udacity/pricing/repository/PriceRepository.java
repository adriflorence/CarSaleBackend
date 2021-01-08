package com.udacity.pricing.repository;

import com.udacity.pricing.domain.Price;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price, Long> {
}
