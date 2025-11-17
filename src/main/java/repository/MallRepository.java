package repository;

import model.Mall;
import model.Floor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MallRepository extends InMemoryRepository<Mall, String> {}