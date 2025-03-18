package com.SR.PoleAppBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.SR.PoleAppBackend.Service.OptionService;
import com.SR.PoleAppBackend.entity.Option;

@RestController
@RequestMapping("/api/options")
@CrossOrigin
public class OptionController {

    @Autowired
    private OptionService optionService;

    /**
     * GET: Retrieve all options
     */
    @GetMapping
    public List<Option> getAllOptions() {
        return optionService.getAllOptions();
    }

    /**
     * GET: Retrieve option by ID
     */
    @GetMapping("/{id}")
    public Option getOptionById(@PathVariable Long id) {
        return optionService.getOptionById(id);
    }

    /**
     * POST: Create a new option
     */
    @PostMapping
    public Option createOption(@RequestBody Option option) {
        return optionService.createOption(option);
    }

    /**
     * DELETE: Delete an option
     */
    @DeleteMapping("/{id}")
    public void deleteOption(@PathVariable Long id) {
        optionService.deleteOption(id);
    }
}
