package hn.demo.seguros.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;


public interface QuotationRepository
        extends JpaRepository<QuotationEntity, Long> {

}
