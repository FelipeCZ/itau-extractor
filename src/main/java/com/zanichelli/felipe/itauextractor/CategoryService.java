package com.zanichelli.felipe.itauextractor;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
        Arrays.asList(
            new Category("Gás", false, Arrays.asList("DA  ULTRAGAZ.*")),
            new Category("Mercado", false, Arrays.asList(".*CARREFOUR .*", ".*Spani.*", ".*SUPERMERCAD.*", ".*RSHOP-EXTRA.*")),
            new Category("Milka", false, Arrays.asList(".*CIA FAUNA.*", ".*COBASI.*")),
            new Category("Luz", false, Arrays.asList("DA  EDP SAO PAULO.*")),
            new Category("Restaurante", false, Arrays.asList(".*SAO COOKIES.*", ".*PIZZA PRE A.*", ".*PICHINELLI.*")),
            new Category("Internet", false, Arrays.asList(".*VIVO FIXO.*")),
            new Category("Carro", false, Arrays.asList(".*COOPERCITRU.*", ".*CONECTAR.*")),
            new Category("Presente", false, Arrays.asList(".*Gingada.*", ".*PIX TRANSF  Elen Fe.*")),
            new Category("Condomínio", false, Arrays.asList("INT PAG TIT 109000045952")),

            new Category("Ignored", true, Arrays.asList(".*APLIC AUT MAIS", "SALDO FINAL", "SALDO PARCIAL"))
        ).forEach(cat -> {
            if (categoryRepository.findById(cat.getName()).isEmpty()) {
                categoryRepository.save(cat);
            }
        });
    }


    public CategoryClassifier newClassifier() {
        return new CategoryClassifier();
    }

    public class CategoryClassifier {

        private List<Category> allCategories;

        private CategoryClassifier() {
            this.allCategories = categoryRepository.findAll();
        }

        public Category findCategory(final String softDescriptor) {
            return allCategories.stream()
                .filter(cat -> cat.getRegex().stream().anyMatch(reg -> Pattern.matches(reg, softDescriptor)))
                .findFirst().orElse(null);
        }
    }
}
