package service;

import model.Shop;
import repository.ShopRepository;

public class ShopService extends AbstractService<Shop> {
    public ShopService(ShopRepository repository) {
        super(repository);
    }

}
