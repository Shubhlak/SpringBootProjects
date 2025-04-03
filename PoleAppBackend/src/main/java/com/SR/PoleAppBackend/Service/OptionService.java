package com.SR.PoleAppBackend.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SR.PoleAppBackend.Repository.OptionRepository;
import com.SR.PoleAppBackend.entity.Option;

@Service
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    // Retrieve all options
    public List<Option> getAllOptions() {
        return optionRepository.findAll();
    }

    // Retrieve an option by ID
    public Option getOptionById(Long id) {
        return optionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Option not found with ID: " + id));
    }

    // Create a new option
    public Option createOption(Option option) {
        return optionRepository.save(option);
    }

    // Delete an option by ID
    public void deleteOption(Long id) {
        optionRepository.deleteById(id);
    }
}
