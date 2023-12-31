package com.feathercode.domain.mentoring.controller;

import com.feathercode.domain.mentoring.controller.dto.MentoringForm;
import com.feathercode.domain.mentoring.service.MentoringService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mentoring")
public class MentoringController {

    private final MentoringService mentoringService;

    @PostMapping("/{email}")
    public ResponseEntity<?> save(@RequestBody @Valid MentoringForm mentoringForm, @PathVariable String email){
        return mentoringService.save(mentoringForm,email);
    }

    @DeleteMapping("/{email}/{mentoringId}")
    public ResponseEntity<?> delete(@PathVariable Long mentoringId,@PathVariable String email){
        return mentoringService.delete(mentoringId);
    }

    @GetMapping()
    public ResponseEntity<?> findMentoringById(@RequestParam Long mentoringId){
        return mentoringService.findMentoringById(mentoringId);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findMentoringAll(){
        return mentoringService.findAll();
    }

    @PatchMapping("/{mentoringId}/{email}/x/edit")
    public ResponseEntity<?> updateTitle(@PathVariable Long mentoringId,@PathVariable String email, @RequestParam String title){
        return mentoringService.updateTitle(mentoringId,title);
    }

    @PatchMapping("/{mentoringId}/{email}/y/edit")
    public ResponseEntity<?> updateDescription(@PathVariable Long mentoringId,@PathVariable String email, @RequestParam String description){
        return mentoringService.updateDescription(mentoringId,description);
    }

    @PatchMapping("/{mentoringId}/{email}/z/edit")
    public ResponseEntity<?> updateDescription(@PathVariable Long mentoringId,@PathVariable String email, @RequestParam int price){
        return mentoringService.updatePrice(mentoringId,price);
    }

}
