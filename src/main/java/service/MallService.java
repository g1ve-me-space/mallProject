package service;

import model.Mall;
import repository.MallRepository;

public class MallService extends AbstractService<Mall> {
    public MallService(MallRepository repository) {
        super(repository);
    }
}
