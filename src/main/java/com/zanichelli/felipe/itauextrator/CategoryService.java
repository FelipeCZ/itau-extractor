package com.zanichelli.felipe.itauextrator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.HdrHistogram.AbstractHistogram.AllValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
        Arrays.asList(
            new Category("DA  ULTRAGAZ.*", "Gás", false),
            new Category("RSHOP-CARREFOUR .*", "Mercado", false),
            new Category("RSHOP-CIA FAUNA.*", "Milka", false),
            new Category("RSHOP-PAG\\*Spani.*", "Mercado", false),
            new Category("DA  EDP SAO PAULO.*", "Luz", false),
            new Category("RSHOP-SUPERMERCAD.*", "Mercado", false),
            new Category("RSHOP-COBASI.*", "Milka", false)
        ).forEach(cat -> {
            if (categoryRepository.findById(cat.getRegex()).isEmpty()) {
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
                .filter(cat -> Pattern.matches(cat.getRegex(), softDescriptor))
                .findFirst()
                .orElse(new Category(softDescriptor, softDescriptor, true));
        }
    }
}
