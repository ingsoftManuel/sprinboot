package co.edu.co.spring.demo.repository;

import co.edu.co.spring.demo.domain.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VehiclesRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findByType(String type);
    List<Vehicle> findByPricePerDayBetween(BigDecimal minPrice, BigDecimal maxPrice);
    List<Vehicle> findByStatus(String status);

}
