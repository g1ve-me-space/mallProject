package repository;

import model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {

    // ⚠️ ACEASTA METODĂ LIPSEA și cauza eroarea în ShopService
    Optional<Shop> findByNameIgnoreCase(String name);

    // Metoda pentru filtrarea după etaj (folosită în controller)
    List<Shop> findByFloor_Id(String floorId);
}